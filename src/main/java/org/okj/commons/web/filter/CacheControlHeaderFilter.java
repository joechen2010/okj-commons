package org.okj.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.okj.commons.web.jackson.ParameterUtils;

/**
 * Set Client Cache Control Header for Response.
 * 
 * Set in web.xml
 * 
 * <filter> <filter-name>cacheControlHeaderFilter</filter-name> <filter-class>
 * com.ericsson.aop.adm.common.filter.CacheControlHeaderFilter</filter-class> <init-param>
 * <param-name>expiresSeconds</param-name> <param-value>31536000</param-value> </init-param> </filter> <filter-mapping>
 * <filter-name>cacheControlHeaderFilter</filter-name> <url-pattern>/img/*</url-pattern> </filter-mapping>
 * 
 * @author calvin
 */
public class CacheControlHeaderFilter implements Filter {

    public static final long DEFAULT_ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    private static final String PARAM_EXPIRES_SECONDS = "expiresSeconds";

    private boolean noCacheAll = false;

    private long expiresSeconds;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request; // NOSONAR
        String requestURI = httpRequest.getRequestURI();

        if (noCacheAll || requestURI.contains(".nocache.")) {
            setNoCacheControl(response);
        } else {
            setExpiresHeader((HttpServletResponse) response, expiresSeconds);// NOSONAR
        }
        chain.doFilter(request, response);
    }

    private void setNoCacheControl(ServletResponse response) {
        long now = System.currentTimeMillis();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setDateHeader("Date", now);

        // make sure the modified date is out
        httpResponse.setDateHeader("Expires", now - 86400000L);
        // http 1.0
        httpResponse.setHeader("Pragma", "no-cache");

        // http 1.1
        httpResponse.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) {
        expiresSeconds = ParameterUtils.getLong(filterConfig.getInitParameter(PARAM_EXPIRES_SECONDS),
                DEFAULT_ONE_YEAR_SECONDS);
        noCacheAll = ParameterUtils.getBool(filterConfig.getInitParameter("nocache"), false);
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
    }
}
