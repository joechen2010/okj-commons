package org.okj.commons.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * This class is only worked for license control. We don't hope the cracker can change the log4j.properties to locate
 * the license class. so don't use log4j to generate the log.
 * 
 * @author ehanrli
 * 
 */
public class SimplerLogger {
    static final protected String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    PrintWriter logWriter = null;
    File logFile = null;
    static SimplerLogger instance = new SimplerLogger();

    private String[] fileName = { "lo", "gs", "/li", "cen", "se.l", "og" };

    private SimplerLogger() {
        File logDir = new File(fileName[0] + fileName[1]);
        if (!logDir.isDirectory()) {
            logDir.mkdirs();
        }
        try {
            logFile = new File(StringUtils.join(fileName));
            logWriter = new PrintWriter(new FileOutputStream(logFile, true), true);
        } catch (IOException e) {
            System.err.append("Can't create logfile: " + StringUtils.join(fileName));
        }
    }

    public static SimplerLogger get() {
        return instance;
    }

    public void log(String message) {
        System.out.println(message);
        writeLine(timeString(System.currentTimeMillis()) + ":" + message);
    }

    synchronized protected void writeLine(String s) {
        if (logWriter != null) {
            logWriter.println(s);
        }
    }

    static public String timeString(long tm) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateFormat.format(new Date(tm));
    }

    public void close() {
        if (logWriter != null) {
            logWriter.close();
        }
        logWriter = null;
    }

}
