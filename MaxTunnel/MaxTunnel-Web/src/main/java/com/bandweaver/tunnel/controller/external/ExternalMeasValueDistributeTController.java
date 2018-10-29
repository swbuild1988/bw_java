package com.bandweaver.tunnel.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDistributeService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDistribute;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

@Controller
@ResponseBody
@RequestMapping("bandweaver")
public class ExternalMeasValueDistributeTController {
	@Autowired
	private MeasValueDistributeService measValueDistributeService;

	@RequestMapping(value = "measvalue-distributed", method = RequestMethod.POST)
	public JSONObject addValue(@RequestBody MeasValueDistribute valueDistributeT) {
		measValueDistributeService.addMeasValueDis(valueDistributeT);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
}
