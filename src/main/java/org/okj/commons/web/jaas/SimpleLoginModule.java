package org.okj.commons.web.jaas;


import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.servlet.http.HttpServletRequest;

import org.okj.commons.web.tomcat.HttpServletRequestValve;



@SuppressWarnings({"rawtypes","unused"})
public abstract class SimpleLoginModule implements LoginModule {
  private boolean isAuthenticated = false;
  private boolean commitSucceeded = false;  
  private CallbackHandler callbackHandler;
  private Subject subject;
  private UserPrincipal user;
  private Role role;
  private Map sharedState;
  private Map options;
  
  
  public void initialize(Subject subject, CallbackHandler callbackHandler,
      Map sharedState, Map options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState=sharedState;
    this.options=options;
  }

  public boolean login() throws LoginException {
	  
	  if (callbackHandler == null){
          throw new LoginException("No CallBackHandler!");
       }
    try {
      NameCallback nameCallback = new NameCallback("username");
      PasswordCallback passwordCallback = new PasswordCallback("password",
          false);
      final Callback[] calls = new Callback[] { nameCallback, passwordCallback };
      callbackHandler.handle(calls);
      String username = nameCallback.getName();
      String password = String.valueOf(passwordCallback.getPassword());
      
      boolean flag = retrieveUser(username, password);
      if (flag) { 
    	  user = new UserPrincipal(username);
    	  role=new Role("seDemo");
    	  isAuthenticated = true;
      } else {
        throw new LoginException("user or password is wrong");
      }

    } catch (IOException e) {
      throw new LoginException("no such user");
    } catch (UnsupportedCallbackException e) {
      throw new LoginException("login failure");
    }
    return isAuthenticated;
  }

  public boolean commit() throws LoginException {
    if (isAuthenticated) {
    	if (!subject.getPrincipals().contains(user)){
           subject.getPrincipals().add(user);
        }
    	if (!subject.getPrincipals().contains(role)){
            subject.getPrincipals().add(role);
         }
    	HttpServletRequest request = (HttpServletRequest) HttpServletRequestValve.getThreadLocalRequest();
    } else {
      throw new LoginException("Authentication failure");
    }
    commitSucceeded = true;
    return isAuthenticated;
  }

  public boolean abort() throws LoginException {
	  if (isAuthenticated == false){
         return false;
      }
      else if (isAuthenticated == true && commitSucceeded == false){
         // login succeeded but overall authentication failed
    	  isAuthenticated = false;
          user = null;
          role=null;
      }
      else{
         logout();
      }
      return true;
  }

  public boolean logout() throws LoginException {
    subject.getPrincipals().remove(user);
    user = null;
    role=null;
    return true;
  }
  
  public abstract boolean retrieveUser(String userName, String password);
  
}
