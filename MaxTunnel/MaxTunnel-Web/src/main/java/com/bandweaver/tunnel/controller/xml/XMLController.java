package com.bandweaver.tunnel.controller.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;
import com.bandweaver.tunnel.common.biz.pojo.xml.CommonEnum;
import com.bandweaver.tunnel.common.biz.pojo.xml.ComplexObjectConvert;
import com.bandweaver.tunnel.common.biz.pojo.xml.Config;
import com.bandweaver.tunnel.common.biz.pojo.xml.EquipmentTypeFile;
import com.bandweaver.tunnel.common.biz.pojo.xml.ObjTypeParam;
import com.bandweaver.tunnel.common.biz.pojo.xml.TunnelParam;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.XMLUtil;

@Controller
@ResponseBody
public class XMLController {

	@Autowired
	private XMLService xmlService;

	/**
	 * 获取config.xml文件的所有信息
	 * @return
	 * @author ya.liu
	 * @Date 2019年6月1日
	 */
	private Config getXMLAllInfo() {
		return xmlService.getXMLAllInfo();
	}
	
	/**
	 * 修改config.xml文件
	 * @param config
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	private void setXMLAllInfo(Config config) {
		xmlService.setXMLInfo(config);
	}

	/**
	 * 获取xml中的所有信息
	 * @return
	 * @author ya.liu
	 * @Date 2019年6月1日
	 */
	@RequestMapping(value = "config/xml", method = RequestMethod.GET)
    public JSONObject getAllInfo() {
    	Config config = getXMLAllInfo();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, config);
    }
	
	/**
	 * 获取大屏各个重要点位置
	 * @return
	 * @author ya.liu
	 * @Date 2019年6月1日
	 */
	@RequestMapping(value = "config/xml/units", method = RequestMethod.GET)
    public JSONObject getUnits() {
    	Config config = getXMLAllInfo();
    	List<RelatedUnit> units = config.getUnits();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, units);
    }
	
	/**
	 * 获取管廊参数以及设备信息
	 * @return
	 * @author ya.liu
	 * @Date 2019年6月27日
	 */
	@RequestMapping(value = "config/xml/tunnel-param/equipment-info", method = RequestMethod.GET)
    public JSONObject getTunnelParamAndEquipmentInfos() {
		JSONObject json = new JSONObject();
		
    	Config config = getXMLAllInfo();
    	TunnelParam tunnelParam = config.getTunnelParam();
    	json.put("tunnelParam", tunnelParam);
    	List<EquipmentTypeFile> equipmentInfo = config.getTypeFiles();
    	json.put("equipmentInfo", equipmentInfo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
    }
	
	/**
	 * 获取告警级别对应的时间
	 * @return
	 * @author ya.liu
	 * @Date 2019年6月27日
	 */
	@RequestMapping(value = "config/xml/alarm-time", method = RequestMethod.GET)
    public JSONObject getAlarmTime() {
    	Config config = getXMLAllInfo();
    	List<CommonEnum> levels = config.getAlarmTimes();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, levels);
    }
	
	/**
	 * 获取告警级别以及对应的时间
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	@RequestMapping(value = "config/xml/alarm-time/json", method = RequestMethod.GET)
    public JSONObject getAlarmTimeToJson() {
    	Config config = getXMLAllInfo();
    	List<CommonEnum> levels = config.getAlarmTimes();
    	List<JSONObject> list = new ArrayList<>();
    	for(AlarmLevelEnum alarm :AlarmLevelEnum.values()) {
    		JSONObject json = new JSONObject();
    		for(CommonEnum com : levels) {
    			if(alarm.getValue() == com.getId()) {
    				json.put("id", alarm.getValue());
    				json.put("name", com.getName());
    				json.put("levelName", alarm.getName());
    				list.add(json);
    			}
    		}
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
	
	/**
	 * 获取入侵延时关闭时间,单位秒
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月4日
	 */
	@RequestMapping(value = "config/xml/close-time", method = RequestMethod.GET)
    public JSONObject getCloseTime() {
    	Config config = getXMLAllInfo();
    	double s = config.getCloseTime();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, s);
    }
	
	/**
	 * 获取监测对象的复杂类型
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月9日
	 */
	@RequestMapping(value = "config/xml/complex-object-converts", method = RequestMethod.GET)
    public JSONObject getComplexObjectConverts() {
    	Config config = getXMLAllInfo();
    	List<ComplexObjectConvert> levels = config.getComplexObjectConverts();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, levels);
    }
	
	/**
	 * AI数据获取小数点位数
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/decimal", method = RequestMethod.GET)
    public JSONObject getDecimal() {
    	Config config = getXMLAllInfo();
    	Integer decimal = config.getDecimal();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, decimal);
    }
	
	/**
	 * 获取管廊内灯光的距离
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/light-distance", method = RequestMethod.GET)
    public JSONObject getLightDistance() {
    	Config config = getXMLAllInfo();
    	Double lightDistance = config.getLightDistance();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, lightDistance);
    }
	
	/**
	 * 获取ai数据的最值与测量值
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/objTypeParams", method = RequestMethod.GET)
    public JSONObject getobjTypeParams() {
    	Config config = getXMLAllInfo();
    	List<ObjTypeParam> lists = config.getObjTypeParams();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, lists);
    }
	
	/**
	 * 获取要展示与不展示的检测对象类型
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/objTypes", method = RequestMethod.GET)
    public JSONObject getobjTypes() {
    	Config config = getXMLAllInfo();
    	List<Integer> lists = config.getObjectTypeIds();
    	// 要展示的对象类型
    	List<JSONObject> shows = new ArrayList<>();
    	// 没展示的对象类型
    	List<JSONObject> unshows = new ArrayList<>();
    	for(ObjectType type :ObjectType.values()) {
    		JSONObject obj = new JSONObject();
    		obj.put("id", type.getValue());
    		obj.put("name", type.getName());
    		if(lists.contains(type.getValue()))
    			shows.add(obj);
    		else
    			unshows.add(obj);
    	}
    	JSONObject json = new JSONObject();
    	json.put("show", shows);
    	json.put("unshow", unshows);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
    }
	
	
	
	/**
	 * 修改大屏各个重要点位置
	 * @param List<RelatedUnit>
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	@RequestMapping(value = "config/xml/units", method = RequestMethod.POST)
    public JSONObject updateUnits(@RequestBody List<RelatedUnit> list) {
    	Config config = getXMLAllInfo();
    	config.setUnits(list);
    	setXMLAllInfo(config);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改管廊参数信息
	 * @param tunnelParam
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	@RequestMapping(value = "config/xml/tunnel-param", method = RequestMethod.POST)
    public JSONObject updateTunnelParam(@RequestBody TunnelParam tunnelParam) {
    	Config config = getXMLAllInfo();
    	config.setTunnelParam(tunnelParam);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改设备参数
	 * @param List<EquipmentTypeFile>
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	@RequestMapping(value = "config/xml/equipment-info", method = RequestMethod.POST)
    public JSONObject updateEquipmentInfo(@RequestBody List<EquipmentTypeFile> list) {
    	Config config = getXMLAllInfo();
    	config.setTypeFiles(list);
    	setXMLAllInfo(config);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改告警延迟时间
	 * @param alarmTimes
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	@RequestMapping(value = "config/xml/alarm-time", method = RequestMethod.POST)
    public JSONObject updateAlarmTime(@RequestBody List<CommonEnum> alarmTimes) {
    	Config config = getXMLAllInfo();
    	config.setAlarmTimes(alarmTimes);
    	setXMLAllInfo(config);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改入侵延时关闭时间
	 * @param closeTime
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月29日
	 */
	@RequestMapping(value = "config/xml/close-time", method = RequestMethod.POST)
    public JSONObject updateCloseTime(@RequestBody Double closeTime) {
    	Config config = getXMLAllInfo();
    	if(closeTime != null) {
    		config.setCloseTime(closeTime);
    		setXMLAllInfo(config);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改监测对象的复杂类型
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月9日
	 */
	@RequestMapping(value = "config/xml/complex-object-converts", method = RequestMethod.POST)
    public JSONObject updateComplexObjectConverts(@RequestBody List<ComplexObjectConvert> list) {
    	Config config = getXMLAllInfo();
    	config.setComplexObjectConverts(list);
    	setXMLAllInfo(config);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * AI数据修改小数点位数
	 * @param decimal
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/decimal", method = RequestMethod.POST)
    public JSONObject setDecimal(@RequestBody Integer decimal) {
    	Config config = getXMLAllInfo();
    	if(decimal != null) {
    		config.setDecimal(decimal);
    		setXMLAllInfo(config);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改管廊内灯光的距离
	 * @param lightDistance
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/light-distance", method = RequestMethod.POST)
    public JSONObject setLightDistance(@RequestBody Double lightDistance) {
    	Config config = getXMLAllInfo();
    	if(lightDistance != null) {
    		config.setLightDistance(lightDistance);
    		setXMLAllInfo(config);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改ai数据的最值与测量值
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/objTypeParams", method = RequestMethod.POST)
    public JSONObject setobjTypeParams(@RequestBody List<ObjTypeParam> objTypeParams) {
    	Config config = getXMLAllInfo();
    	config.setObjTypeParams(objTypeParams);
    	setXMLAllInfo(config);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
	
	/**
	 * 修改要展示的检测对象类型
	 * @param List<Integer>
	 * @return
	 * @author ya.liu
	 * @Date 2019年8月30日
	 */
	@RequestMapping(value = "config/xml/objTypes", method = RequestMethod.POST)
    public JSONObject setobjTypes(@RequestBody List<Integer> objTypes) {
    	Config config = getXMLAllInfo();
    	config.setObjectTypeIds(objTypes);
    	setXMLAllInfo(config);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
}
