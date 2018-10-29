package com.bandweaver.tunnel.controller.mam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueTabdictService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueTabdict;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

@Controller
@ResponseBody
public class MeasValueTabdictController {

	@Autowired
	private MeasValueTabdictService measValueTabdictService;
	
	/**
	 * @Description: 添加
	 * @param @param record
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月29日
	 */
	@RequestMapping(value="/measvalue-tabdict",method=RequestMethod.POST)
	public JSONObject addMeasValueTabdict(@RequestBody MeasValueTabdict record ) {
		
		measValueTabdictService.addMeasValueTabdict(record);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * @Description: 批量添加
	 * @param @param list
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月29日
	 */
	@RequestMapping(value="/measvalue-tabdict/list",method=RequestMethod.POST)
	public JSONObject addMeasValueTabdictBatch(@RequestBody List<MeasValueTabdict> list) {
		measValueTabdictService.addMeasValueTabdictBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list.size());
	}
	
	
	/**
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月29日
	 */
	@RequestMapping(value="/measvalue-tabdict/{id}",method=RequestMethod.GET)
	public JSONObject getMeasValueTabdictById(@PathVariable("id")int id) {
		MeasValueTabdict result = measValueTabdictService.getMeasValueTabdictById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
	}	
	
	/**
	 * @Description: 条件查询
	 * @param @param measValueTabdict 对象中包含有开始时间和结束时间，因此我们可以用现有的对象来封装参数
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月29日
	 */
	@RequestMapping(value="/measvalue-tabdict/condition",method=RequestMethod.POST)
	public JSONObject getMeasValueTabdictListByconditon(@RequestBody MeasValueTabdict measValueTabdict) {

		//TODO
		
		return null;
	}
}
