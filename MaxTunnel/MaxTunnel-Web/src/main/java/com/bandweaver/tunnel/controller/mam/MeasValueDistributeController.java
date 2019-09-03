package com.bandweaver.tunnel.controller.mam;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDistributeService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDistribute;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;
import com.bandweaver.tunnel.common.biz.pojo.mam.transform.MeasValueDistributeT;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;

@Controller
@ResponseBody
public class MeasValueDistributeController {
	@Autowired
	private MeasValueDistributeService measValueDistributeService;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;


	/**接收从maxview发送的分布式数据  
	 * @param list
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月19日
	 */
	@RequestMapping(value = "measvalue-distribute/batch", method = RequestMethod.POST)
	public JSONObject addMeasValueDisBatch(@RequestBody List<MeasValueDistributeT> list) {
		String ip = ContextUtil.getRemoteIp();
		LogUtil.info("来自：" + ip + ",接收从MaxView发送的分布式数据共：" + list.size() + "条" );
		for (MeasValueDistributeT measValueDistributeT : list) {
			MeasValueDistribute measValueDistribute = new MeasValueDistribute();
			measValueDistribute.setObjectId(measValueDistributeT.getObjectId());
			measValueDistribute.setTime(measValueDistributeT.getTime());
			measValueDistribute.setSpacePrecision(measValueDistributeT.getSpacePrecision());
			measValueDistribute.setDcv(Arrays.toString(measValueDistributeT.getDcv()));
			measObjModuleCenter.updateMeasObjDistributeValue(measValueDistribute);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	

	@RequestMapping(value = "measvalue-distribute/now", method = RequestMethod.GET)
	public JSONObject getNowMeasValueAIs() {
		List<MeasObjDistribute> l = measObjModuleCenter.getMeasObjDistribute();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, l);
	}

	
}
