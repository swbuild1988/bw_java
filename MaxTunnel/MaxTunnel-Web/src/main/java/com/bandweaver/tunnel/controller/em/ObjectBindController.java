package com.bandweaver.tunnel.controller.em;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.em.ObjectBindService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoService;
import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;

/**监测对象关联其他对象（视频和预案）管理
 * @author shaosen
 * @date 2018年12月20日
 */
@Controller
@ResponseBody
public class ObjectBindController {

	
	@Autowired
	private ObjectBindService objectBindService;
	@Autowired
	private VideoService videoService;
	
	
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
	
	
	/**通过监测对象获取关联的所有预案类型 
	 * @param id
	 * @return   {"msg":"请求成功","code":"200","data":[{"name":"消防预案","id":4001},{"name":"通风预案","id":4003}]}
	 * @author shaosen
	 * @Date 2018年12月20日
	 */
	@RequestMapping(value="object-bind/{id}/plans",method=RequestMethod.GET)
	public JSONObject getPlansByObject(@PathVariable Integer id) {
		List<ObjectBind> list = objectBindService.getPlansByObject(id);
		
		List<JSONObject> returnData = new ArrayList<>();
		for (ObjectBind objectBind : list) {
			JSONObject js = new JSONObject();
			js.put("id", objectBind.getBindId());
			ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(objectBind.getBindId());
			if(processTypeEnum == ProcessTypeEnum.NONE)
				continue;
			js.put("name",processTypeEnum.getName());
			returnData.add(js);
		}
		return CommonUtil.success(returnData);
	}
	
	
	/**通过监测对象获取关联的所有视频
	 * @param id
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月20日
	 */
	@RequestMapping(value="object-bind/{id}/videos",method=RequestMethod.GET)
	public JSONObject getVideosByObject(@PathVariable Integer id) {
		List<ObjectBind> list = objectBindService.getVideosByObject(id);
		
		List<VideoDto> returnData = new ArrayList<>();
		for (ObjectBind objectBind : list) {
			Integer videoId = objectBind.getBindId();
			VideoDto videoDto = videoService.getVideoDto(videoId);
			if(StringTools.isNullOrEmpty(videoDto))
				continue;
			returnData.add(videoDto);
		}
		return CommonUtil.success(returnData);
	}
}
