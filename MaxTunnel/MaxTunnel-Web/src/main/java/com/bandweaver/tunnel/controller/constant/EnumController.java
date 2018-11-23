package com.bandweaver.tunnel.controller.constant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.DocTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.FileTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.JobStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.MonitorTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.NodeStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FinishEnum;
import com.bandweaver.tunnel.common.biz.constant.em.TargetEnum;
import com.bandweaver.tunnel.common.biz.constant.em.UnitTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendor;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendorVersion;
import com.bandweaver.tunnel.common.biz.constant.oam.CableStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.oam.ContractStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.oam.EnergyType;
import com.bandweaver.tunnel.common.biz.constant.oam.EventLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.oam.PayTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectLevel;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectStatus;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectType;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentTypeEnum;
import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;


/**
 * 枚举接口
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class EnumController {
	
	@Autowired
	private StoreTypeService storeTypeService;
	
	
	/**开关枚举 
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月22日
	 */
	@RequestMapping(value="switch-enums",method=RequestMethod.GET)
	public JSONObject getSwitchEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (SwitchEnum e : SwitchEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**
	 * 付款方式枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"月付"},{"val":2,"key":"季付"},{"val":3,"key":"年付"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="paytype-enums",method=RequestMethod.GET)
	public JSONObject getPayTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (PayTypeEnum e : PayTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 入廊行为枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"参观"},{"val":2,"key":"维护"},{"val":3,"key":"安装"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="action-enums",method=RequestMethod.GET)
	public JSONObject getActionEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (ActionEnum e : ActionEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**
	 * 入廊申请状态枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"审核中"},{"val":2,"key":"通过"},{"val":3,"key":"拒绝"},{"val":4,"key":"结束"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="reqstatus-enums",method=RequestMethod.GET)
	public JSONObject getReqStatusEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (ProcessStatusEnum e : ProcessStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	
	/**
	 * 设备状态枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"运行中"},{"val":2,"key":"故障"},{"val":3,"key":"备品"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="equipmentstatus-enums",method=RequestMethod.GET)
	public JSONObject getEquipmentStatusEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (EquipmentStatusEnum e : EquipmentStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	

	/**
	 * 设备类型枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"安全防范"},{"val":2,"key":"视频监控"},{"val":3,"key":"环境监测"},{"val":4,"key":"电缆本体"},{"val":5,"key":"火灾报警"},{"val":6,"key":"隧道通信"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="equipmenttype-enums",method=RequestMethod.GET)
	public JSONObject getEquipmentTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (EquipmentTypeEnum e : EquipmentTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	

	/**
	 * 单位类别枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"医院"},{"val":2,"key":"学校"},{"val":3,"key":"机关单位"},{"val":4,"key":"商场"},{"val":5,"key":"银行"},{"val":6,"key":"景点"},{"val":7,"key":"火车站/机场"},{"val":8,"key":"其他"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="unittype-enums",method=RequestMethod.GET)
	public JSONObject getUnitTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (UnitTypeEnum e : UnitTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	

	/**
	 *dataType枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":0,"list":[{"val":0,"key":"无"}],"key":"无"},{"val":1,"list":[{"val":1,"key":"温度"},{"val":2,"key":"湿度"},{"val":3,"key":"氧气"},{"val":4,"key":"硫化氢"},{"val":5,"key":"甲烷"},{"val":6,"key":"一氧化碳"},{"val":8,"key":"分布式温度"},{"val":21,"key":"液位仪"},{"val":31,"key":"风机类电表"},{"val":32,"key":"灯类电表"},{"val":33,"key":"水泵类电表"},{"val":34,"key":"其他类电表"}],"key":"模拟量输入"},{"val":2,"list":[],"key":"开关量输入"},{"val":3,"list":[{"val":10,"key":"风机"},{"val":11,"key":"灯"},{"val":41,"key":"声光报警"},{"val":55,"key":"门禁"},{"val":56,"key":"电子井盖"},{"val":57,"key":"红外"},{"val":58,"key":"百叶"},{"val":59,"key":"水泵"}],"key":"状态量输入"},{"val":5,"list":[],"key":"分布式数据"},{"val":7,"list":[{"val":7,"key":"视频"}],"key":"视频"},{"val":97,"list":[{"val":9,"key":"机器人"},{"val":20,"key":"定位设备"},{"val":51,"key":"沉降"},{"val":52,"key":"伸缩缝"},{"val":53,"key":"应力"},{"val":54,"key":"外水压力"}],"key":"结构化对象"},{"val":103,"list":[],"key":"频谱数据"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="datatype-enums",method=RequestMethod.GET)
	public JSONObject getDataTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (DataType e : DataType.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			
			List<JSONObject> joList = new ArrayList<>();
			List<ObjectType> objTypeList = ObjectType.getEnumByDataType(e.getValue());
			for (ObjectType objectType : objTypeList) {
				JSONObject jo = new JSONObject();
				jo.put("key", objectType.getName());
				jo.put("val", objectType.getValue());
				joList.add(jo);
			}
			obj.put("list", joList);
			
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	

	/**
	 *objectType枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":0,"key":"无"},{"val":1,"key":"温度"},{"val":2,"key":"湿度"},{"val":3,"key":"氧气"},{"val":4,"key":"硫化氢"},{"val":5,"key":"甲烷"},{"val":6,"key":"一氧化碳"},{"val":7,"key":"视频"},{"val":8,"key":"分布式温度"},{"val":9,"key":"机器人"},{"val":10,"key":"风机"},{"val":11,"key":"灯"},{"val":20,"key":"定位设备"},{"val":31,"key":"风机类电表"},{"val":32,"key":"灯类电表"},{"val":33,"key":"水泵类电表"},{"val":34,"key":"其他类电表"},{"val":41,"key":"声光报警"},{"val":51,"key":"沉降"},{"val":52,"key":"伸缩缝"},{"val":53,"key":"应力"},{"val":54,"key":"外水压力"},{"val":55,"key":"门禁"},{"val":56,"key":"电子井盖"},{"val":57,"key":"红外"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="objecttype-enums",method=RequestMethod.GET)
	public JSONObject getObjectTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (ObjectType e : ObjectType.values()) {
			if(e==ObjectType.NONE) {
				continue;
			}
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}

	/**
	 * 流程类型枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":0,"key":"其他"},{"val":1001,"key":"巡检计划申请"},{"val":1002,"key":"巡检任务"},{"val":1003,"key":"维修工单"},{"val":2001,"key":"入廊申请"},{"val":4001,"key":"消防预案"},{"val":4003,"key":"通风预案"},{"val":4002,"key":"排水预案"}]}
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="processtype-enums",method=RequestMethod.GET)
	public JSONObject getProcessTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (ProcessTypeEnum e : ProcessTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**
	 * 管仓类型枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"综合仓"},{"val":2,"key":"电力仓"},{"val":3,"key":"燃气仓"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="storetype-enums",method=RequestMethod.GET)
	public JSONObject getStoreTypeEnumList() {
		List<JSONObject> list = new ArrayList<>();
		List<StoreType> li = storeTypeService.getList();
		for (StoreType storeType : li) {
			JSONObject obj = new JSONObject();
			obj.put("key", storeType.getName());
			obj.put("val", storeType.getId());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**管线运行状态枚举 
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"运行中"},{"val":2,"key":"故障"}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月26日
	 */
	@RequestMapping(value="cablestatus-enums",method=RequestMethod.GET)
	public JSONObject getCableStatusEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (CableStatusEnum e : CableStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**合同状态枚举
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"正常"},{"val":2,"key":"过期"}]}  
	 * @throws
	 * @author shaosen
	 * @Date 2018年7月29日
	 */
	@RequestMapping(value="contractstatus-enums",method=RequestMethod.GET)
	public JSONObject getContractStatusEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (ContractStatusEnum e : ContractStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**应急预案流程枚举 
	 * @return  
	 * {"msg":"请求成功","code":"200","data":[{"val":4001,"processKey":"FirePlanProcess","key":"消防预案"},{"val":4003,"processKey":"WindPlanProcess","key":"通风预案"},{"val":4002,"processKey":"WaterPlanProcess","key":"排水预案"}]}  
	 * @author shaosen
	 * @Date 2018年8月9日
	 */
	@RequestMapping(value="plan-enums",method=RequestMethod.GET)
	public JSONObject getPlanEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (ProcessTypeEnum e : ProcessTypeEnum.getProcessTypeList()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			obj.put("processKey", e.getProcessKey());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**告警等级枚举 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":1,"key":"提示"},{"val":2,"key":"一般"},{"val":3,"key":"严重"},{"val":4,"key":"致命"}]}
	 * @author shaosen
	 * @Date 2018年8月14日
	 */
	@RequestMapping(value="alarmlevel-enums",method=RequestMethod.GET)
	public JSONObject getAlarmLevelList() {
		List<JSONObject> list = new ArrayList<>();
		for (AlarmLevelEnum e : AlarmLevelEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**监测内容枚举 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":1,"objectTypeList":[{"val":1,"key":"温度"},{"val":2,"key":"湿度"},{"val":3,"key":"氧气"},{"val":4,"key":"硫化氢"},{"val":5,"key":"甲烷"},{"val":6,"key":"一氧化碳"},{"val":8,"key":"分布式温度"},{"val":31,"key":"风机类电表"},{"val":32,"key":"灯类电表"},{"val":33,"key":"水泵类电表"},{"val":34,"key":"其他类电表"}],"key":"环境监测"},{"val":2,"objectTypeList":[{"val":51,"key":"沉降"},{"val":52,"key":"伸缩缝"},{"val":53,"key":"应力"},{"val":54,"key":"外水压力"}],"key":"结构监测"},{"val":3,"objectTypeList":[{"val":7,"key":"视频"},{"val":9,"key":"机器人"},{"val":10,"key":"风机"},{"val":11,"key":"灯"},{"val":20,"key":"定位设备"},{"val":41,"key":"声光报警"},{"val":55,"key":"门禁"},{"val":56,"key":"电子井盖"},{"val":57,"key":"红外"}],"key":"安防监测"}]}
	 * @author shaosen
	 * @Date 2018年8月16日
	 */
	@RequestMapping(value="monitortype-enums",method=RequestMethod.GET)
	public JSONObject getMonitorTypeList() {
		List<JSONObject> list = new ArrayList<>();
		for (MonitorTypeEnum e : MonitorTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			
			//get objectTypeList
			List<JSONObject> objectTypeList = new ArrayList<>();
			List<ObjectType> eList = ObjectType.getEnumByMonitorType(e.getValue());
			for (ObjectType objectType : eList) {
				JSONObject json = new JSONObject();
				json.put("key", objectType.getName());
				json.put("val", objectType.getValue());
				objectTypeList.add(json);
			}
			obj.put("objectTypeList", objectTypeList);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**日期枚举 
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"日"},{"val":2,"key":"周"},{"val":3,"key":"月"}]}  
	 * @author shaosen
	 * @Date 2018年8月22日
	 */
	@RequestMapping(value="time-enums",method=RequestMethod.GET)
	public JSONObject getTimeList() {
		List<JSONObject> list = new ArrayList<>();
		for (TimeEnum e : TimeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	/**文件类型枚举 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":1,"type":".txt,.doc,.xml,.xls,.ppt,.docx,.xlsx,.pptx,.zip,.rar,.pdf","key":"文档"},{"val":2,"type":".mp4,.rmvb,.avi,.wmv,.3gp","key":"视频"},{"val":3,"type":".png,.JPEG,.JPG","key":"图片"}]}
	 * @author shaosen
	 * @Date 2018年8月28日
	 */
	@RequestMapping(value="filetype-enums",method=RequestMethod.GET)
	public JSONObject getFileTypeList() {
		List<JSONObject> list = new ArrayList<>();
		for (FileTypeEnum e : FileTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			obj.put("type", (String)PropertiesUtil.getValue(e.getType()));
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**资料类型枚举 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":1,"key":"管廊本体资料"},{"val":2,"key":"其他资料"}]}
	 * @author shaosen
	 * @Date 2018年8月28日
	 */
	@RequestMapping(value="doctype-enums",method=RequestMethod.GET)
	public JSONObject getDocTypeList() {
		List<JSONObject> list = new ArrayList<>();
		for (DocTypeEnum e : DocTypeEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**job状态枚举 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":0,"key":"禁用"},{"val":1,"key":"启用"}]}
	 * @author shaosen
	 * @Date 2018年9月7日
	 */
	@RequestMapping(value="jobstatus-enums",method=RequestMethod.GET)
	public JSONObject getJobStatusList() {
		List<JSONObject> list = new ArrayList<>();
		for (JobStatusEnum e : JobStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
     * 获取缺陷类型枚举
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "defectType/enum",method = RequestMethod.GET)
    public JSONObject getDefectTypeEnum() {
    	List<JSONObject> list = new ArrayList<>();
    	for(DefectType de : DefectType.values()) {
    		JSONObject m = new JSONObject();
    		m.put("key", de.getName());
    		m.put("val", de.getValue());
    		list.add(m);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 获取缺陷状态枚举
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "defectStatus/enum",method = RequestMethod.GET)
    public JSONObject getDefectStatusEnum() {
    	List<JSONObject> list = new ArrayList<>();
    	for(DefectStatus de : DefectStatus.values()) {
    		JSONObject m = new JSONObject();
    		m.put("key", de.getName());
    		m.put("val", de.getValue());
    		list.add(m);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 获取缺陷等级枚举
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "defectLevel/enum",method = RequestMethod.GET)
    public JSONObject getDefectLevelEnum() {
    	List<JSONObject> list = new ArrayList<>();
    	for(DefectLevel de : DefectLevel.values()) {
    		JSONObject m = new JSONObject();
    		m.put("key", de.getName());
    		m.put("val", de.getValue());
    		list.add(m);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
   
	
	/** 重大事件级别枚举
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value="eventlevel-enums",method=RequestMethod.GET)
	public JSONObject getEventLevelList() {
		List<JSONObject> list = new ArrayList<>();
		for (EventLevelEnum e : EventLevelEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/** 应急目标对象枚举
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":1,"key":"指定对象"},{"val":2,"key":"类型"}]}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value="em/target-enums",method=RequestMethod.GET)
	public JSONObject getTargetEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (TargetEnum e : TargetEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**应急行为枚举 
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1,"key":"提示信息类"},{"val":2,"key":"通知类"},{"val":3,"key":"联动输出类型"}]}  
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value="em/action-enums",method=RequestMethod.GET)
	public JSONObject getEmActionEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (com.bandweaver.tunnel.common.biz.constant.em.ActionEnum e : com.bandweaver.tunnel.common.biz.constant.em.ActionEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**应急结束类型枚举 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":1,"key":"自动"},{"val":2,"key":"半自动"},{"val":3,"key":"手动"}]}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value="em/finish-enums",method=RequestMethod.GET)
	public JSONObject getFinishEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (FinishEnum e : FinishEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 获取视频供应商枚举
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月7日
	 */
	@RequestMapping(value="video/vendor-enums",method=RequestMethod.GET)
	public JSONObject getVideoVendorEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (VideoVendor e : VideoVendor.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 获取视频版本枚举
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月7日
	 */
	@RequestMapping(value="video/vendor/version-enums",method=RequestMethod.GET)
	public JSONObject getVideoVendorVersionEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (VideoVendorVersion e : VideoVendorVersion.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**获取流程节点状态列表 
	 * @return  {"msg":"请求成功","code":"200","data":[{"val":1,"key":"已完成"},{"val":2,"key":"进行中"},{"val":3,"key":"待完成"}]} 
	 * @author shaosen
	 * @Date 2018年11月14日
	 */
	@RequestMapping(value="nodestatus-enums",method=RequestMethod.GET)
	public JSONObject getNodeStatusEnumList() {
		List<JSONObject> list = new ArrayList<>();
		for (NodeStatusEnum e : NodeStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			obj.put("val", e.getValue());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/** 获取能耗类型枚举
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月14日
	 */
	@RequestMapping(value = "energyType-enums", method = RequestMethod.GET)
	public JSONObject getEnergyTypeEnumList() {
		List<EnergyType> list = new ArrayList<>();
		for(EnergyType et : EnergyType.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", et.getName());
			obj.put("val", et.getValue());
			list.add(et);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
}
