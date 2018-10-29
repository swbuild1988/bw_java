package com.bandweaver.tunnel.common.platform.util;

import java.util.Set;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;


/**
 * ClassName: ContextUtil
 * @Description: context工具类
 * @author shaosen
 * @date 2018年5月14日
 */
public class ContextUtil {

	/**
	 * @Description: 获取登录用户
	 * @param @return   
	 * @return User  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月5日
	 */
	public static User getLoginUser() {
		Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
		if(obj == null)
			throw new RuntimeException("尚未登录");
		return (User)obj;
	}

	/**
	 * @Description: 获取request对象
	 * @param @return   
	 * @return HttpServletRequest  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月14日
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * @Description: 获取session
	 * @param @return   
	 * @return HttpSession  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月7日
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * @Description: 获取登录用户角色
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月14日
	 */
	public static Set<String> getLoginUserRole() {
		return  (Set<String>) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_ROLE);
	}
	
	
	/**
	 * @Description: 获取登录用户权限
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月14日
	 */
	public static Set<String> getLoginUserPermission() {
		return  (Set<String>) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_PERMISSION);
	}
	
	/**
	 * @Description: 获取登录ip地址
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月14日
	 */
	public static String getRemoteIp() {
		return ContextUtil.getRequest().getRemoteAddr();
	}
	

}
