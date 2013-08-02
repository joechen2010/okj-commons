package org.okj.commons.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

public class UserManagerRealm extends AuthorizingRealm {

    private static Logger logger = Logger.getLogger(UserManagerRealm.class);


    public UserManagerRealm() {
        setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
        // setCredentialsMatcher(new SimpleCredentialsMatcher());
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();
        return null;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();

        return null;
    }

    /**
     * Clear the subject's cache in shiro.
     */
    public void clearCurrentSubjectInfoInCache() {
        clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * Clear subject info in cache by user
     * 
     * @param userName
     */
    public void clearSubjectInfoInCache(String userName) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(userName, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * Clear all subjects cache in shiro.
     */
    public void clearAllSubjectInfoInCache() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

}
