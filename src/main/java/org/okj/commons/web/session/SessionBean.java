package org.okj.commons.web.session;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionBean {

	private Map<String, String> online = new HashMap();
	private int accessCount = 0;
	private Set<String> login = new HashSet();

	public int getOnlineCount() {
		return this.getOnline().size();
	}

	public int getLoginCount() {
		return this.getLogin().size();
	}

	public Map<String, String> getOnline() {
		return online;
	}

	public void setOnline(Map<String, String> online) {
		this.online = online;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public Set<String> getLogin() {
		return login;
	}

	public void setLogin(Set<String> login) {
		this.login = login;
	}

}
