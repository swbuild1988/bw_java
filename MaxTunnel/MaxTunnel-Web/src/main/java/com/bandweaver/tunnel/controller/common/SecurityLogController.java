package com.bandweaver.tunnel.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.SecurityLogService;
import com.bandweaver.tunnel.common.biz.pojo.SecurityLog;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

@Controller
@ResponseBody
@RequestMapping("/securitylogs")
public class SecurityLogController {

	@Autowired
	private SecurityLogService securityLogService;

	
}
