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
        doLog(logger,Level.DEBUG,str_debug);
    }
    
    public static void info(Object str_info) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        doLog(logger,Level.INFO,str_info);
    }
    
  
	public static void warn(Object str_warn) {
    	StackTraceElement stack[] = (new Throwable()).getStackTrace();
    	Logger logger = Logger.getLogger(stack[1].getClassName());
    	doLog(logger,Level.WARN,str_warn);
    }
    
    public static void error(Object str_err) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        doLog(logger,Level.ERROR,str_err);
    }
    
    private static void doLog(Logger logger, Level level, Object str_info) {
    	/**
    	 * 说明：
    	 * 如果要打印详细信息，比如打印方法名，则使用logger.log(LogUtil.class.getName(), level, str_info, null);
    	 * 如果要打印简单信息，只要日志，则第一个参数设置为null
    	 */
    	logger.log(LogUtil.class.getName(), level, str_info, null);
	}
    
    public static void main(String[] args) {
    	LogUtil.info("This is info level ....");
    }

}