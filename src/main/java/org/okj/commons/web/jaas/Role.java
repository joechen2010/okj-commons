package org.okj.commons.web.jaas;


import java.security.Principal;

public class Role implements Principal {
	private String rolename;

	public Role(String rolename) {
		this.rolename = rolename;
	}
	public boolean equals(Object object) {
		boolean flag = false;
		if (object == null)
			flag = false;
		if (this == object)
			flag = true;
		if (!(object instanceof Role))
			flag = false;
		if (object instanceof Role) {
			Role that = (Role) object;
			if (this.getName().equals(that.getName())) {
				flag = true;
			}
		}
		return flag;
	}
	public String toString() {
		return this.getName();
	}
	public int hashCode() {
		return rolename.hashCode();
	}
	public String getName() {
		return this.rolename;
	}
}
