package org.okj.commons.spring.ws.cxf;

import javax.jws.WebService;

@WebService(endpointInterface="org.okj.commons.spring.ws.cxf.IHelloWorld")  
public class HelloWorldImpl implements IHelloWorld {  
    public String sayHello(String text) {     
        return "Hello" + text ;  
    }  
}  