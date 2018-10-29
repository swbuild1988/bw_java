package com.bandweaver.tunnel.common.platform.util;

import java.util.Collection;
import java.util.List;

import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;

/**
 * ClassName: BSCheckUtil
 * @Description: 判断数据库操作是否成功工具类
 * @author shaosen
 * @date 2018年5月17日
 */
public class BSCheckUtil {

	
	/**
	 * @Description: 判断查询数据库是否有数据
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月17日
	 */
	public static <T> void isEmpty(Collection<T> list) {
		if(list == null || list.isEmpty())
			throw new BandWeaverException(StatusCodeEnum.DB_FAILED);
	}
	
	/**
	 * @Description: 判断查询数据库是否有数据
	 * @param @param t   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月17日
	 */
	public static <T> void isEmpty(T t) {
		if(t == null ) throw new BandWeaverException(StatusCodeEnum.DB_FAILED);
	}
	
	
	/**
	 * @Description: 判处更新，插入，删除数据是否成功
	 * @param @param num   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月17日
	 */
	public static void isSucess(int num) {
		if(num <= 0 ) throw new BandWeaverException(StatusCodeEnum.DB_FAILED);
	}
	
}
