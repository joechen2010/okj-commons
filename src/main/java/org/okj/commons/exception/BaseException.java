package org.okj.commons.exception;

/**
* Base checked exception class for all MEDecision checked exceptions.
* The returnStatus attribute can then be interrogated for more details about the error.
* @author dhoman
* 
* Creation date: 6/7/2007 9 AM
* Copyright (c) 2007 MEDecision, Inc.  All rights reserved.
*/

public class BaseException extends Exception {

   /** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -3164921516258782060L;
    
    public BaseException() {
    	super();
     }

    public BaseException(String message) {
    	super(message);
     }

    public BaseException(String message, Throwable cause) {
    	super(message, cause);
     }
    
    public BaseException(Class<?> clazz, String cause) {
    	super(cause);
    }

    public BaseException(Throwable cause) {
    	super(cause);
     }

}
