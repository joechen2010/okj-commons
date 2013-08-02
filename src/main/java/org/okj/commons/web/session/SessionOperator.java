/**
 * @(#)SessionOperator.java 2013-1-30
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
package org.okj.commons.web.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * �Ự��ش���
 * @author Administrator
 * @version $Id: SessionOperator.java, v 0.1 2013-1-30 ����11:33:12 Administrator Exp $
 */
public class SessionOperator {
    /* logger */
    private static final Logger LOGGER         = Logger.getLogger(SessionOperator.class);

    /* �Ự��ֵ */
    public static final String  SESSION_USER   = "SESSION_USER";

    public static final String  IS_LOGIN       = "IS_LOGIN";

    /* cookies����ʱ�䣬Ĭ��1�� */
    public static final int     COOKIE_MAX_AGE = 60 * 60 * 24 * 365;

    /**
     * ������¼
     * 
     * @param request
     * @param session
     * @return
     */
    public static boolean setupLoginSession(HttpServletRequest request, Object session) {
        boolean success = false;
        try {
            if (request != null) {
                HttpSession sess = request.getSession();
                sess.setAttribute(SESSION_USER, session);
                sess.setAttribute(IS_LOGIN, true);
                success = true;
            }
        } catch (Exception ex) {
            LOGGER.error("������¼����ʱ�����쳣", ex);
        }

        return success;
    }

    /**
     * ���cookies
     * 
     * @param response
     * @param key
     * @param value
     */
    public static void addCookies(HttpServletResponse response, String key, String value) {
        Cookie c = new Cookie(key, value);
        c.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(c);
    }

    /**
     * ɾ��cookies
     * 
     * @param response
     * @param key
     */
    public static void removeCookies(HttpServletRequest request, HttpServletResponse response,
                                     String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), name)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
    }

    /**
     * ����session�Ự�еĵ�¼����
     * 
     * @param request
     * @param clazz
     * @return
     */
    public static <T> T getLoginSession(HttpServletRequest request, Class<T> clazz) {
        if (request != null) {
            HttpSession sess = request.getSession();
            return (T) sess.getAttribute(SESSION_USER);
        }
        return (T) null;
    }

    /**
     * ��鵱ǰ�Ƿ��ڵ�¼״̬
     * 
     * @param request
     * @return
     */
    public static boolean isLogin(HttpServletRequest request) {
        boolean islogin = false;
        if (request != null) {
            HttpSession session = request.getSession();
            return BooleanUtils.toBoolean((Boolean) session.getAttribute(IS_LOGIN));
        }
        return islogin;
    }

    /**
     * ���ûỰ������ֵ
     * 
     * @param request
     * @param key
     * @param value
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        if (request != null) {
            HttpSession session = request.getSession();
            session.setAttribute(key, value);
        }
    }

    /**
     * ��session�л�ȡֵ
     * @param key
     * @return
     */
    public static <T> T getAttribute(HttpServletRequest request, String key) {
        if (request != null) {
            HttpSession session = request.getSession();
            return (T) session.getAttribute(key);
        }
        return (T) null;
    }
}
