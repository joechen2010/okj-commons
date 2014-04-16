/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package org.okj.commons.annotations;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.okj.commons.annotations.constants.AnnotationConstants;
import org.osgi.framework.BundleContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.osgi.service.exporter.OsgiServiceRegistrationListener;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;

/**
 * ����Osgi����ע���beanǰ�ô�����
 * @author Administrator
 * @version $Id: OsgiServiceBeanPostProcessor.java, v 0.1 2011-12-31 ����9:29:29 Administrator Exp $
 */
public class OsgiServiceBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
                                                                                            implements
                                                                                            BundleContextAware,
                                                                                            BeanClassLoaderAware,
                                                                                            DisposableBean,
                                                                                            ApplicationContextAware {
    /** ��־ */
    protected static final Logger             LOGGER               = Logger
                                                                       .getLogger(OsgiServiceBeanPostProcessor.class);

    /** bundle context */
    private BundleContext                     bundleContext;

    /** classloader */
    private ClassLoader                       classLoader;

    /** listeners */
    private OsgiServiceRegistrationListener[] listeners            = new OsgiServiceRegistrationListener[0];

    /** inner bean service registrations */
    private final Collection                  serviceRegistrations = Collections
                                                                       .synchronizedCollection(new ArrayList());

    /** visibility monitor */
    private final Object                      monitor              = new Object();

    /**
     * destroyed flag - used since some CM implementations still call the service even though it was unregistered
     */
    private boolean                           destroyed            = false;

    /* beanFactory */
    private ApplicationContext                applicationContext;

    /**
     *
     * @see org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
                                                                              throws BeansException {
        Object resultBean = bean;
        //����bean��annotation
        OsgiService service = getAnnotation(bean, OsgiService.class);
        //���annotation�����ڣ��������?ֱ�ӷ���
        if (service == null || service.interfaces().length <= 0) {
            return bean;
        }

        //����Ƿ��Ѿ�ע��OSGi����
        boolean register = hasServiceRegister(service.interfaces());
        if (register) {
            //����Ѿ�ע���ˣ���ֱ�ӷ���bean
            LOGGER.warn("��bean�Ѿ�ע��ΪOSG����bean:" + bean);
            return bean;
        }

        //����Ƿ���Ҫ����WS
        OsgiWebService webService = getAnnotation(bean, OsgiWebService.class);

        //������ע��Osgi���񣬷���OsgiServiceFactoryBean����
        resultBean = createExporter(beanName, bean, service, webService);
        try {
            //��ע���OSGi������뼯���У��Ա�֮��ע�����
            serviceRegistrations.add(resultBean);
        } catch (Exception ex) {
            throw new BeanCreationException("Cannot publish bean for beanName " + beanName, ex);
        }

        return bean;
    }

    /**
     *
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        //��bean��ע��ʱ��Ҫ����ǰ�Ѿ�ע���Osgi����ȫ�����
        synchronized (monitor) {
            //����Ѿ���ٹ��ˣ���ֱ���˳�
            if (destroyed) {
                return;
            }
            if (!serviceRegistrations.isEmpty()) {
                //ע�����
                for (Object obj : serviceRegistrations) {
                    if (obj instanceof OsgiServiceFactoryBean) {
                        OsgiServiceFactoryBean bean = (OsgiServiceFactoryBean) obj;
                        bean.destroy();
                    }
                }
                destroyed = true;
            }
        }
        serviceRegistrations.clear();

        //����bean��destroy-method����
        if (applicationContext != null
            && applicationContext instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) applicationContext).close();
        }
    }

    /**
     * ���ӿ��Ƿ��Ѿ�ע����
     * 
     * @param interfaces
     * @return
     */
    protected boolean hasServiceRegister(Class<?>[] interfaces) {
        for (Class<?> interfaceClass : interfaces) {
            ServiceReference ref = (ServiceReference)bundleContext.getServiceReference(interfaceClass.getName());
            if (ref != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * ����Osgi�����bean����
     * 
     * @param beanName bean���
     * @param bean 
     * @param service OsgiService��annotation
     * @param webService OsgiWebService��annotation
     * @return
     */
    protected OsgiServiceFactoryBean createExporter(String beanName, Object bean,
                                                    OsgiService service, OsgiWebService webService) {
        OsgiServiceFactoryBean exporter = new OsgiServiceFactoryBean();

        exporter.setBeanClassLoader(classLoader);
        exporter.setBeanName(beanName);
        exporter.setBundleContext(bundleContext);
        if (service.interfaces().length > 0) {
            exporter.setInterfaces(service.interfaces());
        }
        exporter.setListeners(listeners);
        exporter.setTarget(bean);
        exporter.setBeanFactory(applicationContext);

        //����WS����
        if (webService != null && service.interfaces().length > 0) {
            String address = getAddressURL(webService, service.interfaces()[0]);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("����WS����ĵ�ַURL��url=" + address);
            }

            //����
            Map<String, String> props = new HashMap<String, String>();
            props.put(AnnotationConstants.SERVICE_EXPORTED_INTERFACES, "*");
            props.put(AnnotationConstants.SERVICE_EXPORTED_CONFIGS, "org.apache.cxf.ws");
            props.put(AnnotationConstants.WS_ADDRESS, address);

            //��Ӹ�������
            Map<String, String> append = appendProperties(webService);
            props.putAll(append);

            //���÷���OSGi����
            exporter.setServiceProperties(props);
        }

        try {
            exporter.afterPropertiesSet();
        } catch (Exception ex) {
            throw new BeanCreationException("Cannot publish bean for beanName " + beanName, ex);
        }
        return exporter;
    }

    /**
     * ����WS�ĵ�ַURL
     * 
     * @param webService
     * @return
     */
    protected String getAddressURL(OsgiWebService webService, Class<?> interfaceClass) {
        StringBuffer url = new StringBuffer("http://");
        url.append(webService.address()).append(":").append(webService.port()).append("/")
            .append(webService.context()).append("/").append(interfaceClass.getSimpleName());
        return url.toString();
    }

    /**
     * 
     * 
     * @param webService
     * @return
     */
    protected Map<String, String> appendProperties(OsgiWebService webService) {
        Map<String, String> map = new HashMap<String, String>();

        String[] props = webService.props();
        for (String prop : props) {
            String[] keyValue = prop.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    /**
     * ����ָ��bean�ϵ�annotation
     * 
     * @param bean
     * @param clazz
     * @return
     */
    protected <T extends Annotation> T getAnnotation(Object bean, Class<T> clazz) {
        return AnnotationUtils.findAnnotation(bean.getClass(), clazz);
    }

    /**
     *
     * @see org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(java.lang.ClassLoader)
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * @param listeners The listeners to set.
     */
    public void setListeners(OsgiServiceRegistrationListener[] listeners) {
        this.listeners = listeners;
    }

    /** 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
