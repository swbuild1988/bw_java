package com.bandweaver.tunnel.controller.common;


import java.util.List;
import java.util.Set;

import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.Loginservice;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.Sha256;

/**
 * 登录管理
 *
 * @author shaosen
 * @date 2018年12月14日
 */
@Controller
@ResponseBody
public class LoginController {


    @Autowired
    private Loginservice loginservice;


    /**
     * 登录并返回权限列表
     *
     * @param
     * @param
     * @return
     */
    @WriteLog(DescEnum.LOGIN)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "name,password");

        String name = requestJson.getString("name");
        String password = requestJson.getString("password");
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        token.setRememberMe(true);
        subject.login(token);

        Session session = subject.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_INFO);

        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("userId", user.getId());

        return CommonUtil.success(json);

    }

    /**
     * 获取登录用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/login/{username}", method = RequestMethod.GET)
    public JSONObject getLogInfo(@PathVariable("username") String username) {
//		JSONObject result = loginservice.getPermissionListByUserName(username);
        List<Role> result = loginservice.getUserRoles(username);
        return CommonUtil.success(result);
    }


    /**
     * GET方式登陆
     *
     * @param name
     * @param password
     * @return
     */
    @WriteLog(DescEnum.LOGIN)
    @RequestMapping(value = "name/{name}/password/{password}", method = RequestMethod.GET)
    public JSONObject loginGet(@PathVariable("name") String name, @PathVariable("password") String password) {

        Subject subject = SecurityUtils.getSubject();
        //密码加密是前端的事情，等前端改好之后，此处代码改回来即可
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        token.setRememberMe(true);
        subject.login(token);

        Session session = subject.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_INFO);
        JSONObject returnData = loginservice.getUserPermission(user.getName());
        returnData.put("token", token);
        return CommonUtil.success(returnData);
    }

    @WriteLog(DescEnum.LOGOUT)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JSONObject Logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return CommonUtil.success();
    }


    @RequestMapping(value = "/unauthor", method = RequestMethod.GET)
    public JSONObject unauthor() {
        return CommonUtil.returnStatusJson(StatusCodeEnum.E_502);
    }

    /**
     * @param @return
     * @return JSONObject
     * @throws
     * @Description: 未登录执行
     * @author shaosen
     * @date 2018年5月22日
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public JSONObject unlogin() {
        return CommonUtil.returnStatusJson(StatusCodeEnum.E_503);
    }

    /**
     * 获取项目版本等
     *
     * @return {"msg":"请求成功","code":"200","data":{"address":"上海市浦东新区张江高科技园区上科路88号","contact":"+86-21-5187 6575","company":"BandWeaver","version":"V100B05","email":"info@bandweaver.cn"}}
     * @author shaosen
     * @Date 2018年12月17日
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public JSONObject getVersionInfo() {
        JSONObject returnData = new JSONObject();
        returnData.put("version", PropertiesUtil.getValue(Constants.MAXTUNNEL_VERSION));
        returnData.put("email", PropertiesUtil.getValue(Constants.MAXTUNNEL_EMAIL));
        returnData.put("contact", PropertiesUtil.getValue(Constants.MAXTUNNEL_CONTACT));
        returnData.put("company", PropertiesUtil.getValue(Constants.MAXTUNNEL_COMPANY));
        returnData.put("address", PropertiesUtil.getValue(Constants.MAXTUNNEL_ADDRESS));
        return CommonUtil.success(returnData);
    }


}