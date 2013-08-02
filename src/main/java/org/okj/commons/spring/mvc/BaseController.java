package org.okj.commons.spring.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.okj.commons.web.jackson.JsonMapper;
import org.okj.commons.web.jackson.JsonMapperFactory;
import org.okj.commons.web.jackson.JsonValidator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
    protected final Logger logger = Logger.getLogger(getClass());
    protected JsonMapper jsonMapper = JsonMapperFactory.get();

    public void validateJsonHeader(String contentType) {
        Assert.isTrue(contentType != null && contentType.startsWith("application/json"),
                "Cannot find the 'application/json' in request header.");

    }

    public void validateJsonBody(String value) {
        JsonValidator.isJsonBody(value, "The content is not a valid json string ! [" + value + "]");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleExceptionFor400(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        logger.error("Bad Request for data format:" + ex.getMessage(), ex);
        response.getWriter().write(ex.getMessage());
    }

    // @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    // @ExceptionHandler(IllegalArgumentException.class)
    // public void handleExceptionFor500(IllegalStateException ex,
    // HttpServletResponse response) throws IOException {
    // logger.error("Internal error:" + ex.getMessage(), ex);
    // response.getWriter().write(ex.getMessage());
    // }

    /**
     * @param addr
     * @param result
     */
    public void debugObject(Logger log, String msg, Object obj) {
        if (log.isDebugEnabled()) {
            log.debug(msg + jsonMapper.toJson(obj));
        }
    }

    /**
     * @param entity
     * @param contentType
     */
    public void validateJsonInput(HttpEntity<String> entity, String contentType) {
        validateJsonHeader(contentType);
        validateJsonBody(entity.getBody());
    }

}
