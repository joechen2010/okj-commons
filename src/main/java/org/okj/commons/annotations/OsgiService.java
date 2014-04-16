/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package org.okj.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>OSGi����ע���Annotations</p>
 * 
 * ��������ʹ��@OsgiService(interfaces = { ClassName.class })ע�⣬�ɽ���ǰBeanע�Ტ����Ϊһ��OSGi����<br>
 * <br>
 * @author Administrator
 * @version $Id: OsgiService.java, v 0.1 2012-7-14 ����11:21:21 Administrator Exp $
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OsgiService {
    /* bean��� */
    String name() default "";

    /* ����OSGi����Ľӿ� */
    Class<?>[] interfaces();
}
