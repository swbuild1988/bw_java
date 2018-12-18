package com.bandweaver.tunnel.common.platform.realm;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

public class MyRealm extends AuthorizingRealm {

	@Resource
	private UserService userService;

	/**
	 * 为当前登录的用户授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户名
		String userName = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 进行授权权限
		JSONObject permissions = userService.getPermissions(userName);
		LogUtil.info("用户" + userName + "权限为：" + permissions);
		authorizationInfo.addStringPermissions((Collection<String>) permissions.get("permissionList"));
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.getByUserName(token.getUsername());
	    if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
	    //登录验证
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), super.getName());
//		user.setPassword(null);//不保存密码到session中
		// 保存用户信息到session中
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_PERMISSION,
				userService.getPermissions(token.getUsername()));
		return authcInfo;
	}
}
