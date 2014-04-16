package org.okj.commons.http;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.okj.commons.net.UrlUtils;

import com.google.common.collect.Lists;

/**
 * A wrapper of Apache HTTP Component to simple the usage of common purpose.
 * 
 * @author ehanrli
 * 
 */
public class SimpleHttpClient {

    private Logger logger = Logger.getLogger(SimpleHttpClient.class);

    private String[] authCritendial;

    private String proxy;

    private int soTimeout;

    /**
     * Create the wrapper class.
     */
    public SimpleHttpClient() {
        super();
    }

    public static SimpleHttpClient create() {
        return new SimpleHttpClient();
    }

    /**
     * 
     * @param userName
     * @param password
     */
    public void setAuth(String userName, String password) {
        this.authCritendial = new String[] { userName, password };
    }

    /**
     * set proxy
     * 
     * @param proxyServer
     *            if it's null, no proxy is used.
     */
    public void setEnableProxy(String proxyServer) {
        this.proxy = proxyServer;
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

    public HttpStatus doGet(String url) throws IOException {
        logger.debug(" get url :" + url);
        HttpGet method = new HttpGet(url);
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
        return doPost(url, headers, params, HttpClientUtils.createEntity(body));
    }

    public HttpStatus doPost(String url, Map<String, String> headers, Map<String, String> formParameters)
            throws IOException {
        return doPost(url, headers, null, HttpClientUtils.createEntity(formParameters));
    }

    /**
     * 
     * @param url
     * @param fileName
     * @param data
     * @param params
     * @return
     * @throws IOException
     */
    public HttpStatus doPostMultiPart(String url, String fileName, byte[] data, Map<String, String> params)
            throws IOException {
        return doPostMultiPart(url, fileName, "file", data, params);
    }

    /**
     * 
     * @param url
     * @param fileName
     * @param fileFieldName
     *            the name store the file byte data.
     * @param fileData
     * @param params
     * @return
     * @throws IOException
     */
    public HttpStatus doPostMultiPart(String url, String fileName, String fileFieldName, byte[] fileData,
            Map<String, String> params) throws IOException {
        MultipartEntity multiPartEntity = new MultipartEntity();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                multiPartEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
            }
        }

        multiPartEntity.addPart(fileFieldName, new ByteArrayBody(fileData, "binary/octect-stream", fileName));

        return doPost(url, null, null, multiPartEntity);

    }

    public HttpStatus doPostWithMultiValues(String url, Map<String, String> params, String separator)
            throws IOException {
        List<NameValuePair> nvps = Lists.newArrayList();

        // httppost.setEntity();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String values[] = StringUtils.split(entry.getValue(), separator);
                for (String value : values) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }

        return doPost(url, null, null, new UrlEncodedFormEntity(nvps, HTTP.ISO_8859_1));

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
        return doPost(url, null, params, HttpClientUtils.createEntity(body));
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

    public HttpStatus doPost(String url, Map<String, String> params, HttpEntity entity) throws IOException {
        return doPost(url, null, params, entity);
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

    private HttpStatus commit(HttpUriRequest method) throws IOException {
        HttpStatus status;
        HttpClient httpclient = createHttpClient();
        // reduce the TIME_WAIT
        // method.addHeader("Connection", "close");

        if (method.getFirstHeader("Referer") == null) {
            URI uri = method.getURI();
            method.setHeader("Referer", uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort());
        }
        ;

        try {
            HttpResponse resp = execute(method, httpclient);

            status = new HttpStatus(resp.getStatusLine());
            if (resp.getEntity() != null) {
                status.setMessage(EntityUtils.toString(resp.getEntity(), "UTF-8"));
            }
        } catch (IOException e) {
            // cancel the connection when the error happen
            method.abort();
            throw e;
        } finally {
            HttpClientUtils.abortConnection(method, httpclient);
        }
        return status;
    }

    /**
     * @param method
     * @param httpclient
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private HttpResponse execute(HttpUriRequest method, HttpClient httpclient) throws IOException,
            ClientProtocolException {
        if (needAuth()) {
            return HttpClientUtils.executeWithBasicAuth(method, httpclient);
        }

        if (soTimeout > 0) {
            HttpConnectionParams.setSoTimeout(method.getParams(), soTimeout);
        }

        return httpclient.execute(method);
    }

    /**
     * @return
     */
    private DefaultHttpClient createHttpClient() {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpClientUtils.disableRetry(client);

        if (StringUtils.isNotBlank(proxy)) {
            HttpClientUtils.setProxy(client, proxy);
        }
        if (needAuth()) {
            HttpClientUtils.setAuth(client, authCritendial[0], authCritendial[1]);
        }
        return client;
    }

    /**
     * @return
     */
    private boolean needAuth() {
        return authCritendial != null && authCritendial.length == 2;
    }

    /**
     * @param i
     */
    public void setSokcetTimeout(int timeout) {
        this.soTimeout = timeout;

    }

    public static void main(String[] args) throws IOException{
        String s = "http://app.zhcw.com/wwwroot/zhcw/jsp/MediaArena2/leitai.jsp?issueId=new&utilType=1";
        String p = "www-proxy.ericsson.se:8080";
        DefaultHttpClient httpclient =  HttpClientUtils.createClient(10);
        HttpClientUtils.setProxy(httpclient, p);
        HttpGet method = new HttpGet(s);
        try {
            HttpResponse resp = httpclient.execute(method);
            System.out.println(resp.getEntity().getContent().toString());
        } catch (IOException e) {
            // cancel the connection when the error happen
            throw e;
        }
    }
}
