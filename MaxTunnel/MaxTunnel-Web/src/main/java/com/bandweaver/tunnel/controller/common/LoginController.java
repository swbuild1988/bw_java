package com.bandweaver.tunnel.controller.common;


import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

import java.util.Set;

@Controller
@ResponseBody
public class LoginController {


    /** 登录并返回用户
     * @param user
     * @return
     */

	@WriteLog(DescEnum.LOGIN)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login (@RequestBody User user){
		LogUtil.info("Enter login ....");
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getName(), user.getPassword());
        token.setRememberMe(true); 
        
        	//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查    
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应    
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法 
        	LogUtil.info(" user[" + user.getName() + "]do login checking");    
            subject.login(token);
            LogUtil.info(" user[" + user.getName() + "]authentication success");
            
            Session session=subject.getSession();
            session.setTimeout(3600000);
            LogUtil.debug(" sessionId:"+session.getId());
            LogUtil.debug(" sessionHost:"+session.getHost());
            LogUtil.debug(" sessionTimeout:"+session.getTimeout());
            LogUtil.debug(" sessionAttribute:" + session.getAttribute("userInfo").toString());
            LogUtil.debug(" sessionLastAccessTime:" + session.getLastAccessTime().toString());
            LogUtil.debug(" sessionStartTimestamp:" + session.getStartTimestamp().toString());

            UserDTO userDTO = new UserDTO((User)session.getAttribute(Constants.SESSION_USER_INFO));
            userDTO.setRoles((Set<String>)session.getAttribute(Constants.SESSION_USER_ROLE));
            userDTO.setPermissions((Set<String>)session.getAttribute(Constants.SESSION_USER_PERMISSION));
            return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, userDTO);
       
    }

	@WriteLog(DescEnum.LOGOUT)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JSONObject Logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

	/**
	 * @Description: 权限认证失败执行
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	@RequestMapping(value = "/unauthor", method = RequestMethod.GET)
	public JSONObject unauthor() {
		 return CommonUtil.returnStatusJson(StatusCodeEnum.E_502);
	}
	/**
	 * @Description: 未登录执行
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public JSONObject unlogin() {
		return CommonUtil.returnStatusJson(StatusCodeEnum.E_503);
	}
	

}