package com.bandweaver.tunnel.common.platform.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据类型通用工具
 */
public class DataTypeUtil {

    public static int toInt(Object o){
        return toInt(o, -1);
    }

    public static int toInt(Object o, int defaultValue){
        return o == null ? defaultValue : (int)o;
    }

    public static Integer toInteger(Object o) {
    	return o == null ? null : (int)o;
    }
    
    
    public static Integer toInteger(String str) {
    	return str == null ? null : Integer.valueOf(str);
    }
    
    
    public static Double toDouble(Object o) {
    	String str = toString(o);
    	return str == null ? null :  Double.parseDouble(str);
    }
    
    public static BigDecimal toBigDecimal(Object o) {
    	String str = toString(o);
    	return str == null ? null : new BigDecimal(str);
    }
    
    public static String toString(Object o){
        return o==null ? null : String.valueOf(o).trim();
    }
    
    
    /**
     * 通过时间戳格式转化为时间格式
     * @param o
     * @return
     */
    public static Date toDateFromTimestamp(Object o) {
        return o == null ? null : DateUtil.setMillisTimestamp2Date(Long.valueOf(String.valueOf(o)).longValue());
    }
    
    

}
