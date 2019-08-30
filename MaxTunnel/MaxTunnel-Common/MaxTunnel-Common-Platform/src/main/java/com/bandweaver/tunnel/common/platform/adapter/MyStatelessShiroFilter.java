package com.bandweaver.tunnel.common.platform.adapter;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.bandweaver.tunnel.common.biz.itf.MqService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.JwtConstants;
import com.bandweaver.tunnel.common.platform.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;

public class MyStatelessShiroFilter extends AccessControlFilter {

	@Autowired
	private MqService mqService;
	/**
	 * 返回false
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @param o
	 * @return 返回结果是false的时候才会执行下面的onAccessDenied方法
	 * @throws Exception
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
			throws Exception {
		// 测试使用true，正式改为false
		return false;
	}

	/**
	 * 从请求头获取token并验证
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @return 返回结果为true表明登录通过
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String url = request.getRequestURI();
		// 从头部获取JWT字符串信息
		String jwt = request.getHeader(JwtConstants.AUTH_HEADER);
		LogUtil.info("=====onAccessDenied  url ======"+url);
		LogUtil.info("***** token ****" + jwt);
		if(StringUtils.isNotBlank(jwt)){
            try {
                boolean verify = JwtUtil.verifyToken(jwt);//验证token
                if(verify){
                    boolean expired = JwtUtil.isTokenExpired(jwt);//是否过期
                    if(expired){
                        String queueName = JwtUtil.getPrivateClaimsFromToken(jwt,"queueName");
                        mqService.deleteQueue(queueName);
                        LogUtil.info("-----token过期,跳登陆页面----");
                        redirectToLogin(servletRequest, servletResponse);
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    LogUtil.info("验证token 失败,跳登陆页面");
                    redirectToLogin(servletRequest, servletResponse);
                    return false;
                }
            } catch (IllegalArgumentException e) {
                LogUtil.info("request header中没有JWT参数！");
                redirectToLogin(servletRequest, servletResponse);
                return false;
            } catch (ExpiredJwtException e) {
                LogUtil.info("ExpiredJwtException JWT已过期");
                String queueName = JwtUtil.getPrivateClaimsFromToken(jwt,"queueName");
                mqService.deleteQueue(queueName);
                redirectToLogin(servletRequest, servletResponse);
                return false;
            } catch (Exception e) {
                LogUtil.info("Exception JWT验证失败" + e.getMessage());
                redirectToLogin(servletRequest, servletResponse);
                return false;
            }
        }else{
            LogUtil.info("------ token为空 ----" );
            redirectToLogin(servletRequest, servletResponse);
            return false;
        }
	}

	/**
	 * 重定向到未登录页
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		WebUtils.issueRedirect(request, response, "/503");
	}

}
