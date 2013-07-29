package org.okj.commons.logger;

import java.io.StringWriter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Use this one to intercept the output of log4j in unit test case.
 * 
 * @author toy
 * 
 */
public class Log4jCapture {
    private StringWriter sw = new StringWriter();
    Logger log;

    public Log4jCapture(String appenderName, String pattern) {
        Logger log = Logger.getLogger(appenderName);

        if (log != null) {
            log.removeAllAppenders();
            log.setLevel(Level.ALL);
            ConsoleAppender appender = new ConsoleAppender();
            appender.setName("capture");
            appender.setImmediateFlush(true);
            appender.setWriter(sw);
            appender.setEncoding("UTF-8");
            appender.setLayout(new PatternLayout(pattern));
            log.addAppender(appender);
        }
    }

    public Log4jCapture(String appenderName) {
        this(appenderName, "[%p]%m%n");
    }

    /**
     * Return the log message in a string
     * 
     * @return
     */
    public String getLogString() {
        return sw.toString();
    }

    public static Log4jCapture capture(String appender) {
        return new Log4jCapture(appender);
    }

    public static Log4jCapture capture(String appender, String pattern) {
        return new Log4jCapture(appender, pattern);
    }
}
