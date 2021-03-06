package org.okj.commons.activemq;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Logging Event Wraps a log4j LoggingEvent object. Wrapping is required by some information is lost
 * when the LoggingEvent is serialized. The idea is to extract all information required from the LoggingEvent
 * object, place it in the wrapper and then serialize the LoggingEventWrapper. This way all required data remains
 * available to us.
 * @author faheem
 *
 */

public class LoggingEventWrapper extends Message implements Serializable{

	private static final String ENHANCED_PATTERN_LAYOUT = "%throwable";
	private static final long serialVersionUID = 3281981073249085474L;
	private LoggingEvent loggingEvent;

	private Long timeStamp;
	private String level;
	private String logger;
	private String message;
	private String detail;
	private String ipAddress;
	private String hostName;

	public LoggingEventWrapper(LoggingEvent loggingEvent){
		this.loggingEvent = loggingEvent;

		//Format event and set detail field
	    EnhancedPatternLayout layout = new EnhancedPatternLayout();
	    layout.setConversionPattern(ENHANCED_PATTERN_LAYOUT);
	    this.detail = layout.format(this.loggingEvent);
	}

	public Long getTimeStamp() {
		return this.loggingEvent.timeStamp;
	}

	public String getLevel() {
		return this.loggingEvent.getLevel().toString();
	}

	public String getLogger() {
		return this.loggingEvent.getLoggerName();
	}

	public String getMessage() {
		return this.loggingEvent.getRenderedMessage();
	}

	public String getDetail() {
	    return this.detail;
	}

	public LoggingEvent getLoggingEvent() {
		return loggingEvent;
	}

	public String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Could not determine IP";
		}
	}

	public String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "Could not determine Host Name";
		}
	}

	public static String getEnhancedPatternLayout() {
		return ENHANCED_PATTERN_LAYOUT;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLoggingEvent(LoggingEvent loggingEvent) {
		this.loggingEvent = loggingEvent;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	
}