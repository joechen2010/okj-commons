package org.okj.commons.web.jaas;


import java.security.Principal;

public final class UserPrincipal implements Principal {

  private String name;

  public UserPrincipal(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object o) {
    return (o instanceof UserPrincipal)
        && this.name.equalsIgnoreCase(((UserPrincipal) o).name);
  }

  public int hashCode() {
    return name.toUpperCase().hashCode();
  }

}

