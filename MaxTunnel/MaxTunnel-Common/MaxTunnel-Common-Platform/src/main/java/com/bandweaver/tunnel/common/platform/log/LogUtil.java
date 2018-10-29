package com.bandweaver.tunnel.common.platform.log;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * ClassName: LogUtil
 * @Description: 封装log4j，打印堆栈信息
 * @author shaosen
 * @date 2018年5月28日
 */
public class LogUtil {
    
    public static void debug(Object str_debug) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();

        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(LogUtil.class.getName(), Level.DEBUG, str_debug, null);
        //logger.debug(str_debug);
    }
    
    public static void info(Object str_info) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();

        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(LogUtil.class.getName(), Level.INFO, str_info, null);
        //logger.info(str_info);
    }
    
    public static void warn(Object str_warn) {
    	StackTraceElement stack[] = (new Throwable()).getStackTrace();
    	
    	Logger logger = Logger.getLogger(stack[1].getClassName());
    	logger.log(LogUtil.class.getName(), Level.WARN, str_warn, null);
    	//logger.info(str_info);
    }
    
    public static void error(Object str_err) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();

        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(LogUtil.class.getName(), Level.ERROR, str_err, null);
        //logger.error(str_err);
    }
    
    
    public static void main(String[] args) {
    	LogUtil.info("This is info level ....");
    }

}