/**
 * @(#)ResponseHelper.java 2013-1-30
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
package org.okj.commons.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;

/**
 * HTTP锟斤拷应锟斤拷锟斤拷
 * @author Administrator
 * @version $Id: ResponseHelper.java, v 0.1 2013-1-30 锟斤拷锟斤拷1:14:48 Administrator Exp $
 */
public class ResponseHelper {
    private static final Logger LOGGER = Logger.getLogger(ResponseHelper.class);

    /**
     * 锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟接︼拷锟斤拷锟�
     * @param content
     * @param response
     */
    public static void writeStringToResponse(String content, HttpServletResponse response) {
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        try {
           // response.setCharacterEncoding("UTF-8");
            //response.setContentType("application/octet-stream");
            response.setContentType("text/plain");
            OutputStream out = response.getOutputStream();
            IOUtils.write(content, out, "UTF-8");
            out.flush();
        } catch (Exception ex) {
            LogUtils.error(LOGGER, "", ex);
            try {
                response.sendError(500);
            } catch (IOException e1) {
                LogUtils.error(LOGGER, "", e1);
            }
        }
    }
}
