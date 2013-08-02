/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2012 by Ericsson, all rights reserved.
 */

package org.okj.commons.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

public class ShiroHelper {

    public ShiroUser getShiroUser() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        return shiroUser;
    }
}
