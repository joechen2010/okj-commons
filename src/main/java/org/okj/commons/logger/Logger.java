package org.okj.commons.logger;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * This class is only worked for license control. We don't hope the cracker can change the log4j.properties to locate
 * the license class. so don't use log4j to generate the log.
 * 
 * @author ehanrli
 * 
 */
public class Logger {
    private static Logger instance = new Logger();

    private String fileName = "logs/license.log";

    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("licenselog");

    private Logger() {
        this("logs/license.log");
    }

    private Logger(String fileName) {
        try {
            this.fileName = fileName;
            PatternLayout layout = new PatternLayout("%d[%p] %m%n");
            RollingFileAppender appender = new RollingFileAppender(layout, fileName);
            appender.setAppend(true);
            appender.setEncoding("utf-8");
            appender.setImmediateFlush(true);
            appender.setMaxBackupIndex(5);
            appender.setMaxFileSize("10MB");
            appender.setThreshold(Level.INFO);
            log.removeAllAppenders();
            log.addAppender(appender);
            log.setAdditivity(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.append("Can't create logfile: " + getLoggerFileName() + " " + e.getMessage());
        }

    }

    public static String getLoggerFileName() {
        return instance.fileName;
    }

    public static Logger get() {
        return instance;
    }

    /**
     * Create a logger by the specified log file name.
     * 
     * @param logFileName
     * @return
     */
    public static Logger create(String logFileName) {
        instance = new Logger(logFileName);
        return instance;
    }

    public void log(String message) {
        log.info(message);
    }

    public void close() {
        LogManager.shutdown();
    }
}
