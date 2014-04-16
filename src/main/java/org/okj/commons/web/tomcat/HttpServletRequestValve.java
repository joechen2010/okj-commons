package org.okj.commons.web.tomcat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

/**
 * A Tomcat Valve that obtains the client's HttpServletRequest and saves it in a
 * ThreadLocal variable. It allows the WebCallbackHandler to populate the
 * HttpServletRequestCallback.
 * 
 * @author xchezhe 
 * @version  
 * 
 * 		<Valve className="org.apache.catalina.authenticator.SingleSignOn" />

		<Valve className="com.ericsson.demo.tomcat.HttpServletRequestValve" />
 * 
 * 
 */
public class HttpServletRequestValve extends ValveBase
{
   /** ThreadLocal to save the HttpServletRequest. */
   public static ThreadLocal<HttpServletRequest> httpRequest;

   /**
    * Default constructor.
    */
   public HttpServletRequestValve()
   {
   }

   /**
    * @see org.apache.catalina.Valve#invoke(org.apache.catalina.connector.Request,
    *      org.apache.catalina.connector.Response)
    */
   public void invoke(Request request, Response response) throws IOException,
         ServletException
   {
      try
      {
         // Set the ThreadLocal
		validateThreadLocalData();
		httpRequest.set(request.getRequest());

         // Perform the request
         getNext().invoke(request, response);
      }
      finally
      {
         // Unset the ThreadLocal
         httpRequest.set(null);
      }
   }
   
   public static final HttpServletRequest getThreadLocalRequest() {
	      validateThreadLocalData();
	      return httpRequest.get();
   }
   
   private static void validateThreadLocalData() {
	    if (httpRequest == null) {
	    	httpRequest = new ThreadLocal<HttpServletRequest>();
	    }
   }
}
