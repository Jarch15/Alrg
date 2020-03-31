package com.elliemae.alrg.utils;

import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.alrg.base.ALRGApplicationBase;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Helper class for logging
 *
 */
public class LogHelper {

	public static final String LOG_START = "start";
	public static final String LOG_END = "end";
	
	public LogHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMethodName(final int depth) {
		  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		  return ste[2+ depth].getMethodName();
	}
	
	public static void logTestStart(Logger _log, String message) {
		logTestStartEnd(_log, ALRGApplicationBase.LOG_START, message, EllieMaeLogLevel.reporter);
	}

	public static void logTestStart(Logger _log, String message, EllieMaeLogLevel... logLevel) {
		logTestStartEnd(_log, ALRGApplicationBase.LOG_START, message, logLevel);
	}
	
	public static void logTestEnd(Logger _log, String message) {
		logTestStartEnd(_log, ALRGApplicationBase.LOG_END, message, EllieMaeLogLevel.reporter);
	}

	public static void logTestEnd(Logger _log, String message, EllieMaeLogLevel... logLevel) {
		logTestStartEnd(_log, ALRGApplicationBase.LOG_END, message, logLevel);
	}
	
	public static void logTestError(Logger _log, String message) {
		ALRGLogger.log(_log, "Error occurred - " + message, EllieMaeLogLevel.error, EllieMaeLogLevel.reporter);	
	}
	
	public static void logTestError(Logger _log, Throwable e) {
		ALRGLogger.log(_log, "Exception trace - " + ExceptionUtils.getStackTrace(e), EllieMaeLogLevel.error, EllieMaeLogLevel.reporter);	
	}
	
	public static void logTestError(Logger _log, Throwable e, String message) {
		ALRGLogger.log(_log, message + ", Exception trace - " + ExceptionUtils.getStackTrace(e), EllieMaeLogLevel.error, EllieMaeLogLevel.reporter);		
	}
	
    public static void logTestStartEnd(Logger _log, String startEnd, String message, EllieMaeLogLevel... logLevel) {
        switch (startEnd.toLowerCase()) {
            case LOG_START:
                ALRGLogger.log(_log, "**************************************************", logLevel);
                ALRGLogger.log(_log, "Start " + CommonUtilityApplication.getCurrentDateTimeString("yyyy-MM-dd HH:mm:ss")
                    + ": " + _log.getName() + "() -- " + message, logLevel);
                break;
            case LOG_END:
                ALRGLogger.log(_log, "End " + CommonUtilityApplication.getCurrentDateTimeString("yyyy-MM-dd HH:mm:ss")
                    + ": " + _log.getName() + "() -- " + message, logLevel);
                ALRGLogger.log(_log, "**************************************************", logLevel);              
                break;
            default:
                break;
        }
    }   
}
