/**
 * @(#)JsonResponse.java 2013-1-30
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
package org.okj.commons.web.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.okj.commons.constants.CommonsKeys;
import org.okj.commons.constants.ResponseFormat;

/**
 * JSON��ݸ�ʽ�������ע��
 * @author Administrator
 * @version $Id: JsonResponse.java, v 0.1 2013-1-30 ����12:23:21 Administrator Exp $
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonResponse {
    /** ���Ӳ������������һ������ʹ�ö��ע������ */
    String[] params() default {};

    /** �����JSON��ԭʼ��ݼ�ֵ�����ڴ��������л�ȡ���Դ */
    String result() default CommonsKeys.RESULT_KEY;

    /** ��ҳ������ֵ��Ĭ��page */
    String page() default CommonsKeys.PAGER_KEY;

    /** JSON��ݸ�ʽ */
    String responseFormat() default ResponseFormat.QUERY_RESULT;

    /** JSON���������ƣ���ʹ��JEXL���ʽ */
    String[] propertyNames() default {};
}
