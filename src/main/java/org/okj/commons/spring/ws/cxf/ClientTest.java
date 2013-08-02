package org.okj.commons.spring.ws.cxf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
  
public class ClientTest {  
    public static void main(String[] args) {   
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:/META-INF/applicationContext*.xml");  
        IHelloWorld client = (IHelloWorld) ctx.getBean("client");  
        String result = client.sayHello("你好!");  
        System.out.println(result);  
    }  
}  