package org.okj.commons.web.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class ProxyConnectionTest {
    public static void main(String[] args) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("www-proxy.ericsson.se", 8080));

        /*Authenticator auth = new Authenticator() {
            private PasswordAuthentication pa = new PasswordAuthentication(username, password.toCharArray());

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return pa;
            }
        };
        Authenticator.setDefault(auth);*/

        System.out.println("connecting...");
        URL url = new URL("http://app.zhcw.com/wwwroot/zhcw/jsp/MediaArena2/leitai.jsp?issueId=new&utilType=1");
        URLConnection conn = url.openConnection(proxy);

        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        System.out.println("done.");
    }
}