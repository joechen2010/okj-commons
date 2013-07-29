/**
 * @(#)JSONOutputterInterceptor.java 2013-1-30
 *
 * Copyright (c) 2004-2013 Lakala, Inc.
 * zhongjiang Road, building 22, Lane 879, shanghai, china 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Lakala, Inc.  
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Lakala.
 */
package org.okj.commons.web.json.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.okj.commons.constants.ResponseFormat;
import org.okj.commons.jexl.JexlExpression;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.FieldSet;
import org.okj.commons.model.Pagination;
import org.okj.commons.web.ResponseHelper;
import org.okj.commons.web.json.annotation.JsonResponse;
import org.okj.commons.web.json.outputter.ItemsJsonOutputter;
import org.okj.commons.web.json.outputter.JsonOutputter;
import org.okj.commons.web.json.outputter.ListJsonOutputter;
import org.okj.commons.web.json.outputter.MessageResultJsonOutputter;
import org.okj.commons.web.json.outputter.TreeMenuJsonOutputter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * JSON������������������������Ҫ����JSON������ͻ��˵�controller
 * @author Administrator
 * @version $Id: JSONOutputterInterceptor.java, v 0.1 2013-1-30 ����12:20:52 Administrator Exp $
 */
public class JSONOutputterInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
    /* log4j */
    private static final Logger        LOGGER = Logger.getLogger(JSONOutputterInterceptor.class);

    /* JSON���������ӳ�� */
    private Map<String, JsonOutputter> outputters;

    /* JEXL���ʽ */
    private JexlExpression             jexlExpression;

    /** 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        Method resolvedMethod = findResolvedMethod(handler, request);
        LogUtils.info(LOGGER, "������Ҫ��ȡ�ķ���, resolvedMethod={0}", resolvedMethod);
        if (resolvedMethod == null) {
            return; //���û���ҵ�ƥ���ע�����˳�����������
        }

        //��ȡ�����ϵ�ע�����
        JsonResponse annotation = AnnotationUtils
            .findAnnotation(resolvedMethod, JsonResponse.class);

        //��request�в��ҽ�����
        Object result = request.getAttribute(annotation.result());
        if (result == null) {
            LogUtils.warn(LOGGER, "Request���޷��ҵ����,��ȷ���ѽ����������Request��,handler={0}", handler);
            return;
        }

        //����JSON���������
        String jsonString = "{}";
        JsonOutputter outputter = outputters.get(annotation.responseFormat());
        if (outputter != null) {
            outputter.setJexlExpression(jexlExpression); //����JEXL���ʽ����
            //���÷�ҳ����
            //��request�в��ҷ�ҳ����
            Pagination pager = getPagination(annotation, request);
            if (pager != null) {
                outputter.setPager(pager);
            }
            //������������б�
            if (annotation.propertyNames() != null && annotation.propertyNames().length > 0) {
                outputter.setFields(new FieldSet("", annotation.propertyNames()));
            }
            //���JSON��ʽ�ַ�
            jsonString = outputter.output(result);
        }

        //�������Ӧ
        ResponseHelper.writeStringToResponse(jsonString, response);
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (outputters == null) {
            outputters = new HashMap<String, JsonOutputter>();
        }
        //װ��Ĭ�ϵ�json���������
        outputters.put(ResponseFormat.QUERY_RESULT, new ListJsonOutputter());
        outputters.put(ResponseFormat.MESSAGE_RESULT, new MessageResultJsonOutputter());
        outputters.put(ResponseFormat.TREE_MENU_SELECTOR, new TreeMenuJsonOutputter());
        outputters.put(ResponseFormat.ITEM_SELECTOR, new ItemsJsonOutputter());

        if (jexlExpression == null) {
            jexlExpression = new JexlExpression();
            jexlExpression.afterPropertiesSet();
        }
    }

    /**
     * ��ָ���Ĵ�������Ѱ�Ҵ���ָ��ע��ķ������ϲ�����
     * @param handler
     * @param annotationClass
     * @return
     */
    protected Set<Method> findHandlerMethods(Object handler,
                                             final Class<? extends Annotation> annotationClass) {
        final Set<Method> handlerMethods = new HashSet<Method>();
        //��ȡ�����������class�����
        final Class<?> handlerClass = ClassUtils.getUserClass(handler);
        //��handler�����б�ע��JsonResultע��ķ������뵽handlerMethods������
        ReflectionUtils.doWithMethods(handlerClass, new ReflectionUtils.MethodCallback() {
            public void doWith(Method method) {
                if (method.isAnnotationPresent(annotationClass)) {
                    Method m = ClassUtils.getMostSpecificMethod(method, handlerClass);
                    handlerMethods.add(m);
                }
            }
        });
        return handlerMethods;
    }

    /**
     * ����controller�����д���JsonResponseע��ķ�������
     * @param handler
     * @param request
     * @return
     */
    protected Method findResolvedMethod(Object handler, HttpServletRequest request) {
        Set<Method> handlerMethods = findHandlerMethods(handler, JsonResponse.class);
        if (handlerMethods.isEmpty()) {
            //�������Ϊ�գ���ֱ�ӷ���null
            LogUtils.warn(LOGGER, "��ǰhandler�������κη���, handler={0}", handler);
            return null;
        }

        //���ֻ��һ������������ֱ�ӷ���
        if (handlerMethods.size() == 1) {
            return handlerMethods.iterator().next();
        }

        //������е����
        return findResolvedMethod(handlerMethods, request);
    }

    /**
     * ��ָ���ķ��������в��Ҵ���JsonResponseע��ķ���
     * @param handlerMethods
     * @param request
     * @return
     */
    protected Method findResolvedMethod(Set<Method> handlerMethods, HttpServletRequest request) {
        //���method�����д��ڶ���1��Ԫ�أ�����Ҫ���е���?Ѱ�ҵ�ǰ������Ǹ�Method����
        for (Enumeration<String> names = request.getParameterNames(); names.hasMoreElements();) {
            //��ȡ��������ֵ��
            String name = names.nextElement();
            String value = request.getParameter(name);

            //���method���󼯺�
            for (Method method : handlerMethods) {
                //�ҵ�JsonResponseע�����
                JsonResponse annotation = AnnotationUtils
                    .findAnnotation(method, JsonResponse.class);
                if (isMatch(annotation, name + "=" + value)) {
                    return method;
                }
            } //end for
        } //end for
        return null;
    }

    /**
     * �Ƿ�ƥ��
     * @param annotation
     * @param params
     * @return
     */
    private boolean isMatch(JsonResponse annotation, String params) {
        if (annotation == null) {
            return false;
        }

        String[] p = annotation.params();
        if (p != null) {
            return ArrayUtils.contains(p, params);
        }

        return false;
    }

    /**
     * ��request�в��ҷ�ҳ����
     * @param annotation
     * @param request
     * @return
     */
    private Pagination getPagination(JsonResponse annotation, HttpServletRequest request) {
        try {
            return (Pagination) request.getAttribute(annotation.page());
        } catch (ClassCastException ex) {
            LogUtils.warn(LOGGER, "��ҳ�������ʹ���, pager={0}", request.getAttribute(annotation.page()));
        }
        return null;
    }

    /**
     * Setter method for property <tt>outputters</tt>.
     * 
     * @param outputters value to be assigned to property outputters
     */
    public void setOutputters(Map<String, JsonOutputter> outputters) {
        this.outputters = outputters;
    }

    /**
     * Setter method for property <tt>jexlExpression</tt>.
     * 
     * @param jexlExpression value to be assigned to property jexlExpression
     */
    public void setJexlExpression(JexlExpression jexlExpression) {
        this.jexlExpression = jexlExpression;
    }
}
