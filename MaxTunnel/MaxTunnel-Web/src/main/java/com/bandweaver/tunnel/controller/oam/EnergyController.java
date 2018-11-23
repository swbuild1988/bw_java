package com.bandweaver.tunnel.controller.oam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.oam.EnergyDto;
import com.bandweaver.tunnel.common.biz.dto.oam.EnergySimpleDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.itf.oam.EnergyService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.oam.Energy;
import com.bandweaver.tunnel.common.biz.vo.oam.EnergyVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**能耗管理
 * @author shaosen
 * @date 2018年5月31日
 */
@Controller
@ResponseBody
public class EnergyController {

	@Autowired
	private EnergyService energyService;
	@Autowired
	private TunnelService tunnelService;
    @Autowired
    private MeasValueAIService measValueAIService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    
    
    //*************************************************************************************
    //*********能耗管理-类别详情-start***********************************************************
    //*************************************************************************************
    
	
	/*@RequestMapping(value="tunnels/energies-det/total",method=RequestMethod.POST)
	public JSONObject getTunnelDetTotal(@RequestBody Map<String, Object> map ) {
		List<Map<String, Object>> list = getObjSub(map);
		
		Double total = new Double(0.00);
		for (Map<String, Object> m : list) {
			total = MathUtil.add(DataTypeUtil.toDouble(m.get("sub")), total);
		}
		
		JSONObject result = new JSONObject();
		result.put("key", "总能耗");
		result.put("val", total);
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}*/
    
    
	/**获取指定时间段内各类能耗总和--new
	 * @param id 管廊id，必须
	 * @param startTime 开始时间（时间戳）可选
	 * @param endTime 结束时间（时间戳） 可选
	 * @return   {"msg":"请求成功","code":"200","data":{"val":18.83,"key":"总能耗"}}
	 * @author shaosen
	 * @Date 2018-11-7
	 */
    @RequestMapping(value="tunnels/energies-det/total",method=RequestMethod.POST)
    public JSONObject getTunnelDetTotal(@RequestBody Map<String, Object> map ) {
    	
    	Date startTime = DataTypeUtil.toDateFromTimestamp(map.get("startTime"));
		Date endTime = DataTypeUtil.toDateFromTimestamp(map.get("endTime"));
		Integer tunnelId = DataTypeUtil.toInteger(map.get("id"));
    	
		List<EnergySimpleDto> list = energyService.getEnergiesByTunnelIdAndTimeCondition(tunnelId, startTime, endTime);
		
		double sum = 0.0;
		for (EnergySimpleDto energySimpleDto : list) {
			sum = MathUtil.add(sum, energySimpleDto.getValue());
		}
    	
    	JSONObject result = new JSONObject();
    	result.put("key", "总能耗");
    	result.put("val", sum);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
    }

    
   
	/*@RequestMapping(value="tunnels/energies-det/ratio",method=RequestMethod.POST)
	public JSONObject getTunnelDetRatio(@RequestBody Map<String, Object> map ) {
		
		List<Map<String, Object>> list = getObjSub(map);
		LogUtil.info("list size : " + list.size());
		
		List<JSONObject> result = new ArrayList<>();
		
		for(ObjectType em : ObjectType.getArr()) {
			JSONObject j = new JSONObject();
			j.put("key", em.getName());
			LogUtil.info("key : " + em.getName());
			
			List<Map<String, Object>> li = new ArrayList<>();
			
			for (Map<String, Object> m : list) {
				MeasObj measObj = measObjModuleCenter.getMeasObj(DataTypeUtil.toInt(m.get("objectId")));
				if(em == ObjectType.getEnum(measObj.getObjtypeId().intValue())) { li.add(m); 
				LogUtil.info(" add mo : " + m);
				}
			}
			
			Double total = new Double(0.00);
			for (Map<String, Object> m2 : li) {
				total = MathUtil.add(DataTypeUtil.toDouble(m2.get("sub")), total);
			}
			j.put("val", total);
			result.add(j);
			
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}*/
	
	
	
	/**获取指定时间段内各类能耗占比--new
	 * @param id 管廊id，必须
	 * @param startTime 开始时间（时间戳）可选
	 * @param endTime 结束时间（时间戳） 可选
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":0.0,"key":"风机类电表"},{"val":15.83,"key":"灯类电表"},{"val":3.0,"key":"水泵类电表"},{"val":0,"key":"其他类电表"}]}
	 * @author shaosen
	 * @Date 2018年11月7日
	 */
	@RequestMapping(value="tunnels/energies-det/ratio",method=RequestMethod.POST)
	public JSONObject getTunnelDetRatio(@RequestBody Map<String, Object> map ) {
		
		Date startTime = DataTypeUtil.toDateFromTimestamp(map.get("startTime"));
		Date endTime = DataTypeUtil.toDateFromTimestamp(map.get("endTime"));
		Integer tunnelId = DataTypeUtil.toInteger(map.get("id"));
		
		List<JSONObject> result = new ArrayList<>();
		
		for(ObjectType em : ObjectType.getArr()) {
			JSONObject j = new JSONObject();
			j.put("key", em.getName());
			
			EnergyVo vo = new EnergyVo();
			vo.setTunnelId(tunnelId);
			vo.setStartTime(startTime);
			vo.setEndTime(endTime);
			vo.setObjectType(em.getValue());
			List<EnergySimpleDto> list = energyService.getEnergiesByCondition(vo);
			
			double sum = 0.0;
			for (EnergySimpleDto energySimpleDto : list) {
				sum = MathUtil.add(sum, energySimpleDto.getValue());
			}
			
			j.put("val", sum );
			result.add(j);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}
    
	
	
	
	/*@RequestMapping(value="tunnels/{id}/energies-det/{value}",method=RequestMethod.GET)
	public JSONObject getTunnelDet(@PathVariable("id")Integer id,@PathVariable("value")Integer value) {
		
		TimeEnum tem = TimeEnum.getEnum(value);
		
		//时间集合
		List<Map<String, Date>> list = DateUtil.getStartTimeAndEndTimeByIntervalvalue(tem);
		
		//数据过滤
		List<Integer> objIdList = new ArrayList<>();
		List<MeasObjAI> aiList = measObjModuleCenter.getMeasObjAIs();
		for (MeasObjAI measObjAI : aiList) {
			boolean boo = Arrays.asList(ObjectType.getArr()).contains(ObjectType.getEnum(measObjAI.getObjtypeId()));
			if(boo) { 
				if(measObjAI.getTunnelId() == id) {
					LogUtil.info(" get MO by tunnelID , objID  :" + measObjAI.getId());
					objIdList.add(measObjAI.getId());
				}
			}
		}
		
		List<JSONObject> result = new ArrayList<>();
		
		for(ObjectType em : ObjectType.getArr()) {
			JSONObject json = new JSONObject();
			json.put("key", em.getName());
			LogUtil.info("key : " + em.getName());
			
			List<Integer> idList = new ArrayList<>();
			for (Integer objId : objIdList) {
				MeasObj measObj = measObjModuleCenter.getMeasObj(objId);
				if(em == ObjectType.getEnum(measObj.getObjtypeId())) {
					idList.add(objId);
					LogUtil.info(" add mo : " + measObj);
				}
			}
			
			List<JSONObject> vList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsonObj = new JSONObject();
				Date startTime = list.get(list.size() - 1 - i).get("startDay");
				Date endTime = list.get(list.size() - 1 - i).get("endDay");

				if (tem == TimeEnum.WEEK) {
					jsonObj.put("key", "第" + DateUtil.getNowWeek(startTime) + "周");
				} else if (tem == TimeEnum.MONTH) {
					jsonObj.put("key", DateUtil.getNowMonth(startTime) + "月");
				} else if (tem == TimeEnum.DAY) {
					jsonObj.put("key", DateUtil.getDate2YYYYMMdd(startTime) + "日");
				}
				
				//get val
				Double total = new Double(0.00);
				for (Integer objId : idList) {
					List<MeasValueAI> valList = measValueAIService.getListByObjectAndTime(objId, startTime, endTime);
					if(valList != null && valList.size() > 0 ) {
						MeasValueAI newAi = valList.get(0);
						MeasValueAI oldAi = valList.get(valList.size() - 1);
						Double sub = MathUtil.sub(newAi.getCV(), oldAi.getCV());
						total = MathUtil.add(sub, total);
					}
				}
				jsonObj.put("val", total);
				vList.add(jsonObj);
			}
			json.put("val", vList);
			result.add(json);
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}*/
	
	
	/** 按照指定周期显示各类能耗使用曲线图--new
	 * @param id 管廊id
	 * @param value 日期枚举
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":[{"val":0.0,"key":"1月"},{"val":0.0,"key":"2月"},{"val":0.0,"key":"3月"},{"val":0.0,"key":"4月"},{"val":0.0,"key":"5月"},{"val":0.0,"key":"6月"},{"val":0.0,"key":"7月"},{"val":12744.9,"key":"8月"}],"key":"风机类电表"},{"val":[{"val":0.0,"key":"1月"},{"val":0.0,"key":"2月"},{"val":0.0,"key":"3月"},{"val":0.0,"key":"4月"},{"val":0.0,"key":"5月"},{"val":0.0,"key":"6月"},{"val":0.0,"key":"7月"},{"val":5469.3,"key":"8月"}],"key":"灯类电表"},{"val":[{"val":0.0,"key":"1月"},{"val":0.0,"key":"2月"},{"val":0.0,"key":"3月"},{"val":0.0,"key":"4月"},{"val":0.0,"key":"5月"},{"val":0.0,"key":"6月"},{"val":0.0,"key":"7月"},{"val":1816.2,"key":"8月"}],"key":"水泵类电表"},{"val":[{"val":0.0,"key":"1月"},{"val":0.0,"key":"2月"},{"val":0.0,"key":"3月"},{"val":0.0,"key":"4月"},{"val":0.0,"key":"5月"},{"val":0.0,"key":"6月"},{"val":0.0,"key":"7月"},{"val":7280.1,"key":"8月"}],"key":"其他类电表"}]}
	 * @author shaosen
	 * @Date 2018年11月7日
	 */
	@RequestMapping(value="tunnels/{id}/energies-det/{value}",method=RequestMethod.GET)
	public JSONObject getTunnelDet(@PathVariable("id")Integer id,@PathVariable("value")Integer value) {
		
		TimeEnum tem = TimeEnum.getEnum(value);
		//时间集合
		List<Map<String, Date>> list = DateUtil.getStartTimeAndEndTimeByIntervalvalue(tem);
			
		List<JSONObject> result = new ArrayList<>();
		
		for(ObjectType em : ObjectType.getArr()) {
			
			JSONObject json = new JSONObject();
			json.put("key", em.getName());
			
			List<JSONObject> vList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsonObj = new JSONObject();
				Date startTime = list.get(list.size() - 1 - i).get("startDay");
				Date endTime = list.get(list.size() - 1 - i).get("endDay");

				if (tem == TimeEnum.WEEK) {
					jsonObj.put("key", "第" + DateUtil.getNowWeek(startTime) + "周");
				} else if (tem == TimeEnum.MONTH) {
					jsonObj.put("key", DateUtil.getNowMonth(startTime) + "月");
				} else if (tem == TimeEnum.DAY) {
					jsonObj.put("key", DateUtil.getDate2YYYYMMdd(startTime) + "日");
				}
				
				EnergyVo vo = new EnergyVo();
				vo.setTunnelId(id);
				vo.setStartTime(startTime);
				vo.setEndTime(endTime);
				vo.setObjectType(em.getValue());
				List<EnergySimpleDto> ls = energyService.getEnergiesByCondition(vo);
				double sum = 0.0;
				for (EnergySimpleDto energySimpleDto : ls) {
					sum = MathUtil.add(sum, energySimpleDto.getValue());
				}
				jsonObj.put("val", sum);
				vList.add(jsonObj);
			}
			
			json.put("val", vList);
			result.add(json);
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}
	
	
	
    //*************************************************************************************
    //*********能耗管理-类别详情-end***********************************************************
    //*************************************************************************************
    
	
	

	/**
	 * 获取指定时间段内，各管廊下的各类能耗总和与占比
	 * @param map
	 * @return
	 * @author liuya
	 * @Date 2018年9月14日
	 */
	@RequestMapping(value="energies-det/ratio/total",method=RequestMethod.POST)
	public JSONObject getDetRatioAndTotal(@RequestBody(required = false) Map<String, Object> map ) {
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		
		List<JSONObject> ls = new ArrayList<>();
		for(TunnelSimpleDto dto : dtos) {
			LogUtil.info("dto" + dto);
			JSONObject res = new JSONObject();
			map.put("id", dto.getId());
			LogUtil.info("id" + dto.getId());
			List<Map<String, Object>> list = getObjSub(map);
			
			List<JSONObject> result = new ArrayList<>();
			
			for(ObjectType em : ObjectType.getArr()) {
				JSONObject j = new JSONObject();
				j.put("key", em.getName());
				List<Map<String, Object>> li = new ArrayList<>();
				
				for (Map<String, Object> m : list) {
					MeasObj measObj = measObjModuleCenter.getMeasObj(DataTypeUtil.toInt(m.get("objectId")));
					if(em == ObjectType.getEnum(measObj.getObjtypeId().intValue())) { li.add(m); 
					}
				}
				Double total = new Double(0.00);
				for (Map<String, Object> m2 : li) {
					total = MathUtil.add(DataTypeUtil.toDouble(m2.get("sub")), total);
				}
				j.put("val", total);
				result.add(j);
			}
			Double total = new Double(0.00);
			for (Map<String, Object> m : list) {
				total = MathUtil.add(DataTypeUtil.toDouble(m.get("sub")), total);
			}
			JSONObject t = new JSONObject();
			t.put("key", "总能耗");
			t.put("val", total);
			result.add(t);
			res.put("tunnelId", dto.getId());
			res.put("tunnelName", dto.getName());
			res.put("value", result);
			ls.add(res);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,ls);
	}
	
	
	
	/**通过objId和开始结束时间查找缓存中的电表监测对象并计算电量消耗
	 * @param map
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月6日
	 */
	private List<Map<String, Object>> getObjSub(Map<String, Object> map) {
		
		Date startTime = DataTypeUtil.toDateFromTimestamp(map.get("startTime"));
		Date endTime = DataTypeUtil.toDateFromTimestamp(map.get("endTime"));
		Integer id = DataTypeUtil.toInteger(map.get("id"));
		
		//获取监测类型为电表的数据
		List<Integer> objIdList = new ArrayList<>();
		
		//数据过滤
		List<MeasObjAI> aiList = measObjModuleCenter.getMeasObjAIs();
		for (MeasObjAI measObjAI : aiList) {
			boolean boo = Arrays.asList(ObjectType.getArr()).contains(ObjectType.getEnum(measObjAI.getObjtypeId()));
			if(boo) {
				if(measObjAI.getTunnelId().equals(id)) {
					LogUtil.info(" get MO by tunnelID , objID  :" + measObjAI.getId());
					objIdList.add(measObjAI.getId()); 
				}
			}
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		if(objIdList != null && objIdList.size() > 0 ) {
			
			for (Integer objectId : objIdList) {
				List<MeasValueAI> valList = measValueAIService.getListByObjectAndTime(objectId, startTime, endTime);
				if(valList != null && valList.size() > 0 ) {
					MeasValueAI newAi = valList.get(0);
					MeasValueAI oldAi = valList.get(valList.size() - 1);
					Double sub = MathUtil.sub(newAi.getCV(), oldAi.getCV());
//					LogUtil.info(" sub : " + sub);
		
					Map<String, Object> m = new HashMap<>();
					m.put("objectId", objectId);
					m.put("sub", sub);
					list.add(m);
				}
			}
			
		}
		return list;
	}
	
	
	/**
	 * @Description: 添加
	 * @param @param energy
	 * @param @return
	 * @return JSONObject
	 * @throws @author shaosen
	 * @date 2018年5月31日
	 */
	@RequestMapping(value = "/energies", method = RequestMethod.POST)
	public JSONObject addEnergy(@RequestBody Energy energy) {
		energyService.addEnergy(energy);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}



	/**
	 * @Description: 管廊能耗统计-饼状图
	 * @param @return
	 * @return JSONObject
	 * @throws @author shaosen
	 * @date 2018年5月31日
	 */
	@RequestMapping(value = "/tunnels/items/energies", method = RequestMethod.GET)
	public JSONObject getTunnelEnergyConsumptionCount() {

		// 获取所有管廊
		List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		List<JSONObject> objList = new ArrayList<JSONObject>();

		for (TunnelSimpleDto tunnel : tunnelList) {
			JSONObject object = new JSONObject();
			object.put("key", tunnel.getName());
			Double total = energyService.getTotalValueByTunnelId(tunnel.getId());
			object.put("val", total == null ? 0.00 : total);
			objList.add(object);
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, objList);
	}

	/**
	 * @Description: 获取每条管廊单位长度的总能耗
	 * @param @return
	 * @return JSONObject
	 * @throws @author shaosen
	 * @date 2018年6月8日
	 */
	@RequestMapping(value = "/tunnels/avg-energies", method = RequestMethod.GET)
	public JSONObject getAvgTunnelEnergyConsumption() {

		List<JSONObject> list = new ArrayList<>();

		List<TunnelDto> tunnelList = tunnelService.getDtoList();
		for (TunnelDto tunnel : tunnelList) {
			JSONObject json = new JSONObject();
			json.put("key", tunnel.getName());
			Double total = energyService.getTotalValueByTunnelId(tunnel.getId());
			if (total == null) {
				continue;
			}
			json.put("val", total / tunnel.getLength());
			list.add(json);
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}



	/**
	 * @Description: 获取历史总能耗，年度能耗，月度能耗，本周能耗
	 * @param @param value 1 : 历史总能耗 2 : 年度能耗 3: 月度能耗 4: 本周能耗
	 * @param @return
	 * @return JSONObject
	 * @throws @author shaosen
	 * @date 2018年6月8日
	 */
	@RequestMapping(value = "/tunnels/energies/timetype/{value}", method = RequestMethod.GET)
	public JSONObject getTunnelEnergyByTimeType(@PathVariable Integer value) {

		JSONObject json = new JSONObject();

		switch (value) {
		case 1:// 历史总能耗
			Double historyTotalValue = energyService.getSumByTime(null, null);
			json.put("key", "历史总能耗");
			json.put("val", historyTotalValue == null ? 0.00 : historyTotalValue);
			break;
		case 2:// 本年度能耗
			Double yearValue = energyService.getSumByTime(DateUtil.getBeginDayOfYear(), new Date());
			json.put("key", "本年度能耗");
			json.put("val", yearValue == null ? 0.00 : yearValue);
			break;
		case 3:// 本月度能耗
			Double monthValue = energyService.getSumByTime(DateUtil.getBeginDayOfMonth(), new Date());
			json.put("key", "本月度能耗");
			json.put("val", monthValue == null ? 0.00 : monthValue);
			break;
		case 4:// 本周能耗
			Double weekValue = energyService.getSumByTime(DateUtil.getBeginDayOfWeek(), new Date());
			json.put("key", "本周能耗");
			json.put("val", weekValue == null ? 0.00 : weekValue);
			break;

		default:
			break;
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
	}

	/**
	 * @Description: 根据起止日期条件查询各管廊能耗列表
	 * @param @param map
	 * @param @return
	 * @return JSONObject
	 * @throws @author shaosen
	 * @date 2018年6月1日
	 */
	@RequestMapping(value = "/tunnels/energies/time", method = RequestMethod.POST)
	public JSONObject getTunnelEngergyConsumptionListByTime(@RequestBody Map<String, Object> map) {

		Date startTime = DataTypeUtil.toDateFromTimestamp(map.get("startTime"));
		Date endTime = DataTypeUtil.toDateFromTimestamp(map.get("endTime"));

		List<EnergyDto> list = energyService.getTunnelEngergyConsumptionListByTime(startTime, endTime);
		
		//如果要查询的时间段内没有管廊的数据，需要赋予0值显示
		List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		List<Integer> idList = new ArrayList<>();
		if(list.size() != tunnelList.size()) {
			
			for (EnergyDto energyDto : list) {
				idList.add(energyDto.getTunnelId());
			}
			
			for (TunnelSimpleDto tunnel : tunnelList) {
				if(!idList.contains(tunnel.getId())) {
					EnergyDto dto = new EnergyDto();
					dto.setAvgUnitPrice(0.00);
					dto.setTunnelId(tunnel.getId());
					dto.setTunnelName(tunnelService.getNameById(tunnel.getId()));
					dto.setTotalValue(0.00);
					list.add(dto);
				}
			}
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}

	/**
	 * @Description: 根据周期查询综合管廊耗电量
	 * @param @param value ={1,2,3} 1:周 ,2：月, 3:季度
	 * @param @return
	 * @return JSONObject
	 * @throws @author shaosen
	 * @date 2018年6月1日
	 */
	@RequestMapping(value = "/tunnels/energies/interval/{value}", method = RequestMethod.GET)
	public JSONObject getTunnelEngergyConsumptionListByInterval(@PathVariable("value") Integer value) {

		TimeEnum em = TimeEnum.getEnum(value);
		List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		List<JSONObject> resultList = new ArrayList<>();

		for (TunnelSimpleDto tunnel : tunnelList) {
			JSONObject tunnelJson = new JSONObject();
			tunnelJson.put("key", tunnel.getName());

			List<JSONObject> objList = new ArrayList<>();
			List<Map<String, Date>> list = DateUtil.getStartTimeAndEndTimeByIntervalvalue(em);
			for (int i = 0; i < list.size(); i++) {

				JSONObject jsonObj = new JSONObject();
				Date startTime = list.get(list.size() - 1 - i).get("startDay");
				Date endTime = list.get(list.size() - 1 - i).get("endDay");

				if (em == TimeEnum.WEEK) {
					jsonObj.put("key", "第" + DateUtil.getNowWeek(startTime) + "周");
				} else if (em == TimeEnum.MONTH) {
					jsonObj.put("key", DateUtil.getNowMonth(startTime) + "月");
				} else if (em == TimeEnum.DAY) {
					jsonObj.put("key", DateUtil.getDate2YYYYMMdd(startTime)+"日");
				}
				Double totalValue = energyService.getEnergyByTunnelIdAndTime(tunnel.getId(), startTime, endTime);
				jsonObj.put("val", totalValue == null ? 0.00 : totalValue);
				objList.add(jsonObj);
			}
			tunnelJson.put("val", objList);
			resultList.add(tunnelJson);

		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, resultList);
		
	}

	
	
	/**
	 * @Description: 根据管廊id获取指定日期内的能耗数据,并分页
	 * @param @param vo
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月13日
	 */
	@RequestMapping(value = "tunnels/energies/datagrid", method = RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody EnergyVo vo) {
		PageInfo<EnergySimpleDto> pageInfo = energyService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}

	
	
	/**
	 * @Description: 根据管廊id获取指定日期内的能耗数据,不分页
	 * @param @param vo
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月14日
	 */
	@RequestMapping(value = "tunnels/energies/time/condition", method = RequestMethod.POST)
	public JSONObject getEnergiesByTunnelIdAndTimeCondition(@RequestBody EnergyVo vo) {
		List<EnergySimpleDto> list = energyService.getEnergiesByCondition(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	
	
	/**
	 * @Description: 获取某管廊一年的能耗
	 * @param @param tunnelId
	 * @param @param time
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value = "tunnels/{id}/energies/one-year/{time}", method = RequestMethod.GET)
	public JSONObject getListOfTunnelOneYear(@PathVariable("id") int tunnelId, @PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		Date startTime = DateUtil.getBeginDayOfYear(datetime);
		Date endTime = DateUtil.getEndDayOfYear(datetime);
		List<EnergySimpleDto> l = energyService.getEnergiesByTunnelIdAndTimeCondition(tunnelId, startTime, endTime);

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, l);
	}
	
	
	
	/**
	 * @Description: 获取管廊一个月的能耗
	 * @param @param tunnelId
	 * @param @param time
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value = "tunnels/{id}/energies/one-month/{time}", method = RequestMethod.GET)
	public JSONObject getListOfTunnelOneMonth(@PathVariable("id") Integer id, @PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		Date startTime = DateUtil.getBeginDayOfMonth(datetime);
		Date endTime = DateUtil.getEndDayOfMonth(datetime);
		List<EnergySimpleDto> l = energyService.getEnergiesByTunnelIdAndTimeCondition(id, startTime, endTime);

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, l);
	}
	
	
	/**
	 * @Description:  获取管廊一天的能耗
	 * @param @param tunnelId
	 * @param @param time
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value = "tunnels/{id}/energies/one-day/{time}", method = RequestMethod.GET)
	public JSONObject getListOfTunnelOneDay(@PathVariable("id") int tunnelId, @PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		Date startTime = DateUtil.getDayStartTime(datetime);
		Date endTime = DateUtil.getDayEndTime(datetime);
		List<EnergySimpleDto> l = energyService.getEnergiesByTunnelIdAndTimeCondition(tunnelId, startTime, endTime);

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, l);
	}
	
	/**
	 * 分页获取各管廊，各类电表的能耗(最近一周内)
	 * @param startTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuya
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value = "energies/one-week/datagrid", method = RequestMethod.POST)
	public JSONObject getListOfOneWeek(@RequestBody EnergyVo vo) {
		Date datetime = vo.getStartTime();
		List<JSONObject> ls = new ArrayList<>();
		for(int i=0;i<7;i++) {
			List<TunnelSimpleDto> dtos = tunnelService.getList();
			
			for(TunnelSimpleDto dto : dtos) {
				
				
				for(ObjectType em : ObjectType.getArr()) {
					JSONObject j = new JSONObject();
					j.put("category", em.getName());
					j.put("tunnelName", dto.getName());
					j.put("date", datetime.getTime());
					
					Date startTime = DateUtil.getDayStartTime(datetime);
					Date endTime = DateUtil.getDayEndTime(datetime);
					
					Double total = getTotalByMap(dto.getId(),startTime,endTime,em);
					
					
					j.put("val", total);
					ls.add(j);
				}
				
			}
			datetime.setDate(datetime.getDate() - 1);
		}
		ListPageUtil<JSONObject> listPageUtil = new ListPageUtil<>(ls,vo.getPageNum(), vo.getPageSize());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listPageUtil);
	}

	/**
	 * 获取各管廊，各类电表的能耗(最近一周内)
	 * @param time
	 * @return
	 * @author liuya
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value = "energies/one-week/{time}", method = RequestMethod.GET)
	public JSONObject getListOfOneWeeks(@PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		List<JSONObject> ls = new ArrayList<>();
		
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		
		for(TunnelSimpleDto dto : dtos) {
			
			for(ObjectType em : ObjectType.getArr()) {
				JSONObject j = new JSONObject();
				j.put("category", em.getName());
				j.put("tunnelName", dto.getName());
				
				List<JSONObject> objList = new ArrayList<>();
				Date newTime = DateUtil.setMillisTimestamp2Date(datetime.getTime());
				for(int i=0;i<7;i++) {
					JSONObject obj = new JSONObject();
					obj.put("date", newTime.getTime());
					
					Date startTime = DateUtil.getDayStartTime(newTime);
					Date endTime = DateUtil.getDayEndTime(newTime);
					
					Double total = getTotalByMap(dto.getId(),startTime,endTime,em);
					obj.put("val", total);
					objList.add(obj);
					newTime.setDate(newTime.getDate() - 1);
				}
				j.put("data", objList);
				ls.add(j);
			}
			
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
	}
	
	/*   测试   */
//	@RequestMapping(value = "en/{time}", method = RequestMethod.GET)
//	public JSONObject getLi(@PathVariable("time") long time) {
//		Date datetime = DateUtil.setTimestamp2Date(time);
//		List<JSONObject> ls = new ArrayList<>();
//		
//			for(ObjectType em : ObjectType.getArr()) {
//				JSONObject j = new JSONObject();
//				j.put("category", em.getName());
//				
//				j.put("date", datetime.getTime());
//				
//				Date startTime = DateUtil.getDayStartTime(datetime);
//				Date endTime = DateUtil.getDayEndTime(datetime);
//				
//				Double total = getTotalByMap(1,startTime,endTime,em);
//				j.put("val", total);
//				ls.add(j);
//			}
//		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
//	}
	
	/**
	 * 获取指定条件下的能耗
	 * @param tunnelId 管廊id
	 * @param newTime  某一天
	 * @param em  电表类型
	 * @return
	 * @author liuya
	 * @Date 2018年9月20日
	 */
	private Double getTotalByMap(Integer tunnelId, Date startTime, Date endTime, ObjectType em) {
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("startTime", startTime.getTime());
		map.put("endTime", endTime.getTime());
		map.put("id", tunnelId);
		
		List<Map<String, Object>> list = getObjSub(map);
		
		List<Map<String, Object>> li = new ArrayList<>();
		
		for (Map<String, Object> m : list) {
			LogUtil.info(m.get("objectId"));
			MeasObj measObj = measObjModuleCenter.getMeasObj(DataTypeUtil.toInt(m.get("objectId")));
			if(em.equals(ObjectType.getEnum(measObj.getObjtypeId().intValue()))) { 
				li.add(m); 
			}
		}
		Double total = new Double(0.00);
		for (Map<String, Object> m2 : li) {
			total = MathUtil.add(DataTypeUtil.toDouble(m2.get("sub")), total);
		}
		return total;
	}
	
	/**
	 * 分页获取各管廊，各类电表的能耗(最近一个月内)
	 * @param startTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuya
	 * @Date 2018年9月20日
	 */
	@RequestMapping(value = "energies/one-month/datagrid", method = RequestMethod.POST)
	public JSONObject getListOfOneMonth(@RequestBody EnergyVo vo) {
		Date datetime = vo.getStartTime();
		List<JSONObject> ls = new ArrayList<>();
		for(int i=0;i<4;i++) {
			List<TunnelSimpleDto> dtos = tunnelService.getList();
			
			Date endTime = DateUtil.getDayEndTime(datetime);
			datetime.setDate(datetime.getDate() - 6);
			Date startTime = DateUtil.getDayStartTime(datetime);
			
			for(TunnelSimpleDto dto : dtos) {
				
				
				for(ObjectType em : ObjectType.getArr()) {
					JSONObject j = new JSONObject();
					j.put("category", em.getName());
					j.put("tunnelName", dto.getName());
					j.put("start", startTime.getTime());
					j.put("end", endTime.getTime());
					
					Double total = getTotalByMap(dto.getId(),startTime,endTime,em);
					
					j.put("val", total);
					ls.add(j);
				}
				
			}
			datetime.setDate(datetime.getDate() - 1);
		}
		ListPageUtil<JSONObject> listPageUtil = new ListPageUtil<>(ls,vo.getPageNum(), vo.getPageSize());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listPageUtil);
	}
	
	
	/**
	 * 获取各管廊，各类电表的能耗(最近一个月内)
	 * @param time
	 * @return
	 * @author liuya
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value = "energies/one-month/{time}", method = RequestMethod.GET)
	public JSONObject getListOfOneMonths(@PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		List<JSONObject> ls = new ArrayList<>();
		
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		
		for(TunnelSimpleDto dto : dtos) {
			
			
			for(ObjectType em : ObjectType.getArr()) {
				JSONObject j = new JSONObject();
				j.put("category", em.getName());
				j.put("tunnelName", dto.getName());
				
				List<JSONObject> objList = new ArrayList<>();
				Date newTime = DateUtil.setMillisTimestamp2Date(datetime.getTime());
				for(int i=0;i<4;i++) {
					Date endTime = DateUtil.getDayEndTime(newTime);
					newTime.setDate(newTime.getDate() - 6);
					Date startTime = DateUtil.getDayStartTime(newTime);
					
					JSONObject obj = new JSONObject();
					obj.put("start", startTime.getTime());
					obj.put("end", endTime.getTime());
					
					Double total = getTotalByMap(dto.getId(),startTime,endTime,em);
					obj.put("val", total);
					objList.add(obj);
					newTime.setDate(newTime.getDate() - 1);
				}
				j.put("data", objList);
				ls.add(j);
			}
			
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
	}
	
	
	/**
	 * 分页获取各管廊，各类电表的能耗(最近一年内)
	 * @param startTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuya
	 * @Date 2018年9月20日
	 */
	@RequestMapping(value = "energies/one-year/datagrid", method = RequestMethod.POST)
	public JSONObject getListOfOneYear(@RequestBody EnergyVo vo) {
		Date datetime = vo.getStartTime();
		List<JSONObject> ls = new ArrayList<>();
		for(int i=0;i<12;i++) {
			List<TunnelSimpleDto> dtos = tunnelService.getList();
			
			Date endTime = DateUtil.getEndDayOfMonth(datetime);
			Date startTime = DateUtil.getBeginDayOfMonth(datetime);
			
			for(TunnelSimpleDto dto : dtos) {
				
				
				for(ObjectType em : ObjectType.getArr()) {
					JSONObject j = new JSONObject();
					j.put("category", em.getName());
					j.put("tunnelName", dto.getName());
					j.put("start", startTime.getTime());
					j.put("end", endTime.getTime());
					
					Double total = getTotalByMap(dto.getId(),startTime,endTime,em);
					
					j.put("val", total);
					ls.add(j);
				}
				
			}
			datetime.setMonth(datetime.getMonth() - 1);
		}
		ListPageUtil<JSONObject> listPageUtil = new ListPageUtil<>(ls,vo.getPageNum(), vo.getPageSize());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listPageUtil);
	}
	
	
	/**
	 * 获取各管廊，各类电表的能耗(最近一年内)
	 * @param time
	 * @return
	 * @author liuya
	 * @Date 2018年9月20日
	 */
	@RequestMapping(value = "energies/one-year/{time}", method = RequestMethod.GET)
	public JSONObject getListOfOneYears(@PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		List<JSONObject> ls = new ArrayList<>();
		
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		
		for(TunnelSimpleDto dto : dtos) {
			
			
			for(ObjectType em : ObjectType.getArr()) {
				JSONObject j = new JSONObject();
				j.put("category", em.getName());
				j.put("tunnelName", dto.getName());
				
				List<JSONObject> objList = new ArrayList<>();
				Date newTime = DateUtil.setMillisTimestamp2Date(datetime.getTime());
				for(int i=0;i<12;i++) {
					Date endTime = DateUtil.getEndDayOfMonth(newTime);
					Date startTime = DateUtil.getBeginDayOfMonth(newTime);
					
					JSONObject obj = new JSONObject();
					obj.put("start", startTime.getTime());
					obj.put("end", endTime.getTime());
					
					Double total = getTotalByMap(dto.getId(),startTime,endTime,em);
					obj.put("val", total);
					objList.add(obj);
					newTime.setMonth(newTime.getMonth() - 1);
				}
				j.put("data", objList);
				ls.add(j);
			}
			
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
	}
	
	
	/**
	 * 获取各管廊，各类电表的能耗(最近一周内)
	 * @param time
	 * @return
	 * @author liuya
	 * @Date 2018年9月21日
	 */
	@RequestMapping(value = "energies/one-week01/{time}", method = RequestMethod.GET)
	public JSONObject getListOfOneWeeks01(@PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		List<JSONObject> ls = new ArrayList<>();
		
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		
		for(ObjectType em : ObjectType.getArr()) {
			
			JSONObject j = new JSONObject();
			j.put("category", em.getName());
			
			List<JSONObject> objList = new ArrayList<>();
			Date newTime = DateUtil.setMillisTimestamp2Date(datetime.getTime());
			for(int i=0;i<7;i++) {
				
				JSONObject obj = new JSONObject();
				obj.put("date", newTime.getTime());
				
				Date startTime = DateUtil.getDayStartTime(newTime);
				Date endTime = DateUtil.getDayEndTime(newTime);
				Double total = 0.0;
				for(TunnelSimpleDto dto : dtos) {
					total += getTotalByMap(dto.getId(),startTime,endTime,em);
				}
				obj.put("val", total);
				objList.add(obj);
				newTime.setDate(newTime.getDate() - 1);
			}
			j.put("data", objList);
			ls.add(j);
			
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
	}
	
	
	/**
	 * 获取各管廊，各类电表的能耗(最近一个月内)
	 * @param time
	 * @return
	 * @author liuya
	 * @Date 2018年9月21日
	 */
	@RequestMapping(value = "energies/one-month01/{time}", method = RequestMethod.GET)
	public JSONObject getListOfOneMonths01(@PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		List<JSONObject> ls = new ArrayList<>();
		
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		
		for(ObjectType em : ObjectType.getArr()) {
			
			
			JSONObject j = new JSONObject();
			j.put("category", em.getName());
			
			List<JSONObject> objList = new ArrayList<>();
			Date newTime = DateUtil.setMillisTimestamp2Date(datetime.getTime());
			for(int i=0;i<4;i++) {
				Date endTime = DateUtil.getDayEndTime(newTime);
				newTime.setDate(newTime.getDate() - 6);
				Date startTime = DateUtil.getDayStartTime(newTime);
				
				JSONObject obj = new JSONObject();
				obj.put("start", startTime.getTime());
				obj.put("end", endTime.getTime());
				
				Double total = 0.0;
				for(TunnelSimpleDto dto : dtos) {
					total += getTotalByMap(dto.getId(),startTime,endTime,em);
				}
				obj.put("val", total);
				objList.add(obj);
				newTime.setDate(newTime.getDate() - 1);
			}
			
			j.put("data", objList);
			ls.add(j);
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
	}
	
	
	/**
	 * 获取各管廊，各类电表的能耗(最近一年内)
	 * @param time
	 * @return
	 * @author liuya
	 * @Date 2018年9月21日
	 */
	@RequestMapping(value = "energies/one-year01/{time}", method = RequestMethod.GET)
	public JSONObject getListOfOneYears01(@PathVariable("time") long time) {
		Date datetime = DateUtil.setTimestamp2Date(time);
		List<JSONObject> ls = new ArrayList<>();
		
		List<TunnelSimpleDto> dtos = tunnelService.getList();
		for(ObjectType em : ObjectType.getArr()) {
			JSONObject j = new JSONObject();
			j.put("category", em.getName());
			
			List<JSONObject> objList = new ArrayList<>();
			Date newTime = DateUtil.setMillisTimestamp2Date(datetime.getTime());
			for(int i=0;i<12;i++) {
				Date endTime = DateUtil.getEndDayOfMonth(newTime);
				Date startTime = DateUtil.getBeginDayOfMonth(newTime);
				
				JSONObject obj = new JSONObject();
				obj.put("start", startTime.getTime());
				obj.put("end", endTime.getTime());
				
				Double total = 0.0;
				for(TunnelSimpleDto dto : dtos) {
		
					total += getTotalByMap(dto.getId(),startTime,endTime,em);
				}
				obj.put("val", total);
				objList.add(obj);
				newTime.setMonth(newTime.getMonth() - 1);
			}
			j.put("data", objList);
			ls.add(j);
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
	}
	
	/**
	 * 
	 * @param tunnelId
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月15日
	 */
	@RequestMapping(value = "tunnels/{tunnelId}/energies/one-year", method = RequestMethod.GET)
	public JSONObject getListOfTunnelOneYear(@PathVariable("tunnelId") Integer tunnelId) {
		//List<TunnelSimpleDto> list = tunnelService.getList();
		String [] str = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
		List<JSONObject> list = new ArrayList<>();
		for(int i=0;i<str.length;i++) {
			JSONObject obj = new JSONObject();
			obj.put("key", str[i]);
			obj.put("val", i + tunnelId + 300);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
}
			