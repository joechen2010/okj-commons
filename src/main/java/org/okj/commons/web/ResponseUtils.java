package org.okj.commons.web;

import java.util.List;

import org.okj.commons.web.jackson.JacksonMapper;
import org.okj.commons.web.jackson.JsonMapper;
import org.okj.commons.web.jackson.JsonMapperFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

public final class ResponseUtils {
    private static JsonMapper jsonMapper = JsonMapperFactory.get();
    private static JsonMapper noUndersocreJsonMapper = new JacksonMapper("noUnderScore");

    private ResponseUtils() {

    }

    public static ResponseEntity<String> jsonWrapper(List<?> results) {
        if (CollectionUtils.isEmpty(results)) {
            return new ResponseEntity<String>("Cannot find record", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<String>(jsonMapper.toJson(results), createJsonHeader(), HttpStatus.OK);
    }

    public static ResponseEntity<String> jsonWrapper(Object results) {
        if (results == null) {
            return new ResponseEntity<String>("Cannot find record", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>(jsonMapper.toJson(results), createJsonHeader(), HttpStatus.OK);
    }

    public static ResponseEntity<String> noUndersocreJsonWrapper(Object results) {
        if (results == null) {
            return new ResponseEntity<String>("Cannot find record", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>(noUndersocreJsonMapper.toJson(results), createJsonHeader(), HttpStatus.OK);
    }

    public static ResponseEntity<String> jsonWrapper(String results) {
        if (results == null || results.trim().length() == 0) {
            return new ResponseEntity<String>("Cannot find record", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<String>(results, createJsonHeader(), HttpStatus.OK);
    }

    public static ResponseEntity<String> htmlWrapper(String results) {
        if (results == null || results.trim().length() == 0) {
            return new ResponseEntity<String>("Cannot find record", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<String>(results, createHtmlHeader(), HttpStatus.OK);
    }

    public static ResponseEntity<String> ok(String message) {
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    public static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    public static HttpHeaders createHtmlHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html;charset=UTF-8");
        return headers;
    }
}
