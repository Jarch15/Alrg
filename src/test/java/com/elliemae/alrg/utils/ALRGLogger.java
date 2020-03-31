package com.elliemae.alrg.utils;

import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;

public class ALRGLogger  {

	private ALRGLogger() {}
	
	private static String TIMESTAMP_FORMAT = "[yyyy-MM-dd HH:mm:ss a, z] ";
	/**
	 * <b>Name:</b> log<br>
	 * <b>Description:</b> This method will add different levels of EllieMaelog to log file
	 * 
	 * @param log The Logger instance.
	 * @param logMessage The message to be logged.
	 * @param logLevel The EllieMaeLogLevel var-args of allowed log levels.
	 */
	public static synchronized void log(final Logger log, final String logMessage, final EllieMaeLogLevel ...logLevel) {
		
		if(0 == logLevel.length){
			log.info(logMessage);
		}
		else {
			for(EllieMaeLogLevel level: logLevel) {
				switch(level) {
					case debug:
						log.debug(logMessage);
						break;
						
					case info:
						log.info(logMessage);
						break;
						
					case reporter:	
						log.info(logMessage);
						//add timestamp when logging to report file
						Reporter.log(DateFormatUtils.format(new Date().getTime(), TIMESTAMP_FORMAT) + logMessage, true);
						break;
						
					case warn:
						log.warn(logMessage);
						break;
						
					case error:
						log.error(logMessage);
						break;
						
					default:
						log.error("Log level '" + level + "' is not supported by the framework.");
				}
			}			
		}
	}
	
	/**
	 * <b>Name:</b> log
	 * <b>Description:</b> Method logs the message with cause (if present) for different log levels.
	 * 
	 * @param log The Logger instance.
	 * @param logMessage The message to be logged.
	 * @param cause The throwable cause instance.
	 * @param logLevel The EllieMaeLogLevel variable arguments of allowed log levels.
	 */
	public static synchronized void log(final Logger log, final String logMessage, final Throwable cause, final EllieMaeLogLevel ...logLevel) {	
		if(0 == logLevel.length) {
			log.info(logMessage, cause);
		}
		else {
			StringWriter stackTrace;
			
			for(EllieMaeLogLevel level: logLevel) {
				switch(level) {
					case debug:
						log.debug(logMessage, cause);
						break;
						
					case info:
						log.info(logMessage, cause);
						break;
						
					case reporter:	
						log.info(logMessage, cause);

						stackTrace = new StringWriter();
						stackTrace.append(logMessage);
						
						if(null != cause) {
							stackTrace.append("<br>");
							cause.printStackTrace(new PrintWriter(stackTrace));
						}
						//add timestamp when logging to report file
						Reporter.log(DateFormatUtils.format(new Date().getTime(), TIMESTAMP_FORMAT) + stackTrace.toString(), true);						
						break;
						
					case warn:
						log.warn(logMessage, cause);
						break;
						
					case error:
						log.error(logMessage, cause);
						break;
						
					default:
						log.error("Log level '" + level + "' is not supported by the framework.");
				}
			}			
		}
	}
}
