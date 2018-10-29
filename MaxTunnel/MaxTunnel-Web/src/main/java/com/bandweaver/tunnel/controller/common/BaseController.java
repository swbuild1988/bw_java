package com.bandweaver.tunnel.controller.common;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.quartz.SchedulerException;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;

/**通用controller
 * @author shaosen
 * @param <Q>
 * @date 2018年9月25日
 */
public abstract class BaseController<T> {

	public abstract JSONObject add(T record) throws Exception;

	public abstract JSONObject delete(Integer id) throws Exception;
	
	public abstract JSONObject deleteBatch(String ids) throws Exception;

	public abstract JSONObject update(T record) throws Exception;

	public abstract JSONObject getById(Integer id) throws Exception;
	
	
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	
	/**获取session用户 
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月25日
	 */
	public User getSessionUser() {
		Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
		if(obj == null)
			return null;
		else
			return (User)obj;
	}
	
	
	/**保存用户到session 
	 * @param user   
	 * @author shaosen
	 * @Date 2018年9月25日
	 */
	public void setSessionUser(User user) {
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO,user);
	}

	
	
	/**把id字符串转化成Integer类型的List集合
	 * @param ids
	 * @return  List<Integer>
	 * @author shaosen
	 * @Date 2018年9月25日
	 */
	protected List<Integer> convertStringToList(String ids){
		List<Integer> list = new ArrayList<>();
		String[] arr = ids.split(",");
		if(arr.length == 0) {
			arr = EMPTY_STRING_ARRAY;
		}
		for (String str : arr) {
			list.add(DataTypeUtil.toInteger(str));
		}
		return list;
	}
	
	
	protected JSONObject success() {
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	protected JSONObject success(Object obj) {
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,obj);
	}
	
	
	

}
