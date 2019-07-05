package com.bandweaver.tunnel.controller.xml;

import java.util.List;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;
import com.bandweaver.tunnel.common.biz.pojo.xml.CommonEnum;
import com.bandweaver.tunnel.common.biz.pojo.xml.Config;
import com.bandweaver.tunnel.common.biz.pojo.xml.EquipmentTypeFile;
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
}
