package com.bandweaver.tunnel.common.platform.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

public class PropertiesUtil {
	
	
	public static Properties prop = null;
	
	static {
		try {
			prop = new Properties();
			InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("properties/config.properties");
			prop.load(is);
			//关闭流
			is.close();
		} catch (IOException e) {
			throw new BandWeaverException(StatusCodeEnum.IO_EXP,e);
		}
	}
	
	
	/**
	 * @Description: 通过key获取value值
	 * @param @param key
	 * @param @return   
	 * @return Object  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月3日
	 */
	public static Object getValue(String key) {
		Set<String> keys = prop.stringPropertyNames();
//		LogUtil.debug("debug:"+keys);
		for (String str : keys) {
			if(str.equals(key)) {
				return prop.getProperty(key);
			}
		}
		return null;
	}
}
