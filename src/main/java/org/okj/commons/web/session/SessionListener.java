package org.okj.commons.web.session;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 *  Add to web.xml in tomcat 
 * 
 * 
 *  <listener>
		<listener-class>com.ericsson.demo.session.SessionListener</listener-class>
	</listener>
 * 
 * 
 * */


public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener,ServletRequestListener {
	
	private HttpServletRequest request;

	public void sessionCreated(HttpSessionEvent event) {

		HttpSession session = event.getSession();

		SessionBean sessionBean = SessionFactory.getInstance().getSessionBean();

		sessionBean.setAccessCount(sessionBean.getAccessCount() + 1);

		sessionBean.getOnline().put(session.getId(), null);

	}

	public void sessionDestroyed(HttpSessionEvent event) {

		HttpSession session = event.getSession();

		SessionBean sessionBean = SessionFactory.getInstance().getSessionBean();

		sessionBean.getLogin().remove(
				sessionBean.getOnline().get(session.getId()));

		sessionBean.getOnline().remove(session.getId());
		
	}

	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void requestInitialized(ServletRequestEvent e) {
		this.request = (HttpServletRequest)e.getServletRequest();
	}
	
	
}
