package com.bandweaver.tunnel.common.platform.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.alibaba.fastjson.util.TypeUtils;

public class PropertiesUtil  extends PropertyPlaceholderConfigurer{
	
	
/*	public static Properties prop = null;
	
	static {
		InputStream is = null;
		try {
			prop = new Properties();
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream("properties/config.properties");
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");//解析中文
			prop.load(isr);

		} catch (IOException e) {
			throw new BandWeaverException(e.toString());
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static Object getValue(String key) {
		Set<String> keys = prop.stringPropertyNames();
		for (String str : keys) {
			if(str.equals(key)) {
				return prop.getProperty(key);
			}
		}
		return null;
	}*/
	
	
	private static Map<String, Object> ctxPropertiesMap;
	 
	/**
	 * 重写processProperties方法
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}
 
	public static String getValue(String key) {
		return TypeUtils.castToString(ctxPropertiesMap.get(key));
	}
 
	public static Object getObject(String key, Class clazz) {
		Object obj = getValue(key);
		return TypeUtils.castToJavaBean(obj, clazz);
	}
 
	public static Boolean getBoolean(String key) {
		Object value = getValue(key);
		if (value == null)
			return null;
		else
			return TypeUtils.castToBoolean(value);
	}
 
	public static byte[] getBytes(String key) {
		Object value = getValue(key);
		if (value == null)
			return null;
		else
			return TypeUtils.castToBytes(value);
	}
 
	public static boolean getBooleanValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return false;
		else
			return TypeUtils.castToBoolean(value).booleanValue();
	}
 
	public static Byte getByte(String key) {
		Object value = getValue(key);
		return TypeUtils.castToByte(value);
	}
 
	public static byte getByteValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return 0;
		else
			return TypeUtils.castToByte(value).byteValue();
	}
 
	public static Short getShort(String key) {
		Object value = getValue(key);
		return TypeUtils.castToShort(value);
	}
 
	public static short getShortValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return 0;
		else
			return TypeUtils.castToShort(value).shortValue();
	}
 
	public static Integer getInteger(String key) {
		Object value = getValue(key);
		return TypeUtils.castToInt(value);
	}
 
	public static int getIntValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return 0;
		else
			return TypeUtils.castToInt(value).intValue();
	}
 
	public static Long getLong(String key) {
		Object value = getValue(key);
		return TypeUtils.castToLong(value);
	}
 
	public static long getLongValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return 0L;
		else
			return TypeUtils.castToLong(value).longValue();
	}
 
	public static Float getFloat(String key) {
		Object value = getValue(key);
		return TypeUtils.castToFloat(value);
	}
 
	public static float getFloatValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return 0.0F;
		else
			return TypeUtils.castToFloat(value).floatValue();
	}
 
	public static Double getDouble(String key) {
		Object value = getValue(key);
		return TypeUtils.castToDouble(value);
	}
 
	public static double getDoubleValue(String key) {
		Object value = getValue(key);
		if (value == null)
			return 0.0D;
		else
			return TypeUtils.castToDouble(value).doubleValue();
	}
 
	public static BigDecimal getBigDecimal(String key) {
		Object value = getValue(key);
		return TypeUtils.castToBigDecimal(value);
	}
 
	public static BigInteger getBigInteger(String key) {
		Object value = getValue(key);
		return TypeUtils.castToBigInteger(value);
	}
 
	public static String getString(String key) {
		Object value = getValue(key);
		if (value == null)
			return null;
		else
			return value.toString();
	}
 
	public static Date getDate(String key) {
		Object value = getValue(key);
		return TypeUtils.castToDate(value);
	}
 
	public static java.sql.Date getSqlDate(String key) {
		Object value = getValue(key);
		return TypeUtils.castToSqlDate(value);
	}
 
	public static Timestamp getTimestamp(String key) {
		Object value = getValue(key);
		return TypeUtils.castToTimestamp(value);
	}
	
}
