/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2012 by Ericsson, all rights reserved.
 */

package org.okj.commons.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 7482286172936237027L;

    private Long id;

    private String name;

    private volatile int hashCode = 0;

    public ShiroUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShiroUser)) {
            return false;
        }
        ShiroUser user = (ShiroUser) obj;
        return id.longValue() == user.getId().longValue() && name.equals(user.getName());
    }

    @Override
    public int hashCode() {
        final int multiplier = 3;
        if (hashCode == 0) {
            int code = 123;
            code = multiplier * code + id.intValue();
            code = multiplier * code + name.hashCode();
            hashCode = code;
        }
        return hashCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
