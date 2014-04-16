package org.okj.commons.web.session;

import javax.servlet.http.HttpSession;

public class SessionFactory {

	private static SessionFactory factory;
	private SessionBean sessionBean = new SessionBean();

	private SessionFactory() {
	}

	static {
		factory = new SessionFactory();
	}

	public synchronized static SessionFactory getInstance() {
		if (factory == null) {
			factory = new SessionFactory();
		}
		return factory;
	}

	public void addLogin(HttpSession session, String user) {
		this.getSessionBean().getOnline().put(session.getId(), user);
		this.getSessionBean().getLogin().add(user);
	}

	public void removeLogin(HttpSession session, String user) {
		this.getSessionBean().getLogin()
				.remove(user);
		this.getSessionBean().getOnline().remove(session.getId());
	}

	public boolean isOnline(String user) {
		return this.getSessionBean().getLogin().contains(user);
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
}
