/**
 * @(#)LogUtils.java 2013-1-30
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
package org.okj.commons.logger;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

/**
 * 日志工具
 * @author Administrator
 * @version $Id: LogUtils.java, v 0.1 2013-1-30 上午11:05:21 Administrator Exp $
 */
public class LogUtils {
    /**
     * info日志
     * 
     * @param logger
     * @param message
     * @param args
     */
    public static void info(Logger logger, String message, Object... args) {
        if (logger != null) {
            if (logger.isInfoEnabled()) {
                logger.info(format(message, args));
            }
        }
    }

    /**
     * debug日志
     * 
     * @param logger
     * @param message
     * @param args
     */
    public static void debug(Logger logger, String message, Object... args) {
        if (logger != null) {
            if (logger.isDebugEnabled()) {
                logger.debug(format(message, args));
            }
        }
    }

    /**
     * error日志
     * 
     * @param logger
     * @param message
     * @param ex
     * @param args
     */
    public static void error(Logger logger, String message, Throwable ex, Object... args) {
        if (logger != null) {
            logger.error(format(message, args), ex);
        }
    }

    /**
     * warn日志
     * 
     * @param logger
     * @param message
     * @param args
     */
    public static void warn(Logger logger, String message, Object... args) {
        if (logger != null) {
            logger.warn(format(message, args));
        }
    }

    /**
     * 格式化消息
     * 
     * @param message
     * @param args
     * @return
     */
    protected static String format(String message, Object... args) {
        if (args != null && args.length > 0) {
            return new MessageFormat(message).format(args);
        }
        return message;
    }
}
