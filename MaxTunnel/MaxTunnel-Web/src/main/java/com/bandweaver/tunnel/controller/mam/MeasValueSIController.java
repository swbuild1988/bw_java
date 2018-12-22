package com.bandweaver.tunnel.controller.mam;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueSIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSI;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class MeasValueSIController {
	@Autowired
	private MeasValueSIService measValueSIService;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;

	 /**接收MaxView发送过来的数据 
     * @param list
     * @return   
     * @author shaosen
     * @Date 2018年10月18日
     */
	@RequestMapping(value = "measvalue-si/batch", method = RequestMethod.POST)
	public JSONObject addMeasValueSIBatch(@RequestBody List<MeasValueSI> list) {
		LogUtil.info("接收到MaxView发送的SI数据共：" + list.size() + "条" );
        for (MeasValueSI measValueSI : list) {
            measObjModuleCenter.updateMeasObjSIValue(measValueSI);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	@RequestMapping(value = "measvalue-si", method = RequestMethod.POST)
	public JSONObject addMeasValueSI(@RequestBody MeasValueSI valueSI) {
		measValueSIService.addMeasValueSI(valueSI);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	
}
