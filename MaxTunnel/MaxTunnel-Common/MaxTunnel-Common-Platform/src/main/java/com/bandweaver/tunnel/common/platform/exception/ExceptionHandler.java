package com.bandweaver.tunnel.common.platform.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

/**
 * ClassName: ExceptionHandler
 * 
 * @Description: controller异常捕获类
 * 
 *               使用方法： 满足条件，直接throw给上一层，也就是Controller 然后Controller继续抛出，这样当条件满足时
 *               这个异常信息（也就是那个字符串）就会在控制台上出现
 * @author shaosen
 * @date 2018年5月16日
 */
@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	@ResponseBody
	public JSONObject handle(Exception e) {
		if (e instanceof BandWeaverException) {
			BandWeaverException bwException = (BandWeaverException) e;
			return CommonUtil.returnJson(bwException.getCode(), bwException.getMessage(), bwException.getCause());
			
		} else if (e instanceof IncorrectCredentialsException) {
			return CommonUtil.returnStatusJson(StatusCodeEnum.E_20007);
			
		} else if (e instanceof UnknownAccountException) {
			return CommonUtil.returnStatusJson(StatusCodeEnum.E_20006);
			
		} else if (e instanceof AuthenticationException) {
			return CommonUtil.returnStatusJson(StatusCodeEnum.E_400);
			
		} else if (e instanceof UnauthorizedException) {
			return CommonUtil.returnStatusJson(StatusCodeEnum.E_502);
			
		} else {
			LogUtil.error("[系统异常] :" + e);
			return CommonUtil.returnJson("-1", "未知错误", e.toString());
		}
	}
}
