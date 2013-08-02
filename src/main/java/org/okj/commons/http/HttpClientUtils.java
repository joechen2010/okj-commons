/**
 * 
 */
package org.okj.commons.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.okj.commons.test.Assert;

import com.google.common.collect.Lists;

/**
 * 
 */
public class HttpClientUtils {

    /**
     * @param httpclient
     * @param i
     */
    public static void setKeepAlive(final DefaultHttpClient httpclient, final int time) {
        httpclient.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = time;
                }
                return keepAlive;
            }
        });
    }

    /**
     * @param httpclient
     */
    public static void disableRetry(DefaultHttpClient httpclient) {
        httpclient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
            public boolean retryRequest(final IOException exception, int executionCount, final HttpContext context) {
                return false;
            }
        });
    }

    /**
     * @param connectionPoolSize
     * @return
     */
    public static DefaultHttpClient createClient(int connectionPoolSize) {
        ThreadSafeClientConnManager cm = null;
        if (connectionPoolSize > 1) {
            cm = new ThreadSafeClientConnManager();
            cm.setMaxTotal(connectionPoolSize);
            // it's 2 by default, which means only 2 connections with a same
            // host.
            cm.setDefaultMaxPerRoute(connectionPoolSize);
        }

        DefaultHttpClient httpclient = new DefaultHttpClient(cm);
        HttpClientUtils.disableRetry(httpclient);
        HttpClientUtils.setKeepAlive(httpclient, 5000);
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        return httpclient;
    }

    /**
     * @param httpclient
     */
    public static void release(DefaultHttpClient httpclient) {
        if (httpclient != null) {
            httpclient.getConnectionManager().shutdown();
            httpclient.clearRequestInterceptors();
            httpclient.clearResponseInterceptors();
        }
    }

    /**
     * 
     * @param httpclient
     * @param userName
     * @param password
     */
    public static void setAuth(final DefaultHttpClient httpclient, String userName, String password) {
        Credentials creds = new UsernamePasswordCredentials(userName, password);
        httpclient.getCredentialsProvider()
                .setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
    }

    /**
     * @param httpclient
     * @param proxyServer
     */
    public static void setProxy(final DefaultHttpClient httpclient, String proxyServer) {
        if (proxyServer == null || proxyServer.trim().length() == 0) {
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
            return;
        }
        Assert.assertTrue(proxyServer.contains(":"), "No port info in proxy string");
        String host = proxyServer.substring(0, proxyServer.lastIndexOf(':'));
        String port = proxyServer.substring(proxyServer.lastIndexOf(':') + 1);
        HttpHost proxyHost = new HttpHost(host, Integer.parseInt(port));
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
    }

    /**
     * @param method
     * @param httpclient
     */
    public static void abortConnection(HttpUriRequest method, HttpClient httpclient) {
        if (method != null) {
            method.abort();
        }
        if (httpclient != null) {
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * 
     * @param hrb
     * @param httpclient
     */
    public static void abortConnection(final HttpRequestBase hrb, final HttpClient httpclient) {
        if (hrb != null) {
            hrb.abort();
        }
        if (httpclient != null) {
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * @param method
     * @param httpclient
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static HttpResponse executeWithBasicAuth(HttpUriRequest method, HttpClient httpclient)
            throws ClientProtocolException, IOException {
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();

        URI uri = method.getURI();
        HttpHost targetHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());

        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        BasicHttpContext localcontext = new BasicHttpContext();
        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
        return httpclient.execute(targetHost, method, localcontext);
    }

    /**
     * @param params
     * @return
     */
    public static HttpEntity createEntity(Map<String, String> params) {

        List<NameValuePair> nvps = Lists.newArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            return new UrlEncodedFormEntity(nvps, HTTP.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param body
     * @return
     */
    public static HttpEntity createEntity(byte[] body) {
        ByteArrayEntity entity = new ByteArrayEntity(body);
        entity.setContentType("binary/octet-stream");
        entity.setContentEncoding(HTTP.UTF_8);
        return entity;
    }

}
