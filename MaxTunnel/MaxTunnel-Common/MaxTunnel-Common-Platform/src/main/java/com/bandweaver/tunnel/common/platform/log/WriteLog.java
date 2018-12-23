package com.bandweaver.tunnel.common.platform.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ClassName: WriteLog
 * @Description:打印日志
 * @author shaosen
 * @date 2018年5月17日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD}) 
@Retention(RetentionPolicy.RUNTIME) 
@Documented 
public @interface WriteLog {
	
	/**LogConfig.xml中标签<Operation>的ID*/
	DescEnum value();
	
}
