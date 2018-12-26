package com.bandweaver.tunnel.controller.em;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.em.ObjectBindService;
import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

/**监测对象关联其他对象（视频和预案）管理
 * @author shaosen
 * @date 2018年12月20日
 */
@Controller
@ResponseBody
public class ObjectBindController {

	
	@Autowired
	private ObjectBindService objectBindService;
	
	
	/**添加 
	 * @param objectId 监测对象id
	 * @param bindId 视频或预案id
	 * @param type {"msg":"请求成功","code":"200","data":[{"val":1,"key":"预案"},{"val":2,"key":"视频"}]}
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月20日
	 */
	@RequestMapping(value="object-bind",method=RequestMethod.POST)
	public JSONObject add(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "objectId,bindId,type");
		ObjectBind objectBind = CommonUtil.parse2Obj(reqJson, ObjectBind.class);
		objectBindService.add(objectBind);
		return CommonUtil.success();
	}
	
	
}
