/**
 * Storevm.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package org.okj.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����OSGi��WS����
 * @author Administrator
 * @version $Id: OsgiWebService.java, v 0.1 2012-11-4 ����5:18:22 Administrator Exp $
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OsgiWebService {
    /* WS�ķ�������ַ */
    String address() default "0.0.0.0";

    /* WS�Ķ˿� */
    int port() default 8082;

    /* WS��������·�� */
    String context() default "services";

    /* ���� */
    String[] props() default {};
}
