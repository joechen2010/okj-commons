package org.okj.commons.http;

import javax.servlet.http.HttpServletRequest;

public class IPHelper {
	public static boolean ipIsValid(String ipSection, String ip) {   
   	 
        if (ipSection == null)   
            throw new NullPointerException("");   
        if (ip == null)   
            throw new NullPointerException("");   
        ipSection = ipSection.trim();   
        ip = ip.trim();   
        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";   
        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;   
        if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))   
            return false;   
        int idx = ipSection.indexOf('-');   
        String[] sips = ipSection.substring(0, idx).split("\\.");   
        String[] sipe = ipSection.substring(idx + 1).split("\\.");   
        String[] sipt = ip.split("\\.");   
        long ips = 0L, ipe = 0L, ipt = 0L;   
        for (int i = 0; i < 4; ++i) {   
            ips = ips << 8 | Integer.parseInt(sips[i]);   
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);   
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);   
        }   
        if (ips > ipe) {   
            long t = ips;   
            ips = ipe;   
            ipe = t;   
        }   
        return ips <= ipt && ipt <= ipe;   
    }   
	
	public static String getIpAddr(HttpServletRequest request) {      
	       String ip = request.getHeader("x-forwarded-for");      
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	           ip = request.getHeader("Proxy-Client-IP");      
	       }      
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	           ip = request.getHeader("WL-Proxy-Client-IP");      
	       }      
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	           ip = request.getRemoteAddr();      
	       }      
	       return ip;      
	 } 
	
	 public static void main(String[] args) {   
	        if (ipIsValid("211.90.0.0-211.97.255.255", "211.93.5.255")) {   
	            System.out.println("ip���ڸ����");   
	        } else  {
	            System.out.println("ip�����ڸ����");  } 
	    }   

}

//
//61.240.0.0 - 61.243.255.255
//210.78.160.0 - 210.78.255.255
//211.90.0.0 - 211.97.255.255
//211.145.128.0 - 211.145.255.255
//220.192.183.0 - 220.192.215.255
//220.193.0.0 - 220.207.255.255 

//117.128.0.0 - 117.191.255.255
//120.192.0.0 - 120.255.255.255
//211.103.0.0 - 211.103.127.255 
//211.136.0.0 - 211.143.255.255
//211.154.128.0 - 211.154.159.255
//218.200.0.0 - 218.207.255.255
//220.231.128.0 - 220.231.255.255
//221.130.0.0 - 221.131.255.255
//221.176.0.0 - 221.183.255.255 
