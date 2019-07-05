package com.bandweaver.tunnel.common.platform.realm;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.bandweaver.tunnel.common.platform.util.RedisUtil;
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
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

	@Resource
	private UserService userService;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 为当前登录的用户授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户名
		String userName = (String) principals.getPrimaryPrincipal();
		String REDIS_KEY = userName + "." + Constants.SHIRO_PERMISSION_KEY;
		Set<String> permissionSet = new HashSet<>();
		if (redisUtil.hasKey(REDIS_KEY)) {

			// 从缓存中获取权限信息
			permissionSet = redisUtil.sMembers(REDIS_KEY);
			LogUtil.info("从redis缓存中命中:" + permissionSet);
		} else {

			// 如果没有，则需要从数据库查询，并同步到redis缓存中
//			JSONObject permissions = userService.getPermissions(userName);
//			permissionSet = (Set<String>) permissions.get("permissionList");
			permissionSet = userService.getUserPermissions(userName);
			LogUtil.info("从DB查询用户" + userName + "权限为：" + permissionSet);
			for (String permission : permissionSet) {
				redisUtil.sAdd(REDIS_KEY, permission);
				// 设置缓存过期时间
				redisUtil.expire(REDIS_KEY,30, TimeUnit.MINUTES);
			}
		}

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permissionSet);
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
//		JSONObject permissions = userService.getPermissions(token.getUsername());
//		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_PERMISSION, (Set<String>)permissions.get("permissionList"));
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_PERMISSION, userService.getUserPermissions(token.getUsername()));
		return authcInfo;
	}
}
