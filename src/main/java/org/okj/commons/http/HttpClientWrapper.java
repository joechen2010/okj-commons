package org.okj.commons.http;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.okj.commons.net.UrlUtils;
import org.okj.commons.test.Assert;

/**
 * A wrapper of Apache HTTP Component to simple the usage of common purpose.
 * 
 * @author ehanrli
 * 
 */
public class HttpClientWrapper {

    private DefaultHttpClient httpclient;

    private int connectionPoolSize = 2;

    @SuppressWarnings("unused")
    private int connectionTimeOut = 5000;

    private Logger logger = Logger.getLogger(HttpClientWrapper.class);

    /**
     * Create the wrapper class.
     */
    public HttpClientWrapper() {
        super();
        initSetting(); // dirty code.
    }

    public static HttpClientWrapper createSingleConnectionInstance() {
        HttpClientWrapper instance = new HttpClientWrapper();
        instance.setConnectionPoolSize(1);
        instance.initSetting();
        return instance;
    }

    @PostConstruct
    public void initSetting() {
        if (httpclient != null) {
            HttpClientUtils.release(httpclient);
        }
        httpclient = HttpClientUtils.createClient(connectionPoolSize);
    }

    /**
     * The pool size of connection manager.
     * 
     * @param connectionPoolSize
     */
    public void setConnectionPoolSize(int connectionPoolSize) {
        Assert.isTrue(connectionPoolSize >= 1, "The size of pool should be great than 1");
        this.connectionPoolSize = connectionPoolSize;
        initSetting();
    }

    /**
     * The time out for a connection.
     * 
     * @param connectionTimeOut
     */
    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    /**
     * set proxy
     * 
     * @param proxyServer
     *            if it's null, no proxy is used.
     */
    public void setEnableProxy(String proxyServer) {
        HttpClientUtils.setProxy(httpclient, proxyServer);
    }

    /**
     * Run a http delete operation
     * 
     * @param url
     * @param params
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doDelete(String url, Map<String, String> params) throws IOException {
        HttpDelete method = new HttpDelete(UrlUtils.expandUrl(url, params));
        return commit(method);
    };

    /**
     * For http get, only 200 is normal status, so don't need return other extra infomation.
     * 
     * @param url
     * @param params
     * @return response content
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doGet(String url, Map<String, String> params) throws IOException {

        String requestUrl = UrlUtils.expandUrl(url, params);
        logger.debug(" get url :" + requestUrl);
        HttpGet method = new HttpGet(requestUrl);
        return commit(method);
    };

    /**
     * Do a general put request with a json or xml string .
     * 
     * @param url
     * @param params
     * @param body
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doPut(String url, Map<String, String> params, String body) throws IOException {
        return doPut(url, params, new StringEntity(body));
    }

    /**
     * run a general put requeest with a string
     * 
     * @param url
     * @param params
     * @param body
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doPut(String url, Map<String, String> params, StringEntity body) throws IOException {
        HttpPut method = new HttpPut(UrlUtils.expandUrl(url, params));
        method.setEntity(body);
        return commit(method);
    }

    /**
     * Do a general post request with binary data .
     * 
     * @param url
     * @param headers
     * @param params
     * @param body
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doPost(String url, Map<String, String> headers, Map<String, String> params, byte[] body)
            throws IOException {
        return doPost(url, headers, params, createEntity(body));
    }

    private ByteArrayEntity createEntity(byte[] body) {
        ByteArrayEntity entity = new ByteArrayEntity(body);
        entity.setContentType("binary/octet-stream");
        entity.setContentEncoding(HTTP.UTF_8);
        return entity;
    }

    /**
     * Do a general post request with binary data .
     * 
     * @param url
     * @param params
     * @param body
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doPost(String url, Map<String, String> params, byte[] body) throws IOException {
        return doPost(url, null, params, createEntity(body));
    }

    /**
     * Do a general post request with a xml or json string. you might have to define the content header in some
     * situation, try other methods.
     * 
     * @param url
     * @param params
     * @param body
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doPost(String url, Map<String, String> params, String body) throws IOException {
        return doPost(url, null, params, new StringEntity(body));
    }

    /**
     * Do a general post request with a HttpEntity object.
     * 
     * @param url
     * @param headers
     * @param params
     * @param entity
     * @return
     * @throws IOException
     *             any network exception happen
     */
    public HttpStatus doPost(String url, Map<String, String> headers, Map<String, String> params, HttpEntity entity)
            throws IOException {
        HttpPost method = new HttpPost(UrlUtils.expandUrl(url, params));
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                method.addHeader(entry.getKey(), entry.getValue());
            }
        }
        method.setEntity(entity);
        return commit(method);
    }
    
    public HttpResponse executePost(String url, Map<String, String> headers, Map<String, String> params, HttpEntity entity)
    		throws IOException {
    	HttpPost method = new HttpPost(UrlUtils.expandUrl(url, params));
    	if (headers != null) {
    		for (Map.Entry<String, String> entry : headers.entrySet()) {
    			method.addHeader(entry.getKey(), entry.getValue());
    		}
    	}
    	method.setEntity(entity);
    	return execute(method);
    }

    private HttpStatus commit(HttpUriRequest method) throws IOException {
        Assert.notNull(httpclient, "don't init the setting, make sure you call initsetting method first !");
        HttpStatus status;
        try {
            HttpResponse resp = httpclient.execute(method);
            status = new HttpStatus(resp.getStatusLine());
            if (resp.getEntity() != null) {
                status.setMessage(EntityUtils.toString(resp.getEntity(), "UTF-8"));
            }
        } catch (IOException e) {
            // cancel the connection when the error happen
            method.abort();
            throw e;
        }
        return status;
    }
    
    private HttpResponse execute(HttpUriRequest method) throws IOException {
    	Assert.notNull(httpclient, "don't init the setting, make sure you call initsetting method first !");
    	HttpResponse resp;
    	try {
    		resp = httpclient.execute(method);
    	} catch (IOException e) {
    		// cancel the connection when the error happen
    		method.abort();
    		throw e;
    	}
    	return resp;
    }

    /**
     * close the connection pool now.
     */
    public void closeConnectionPool() {
        httpclient.getConnectionManager().shutdown();
    }

    /**
     * @param string
     * @param string2
     */
    public void setAuth(String userName, String password) {
        Credentials creds = new UsernamePasswordCredentials(userName, password);
        httpclient.getCredentialsProvider()
                .setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
    }

}
