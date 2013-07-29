package org.okj.commons.robot;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.okj.commons.web.HttpClientUtils;
import org.okj.commons.web.jackson.JsonMapper;
import org.okj.commons.web.jackson.JsonMapperFactory;

public class ChatRobot {
	
	private String host = "www.simsimi.com";
	private String referer = "http://www.simsimi.com/talk.htm?lc=ch";
	private String api = "http://www.simsimi.com/func/req?msg={msg}&lc=ch";
	//private String api = "http://nlp.xiaoi.com/robot/demo/wap/wap-demo.action";
	
	private HttpGet httpget;
	private DefaultHttpClient client;
	private String proxyHost;
	
	private static JsonMapper jsonMapper = JsonMapperFactory.get();
	
	public ChatRobot() {
		
	}

	
	public RobotResponse chat(String msg) {
		String url = api.replace("{msg}", msg);
		String cookie = fectCookie();
		createGet(url, cookie);
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
	    String responseBody = "";
	    try {
	    	client = HttpClientUtils.createClient(10);
	    	if(proxyHost != null)
	    		HttpClientUtils.setProxy(client, proxyHost);
	        responseBody = client.execute(httpget, responseHandler);
	    } catch (Exception e) {
	        e.printStackTrace();
	        responseBody = null;
	    } finally {
	        httpget.abort();
	    }
	    System.out.println(responseBody);
	    return jsonMapper.fromJson(responseBody, RobotResponse.class); 
	}
	
	public static void main(String[] args){
		ChatRobot r = new ChatRobot();
		r.setProxyHost("www-proxy.ericsson.se:8080");
		r.chat("变态");
	}
	
	private String fectCookie(){
		String cookie = " __utma=119922954.1370034306.1374633260.1374633260.1374633260.1; __utmc=119922954; __utmz=119922954.1374633260.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); sagree=true";
	    try {
	    	HttpPost method = new HttpPost("http://"+host);
	    	client = HttpClientUtils.createClient(10);
	    	if(proxyHost != null)
	    		HttpClientUtils.setProxy(client, proxyHost);
	    	HttpResponse resp = client.execute(method);
	    	Header[] headers = resp.getHeaders("Set-Cookie");
	    	for(Header h : headers){
	    		String v = h.getValue();
	    		cookie = v.substring(0,v.indexOf(";")+1) + cookie;
	    	}
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	HttpClientUtils.release(client);
	    }
	    return cookie;
	}
	
	private HttpGet createGet(String url,String cookie){
	    httpget = new HttpGet(url);
	    httpget.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        httpget.setHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
        httpget.setHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpget.setHeader("Connection", "keep-alive");
        httpget.setHeader("Content-Type", "application/json; charset=utf-8");
        if(cookie != null)
        	httpget.setHeader("Cookie",cookie);
	    httpget.setHeader("Host", host);
	    httpget.setHeader("Referer", referer);
	    httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER");
	    httpget.setHeader("X-Requested-With", "XMLHttpRequest");
	    return httpget;
	}
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}
	
	public HttpGet getHttpget() {
		return httpget;
	}

	public void setHttpget(HttpGet httpget) {
		this.httpget = httpget;
	}

	public HttpClient getClient() {
		return client;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setClient(DefaultHttpClient client) {
		this.client = client;
	}


}
