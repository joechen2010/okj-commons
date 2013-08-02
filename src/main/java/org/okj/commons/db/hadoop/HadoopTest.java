package org.okj.commons.db.hadoop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HadoopTest {
	
	public static void main(String[] arguments) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("hadoop-context.xml");
    }

}
