package org.okj.commons.web;

import org.apache.http.StatusLine;

/**
 * A class to represent the response status of a http request.
 * 
 * @author ehanrli
 * 
 */
public class HttpStatus {

    private String message;

    private StatusLine statusLine;

    public HttpStatus(StatusLine statusLine) {
        super();
        this.statusLine = statusLine;
    }

    public String getReasonPhrase() {
        return statusLine.getReasonPhrase();
    }

    public int getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(statusLine.toString());
        if (message != null) {
            sb.append("\n" + message);
        }
        return sb.toString();
    }

    public boolean isOk() {
        return getStatusCode() == 200;
    }

    public boolean isFailtoAuth() {
        return getStatusCode() == 401;
    }

}
