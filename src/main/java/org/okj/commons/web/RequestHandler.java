package org.okj.commons.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handle the standard http request. just use it for demo and test.
 * 
 * @author ehanrli
 * 
 */
public interface RequestHandler {

    /**
     * 
     * @param target
     *            the path of url
     * @param request
     * @param response
     * @throws IOException
     *             any network error happen
     */
    void handle(String target, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
