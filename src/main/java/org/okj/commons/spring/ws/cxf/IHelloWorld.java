package org.okj.commons.spring.ws.cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService  
public interface IHelloWorld {  
    public String sayHello(@WebParam(name="arg0")String text);  
}  