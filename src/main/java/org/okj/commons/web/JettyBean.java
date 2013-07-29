package org.okj.commons.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;

/**
 * create a quick mock server based on the embeded jetty server.
 * 
 * @author ehanrli
 * 
 */
public class JettyBean {

    private int port;

    private RequestHandler handler;

    private Server server;

    private String ip;

    private SecurityHandler securityHandler;

    /**
     * Create a mock server.
     * 
     * @param port
     * @param handler
     * @return
     */
    public static JettyBean create(int port, RequestHandler handler) {
        JettyBean instance = new JettyBean();
        instance.port = port;
        instance.handler = handler;
        instance.init();
        return instance;
    }

    public static JettyBean create(String ip, int port, RequestHandler handler) {
        JettyBean instance = new JettyBean();
        instance.ip = ip;
        instance.port = port;
        instance.handler = handler;
        instance.init();
        return instance;
    }

    /**
     * Create a jetty server on the specified port.
     * 
     * @param port
     * @return
     */
    public static JettyBean create(int port) {
        JettyBean instance = new JettyBean();
        instance.port = port;
        return instance;
    }

    /**
     * Bind a request handler for the incoming request.
     * 
     * @param handler
     */
    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    public void init() {

        server = new Server();

        HandlerCollection handlers = new HandlerCollection();
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setHost(StringUtils.isNotBlank(ip) ? ip : "127.0.0.1");
        server.addConnector(connector);

        if (securityHandler != null) {
            handlers.addHandler(securityHandler);
        }

        Handler h = new AbstractHandler() {

            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request,
                    HttpServletResponse response) throws IOException, ServletException {
                if (handler != null) {
                    handler.handle(target, request, response);
                }
                ((Request) request).setHandled(true);
            }

        };
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        context.setHandler(h);
        handlers.addHandler(h);

        server.setHandler(handlers);
    }

    public void setBasicAuth(String userName, String password) {
        securityHandler = basicAuth(userName, password, "Private!");
    }

    private static final SecurityHandler basicAuth(String username, String password, String realm) {

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(new String[] { "user" });
        constraint.setAuthenticate(true);

        ConstraintMapping cm = new ConstraintMapping();
        cm.setConstraint(constraint);
        cm.setPathSpec("/*");

        /*
         * SecurityHandler csh = new SecurityHandler(); csh.setAuthenticator(new BasicAuthenticator());
         * csh.setConstraintMappings(new ConstraintMapping[] { cm }); HashUserRealm userRealm = new
         * HashUserRealm(realm); userRealm.put(username, password); userRealm.addUserToRole(username, "user");
         * csh.setUserRealm(userRealm);
         */
        HashLoginService l = new HashLoginService();
        l.putUser(username, Credential.getCredential(password), new String[] { "user" });
        l.setName(realm);
        ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
        csh.setAuthenticator(new BasicAuthenticator());
        csh.setConstraintMappings(new ConstraintMapping[] { cm });
        csh.setRealmName(realm);
        csh.setLoginService(l);
        csh.setStrict(true);
        return csh;

    }

    /**
     * Start the server
     * 
     * @throws Exception
     *             any exception
     */
    public void start() throws Exception {
        if (server == null) {
            init();
        }
        server.start();
    }

    /**
     * Stop the server
     * 
     * @throws Exception
     *             any exception
     */
    public void stop() throws Exception {
        assertTheServerIsCreated();
        server.stop();
    }

    private void assertTheServerIsCreated() {
        if (server == null) {
            throw new IllegalStateException("server is not initialized !!!");
        }
    }

}
