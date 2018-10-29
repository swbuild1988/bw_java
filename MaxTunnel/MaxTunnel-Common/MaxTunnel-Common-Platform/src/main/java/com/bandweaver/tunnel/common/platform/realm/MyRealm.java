package com.bandweaver.tunnel.common.platform.realm;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.bandweaver.tunnel.common.biz.itf.UserService;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

public class MyRealm extends AuthorizingRealm {

//    Logger log = Logger.getLogger(MyRealm.class);

	@Resource
	private UserService userService;

	/**
	 * 为当前登录的用户授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		LogUtil.info("Enter doGetAuthorizationInfo ....");
		// 获取用户名
		String userName = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 进行授权角色
		authorizationInfo.setRoles(userService.getRoles(userName));
		// 进行授权权限
		Set<String> permissions = userService.getPermissions(userName);
		authorizationInfo.setStringPermissions(userService.getPermissions(userName));

		return authorizationInfo;
	}

	/**
	 * 验证当前登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		LogUtil.info("Enter doGetAuthenticationInfo ....");
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		User user = userService.getByUserName(token.getUsername());
		LogUtil.info("Get user : " + user );
		if (null != user) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getName(), user.getPassword(),
					super.getName());
			LogUtil.info("Get authcInfo : " + authcInfo );
			// 保存用户信息到session中
			SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
			SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_ROLE,
					userService.getRoles(token.getUsername()));
			SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_PERMISSION,
					userService.getPermissions(token.getUsername()));
			return authcInfo;
		} else {
			// 没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
			return null;
		}

	}
}
