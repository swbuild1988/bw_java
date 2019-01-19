package com.bandweaver.tunnel.controller.oam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.constant.oam.EnergyType;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeDataService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;

/**
 * 能耗缓存
 * @author ya.liu
 * @Date 2018年11月15日
 */
@Controller
@ResponseBody
public class ConsumeDataController {

	@Autowired
	private ConsumeDataService consumeDataService;
	@Autowired
	private ConsumeService consumeService;
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 添加能耗数据
	 * @param consumeData
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月15日
	 */
	@RequestMapping(value = "consume-datas", method = RequestMethod.POST)
	public JSONObject insert(@RequestBody ConsumeData consumeData) {
		consumeDataService.insert(consumeData);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 批量添加
	 * @param list
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月15日
	 */
	@RequestMapping(value = "consume-datas/batch", method = RequestMethod.POST)
	public JSONObject addBatch(@RequestBody List<ConsumeData> list) {
		consumeDataService.addBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * vo获取某一电表并统计该电表在某一时间段内的总能耗
	 * @param vo
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月15日
	 */
	private Double getSum(ConsumeDataVo vo) {
		if(vo.getStoreId() == null) vo.setStoreId(0);
		if(vo.getAreaId() == null) vo.setAreaId(0);
		if(vo.getObjectType() == null) vo.setObjectType(0);
		if(vo.getEnergyType() == null) vo.setEnergyType(0);
		List<ConsumeDto> consumeDto = consumeService.getConsumesByCondition(vo);
		if(consumeDto.size() == 0) return null;
		ConsumeDataVo dataVo = new ConsumeDataVo();
		dataVo.setId(consumeDto.get(0).getId());
		dataVo.setEndTime(vo.getEndTime());
		dataVo.setStartTime(vo.getStartTime());
		List<ConsumeData> list = consumeDataService.getListByIdAndTime(dataVo);
		Double sum = 0.0;
		for(ConsumeData data : list) {
			
			Double num = data.getDirect();
			Double count = data.getIndirect();
			Double cv = num == null ? (count == null ? 0 : count) : num;
			sum = MathUtil.add(sum, cv);
		}
		return sum;
	}
	
	/**
	 * 某一电表在某段时间内的总能耗
	 * @param tunnelId 管廊id
	 * @param storeId 管舱id
	 * @param areaId 区段id
	 * @param objectType 对象类型
	 * @param energyType 能耗类型
	 * @param startTime 开始时间	可选
	 * @param endTime 结束时间	可选
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "consume-datas/condition", method = RequestMethod.POST)
	public JSONObject getConsumeDatasByCondition(@RequestBody ConsumeDataVo vo) {
		Double sum = getSum(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, sum);
	}
	
	/**
     * 获取不同条件下每个管廊内的总能耗
     * @param objectType 对象类型	可选
	 * @param energyType 能耗类型枚举
	 * @param startTime 开始时间	可选
	 * @param endTime 结束时间	可选
     * @return
     * @author ya.liu
     * @Date 2018年11月16日
     */
    @RequestMapping(value = "tunnels/consume-datas", method = RequestMethod.POST)
    public JSONObject getConsumeDatasByTunnleIds(@RequestBody ConsumeDataVo vo) {
    	List<TunnelSimpleDto> TunnelList = tunnelService.getList();
    	List<JSONObject> list = new ArrayList<>();
    	for(TunnelSimpleDto dto : TunnelList) {
    		vo.setTunnelId(dto.getId());
    		Double sum = getSum(vo);
    		JSONObject obj = new JSONObject();
    		obj.put("key", dto);
    		obj.put("val", sum);
    		list.add(obj);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
    
    /**
     * 不同条件下每个管舱的总能耗
     * @param tunnelId 管廊id
     * @param areaId 区域id 可选
     * @param objectType 对象类型	可选
     * @param energyType 能耗类型枚举
	 * @param startTime 开始时间	可选
	 * @param endTime 结束时间	可选
     * @return
     * @author ya.liu
     * @Date 2018年11月16日
     */
    @RequestMapping(value = "stores/consume-datas", method = RequestMethod.POST)
    public JSONObject getConsumeDatasByStoreIds(@RequestBody ConsumeDataVo vo){
    	List<StoreDto> dtoList = storeService.getStoresByTunnelId(vo.getTunnelId());
    	List<JSONObject> list = new ArrayList<>();
    	for(StoreDto dto : dtoList) {
    		vo.setStoreId(dto.getId());
    		Double sum = getSum(vo);
    		JSONObject obj = new JSONObject();
    		obj.put("name", dto.getName());
    		obj.put("val", sum);
    		obj.put("id", dto.getId());
    		list.add(obj);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
    
    /**
    * 不同条件下每个区域内的总能耗
     * @param tunnelId 管廊id
     * @param storeId 管舱id 可选
     * @param objectType 对象类型	可选
     * @param energyType 能耗类型枚举
	 * @param startTime 开始时间	可选
	 * @param endTime 结束时间	可选
     * @return
     * @author ya.liu
     * @Date 2018年11月19日
     */
    @RequestMapping(value = "areas/consume-datas", method = RequestMethod.POST)
    public JSONObject getConsumeDatasByAreaIds(@RequestBody ConsumeDataVo vo) {
    	List<AreaDto> dtoList = areaService.getAreasByTunnelId(vo.getTunnelId());
    	List<JSONObject> list = new ArrayList<>();
    	for(AreaDto dto : dtoList) {
    		vo.setAreaId(dto.getId());
    		Double sum = getSum(vo);
    		JSONObject obj = new JSONObject();
    		obj.put("name", dto.getName());
    		obj.put("val", sum);
    		obj.put("id", dto.getId());
    		list.add(obj);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 不同条件下每个对象类型下的总能耗
     * @param tunnelId 管廊id
     * @param storeId 管舱id 可选
     * @param areaId 区域id 可选
     * @param energyType 能耗类型枚举
	 * @param startTime 开始时间	可选
	 * @param endTime 结束时间	可选
     * @return
     * @author ya.liu
     * @Date 2018年11月19日
     */
    @RequestMapping(value = "objectType/consume-datas", method = RequestMethod.POST)
    public JSONObject getConsumeDatasByObjectTypes(@RequestBody ConsumeDataVo vo) {
    	List<ObjectType> objList = new ArrayList<>();
    	if(Integer.valueOf(EnergyType.COMMON.getValue()).equals(vo.getEnergyType())) {
    		objList = Arrays.asList(ObjectType.getCommon());
    	}else if(Integer.valueOf(EnergyType.EMERGENCY.getValue()).equals(vo.getEnergyType())) {
    		objList = Arrays.asList(ObjectType.getEmergency());
    	}else {
    		objList.add(ObjectType.NONE);
    	}
    	List<JSONObject> list = new ArrayList<>();
    	for(ObjectType obj : objList) {
    		vo.setObjectType(obj.getValue());
    		Double sum = getSum(vo);
    		JSONObject json = new JSONObject();
    		json.put("name", obj.getName());
    		json.put("val", sum);
    		json.put("id", obj.getValue());
    		list.add(json);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    
	/**
	 * 删除某个时间以前的能耗值
	 * @param time 时间
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "consume-datas", method = RequestMethod.DELETE)
	public JSONObject delete(@RequestBody ConsumeData consumeData) {
		consumeDataService.deleteBatch(consumeData.getTime());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 获取每条管廊，每个月的总能耗
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月10日
	 */
	@RequestMapping(value = "energy/totle-everymonth", method = RequestMethod.GET)
    public JSONObject getEnergyByTunnleIds() {
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
    	List<JSONObject> list = new ArrayList<>();
    	
		// 遍历，获取每个管廊的能耗
    	for(TunnelSimpleDto dto : tunnelList) {
    		JSONObject json = new JSONObject();
    		json.put("key", dto.getName());
    		List<JSONObject> energyList = new ArrayList<>();
    		Date now = new Date();
    		now.setMonth(now.getMonth() - 11);
        	// 往后循环12个月
    		for(int i=0;i<12;i++) {
    			JSONObject obj = new JSONObject();
    			obj.put("key", now.getMonth() + 1 + "月");
    		
				ConsumeDataVo vo = new ConsumeDataVo();
				Date nowStart = DateUtil.getBeginDayOfMonth(now);
				Date nowEnd = DateUtil.getEndDayOfMonth(now);
				vo.setStartTime(nowStart);
				vo.setEndTime(nowEnd);
				vo.setTunnelId(dto.getId());
				// 一个月的总能耗
				Double sum = getSum(vo);
				obj.put("val", sum);
				energyList.add(obj);
				now.setMonth(now.getMonth() + 1);
    		}
    		json.put("val", energyList);
    		list.add(json);
		}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
	
	/**
	 * 获取每个月【2018-9~2019-1】，每条管廊的平均能耗/每公里
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月10日
	 */
	@RequestMapping(value = "energy/average-everymonth", method = RequestMethod.GET)
    public JSONObject getAverageEnergyByTunnleIds() {
    	List<TunnelDto> tunnelList = tunnelService.getDtoList();
    	List<List<JSONObject>> list = new ArrayList<>();
    	
		// 获取2018年9月到2019年2月的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = null;
		Date ed = null;
		try {
			sd = sdf.parse("2018-9-1");
			ed = sdf.parse("2019-1-31");
		} catch (Exception e) {
			
		}
		while(sd.before(ed)) {
			List<JSONObject> energyList = new ArrayList<>();
			// 遍历，获取每个管廊的能耗
			for(TunnelDto dto : tunnelList) {
				ConsumeDataVo vo = new ConsumeDataVo();
				Date nowStart = DateUtil.getBeginDayOfMonth(sd);
				Date nowEnd = DateUtil.getEndDayOfMonth(sd);
				vo.setStartTime(nowStart);
				vo.setEndTime(nowEnd);
				vo.setTunnelId(dto.getId());
				// 一个月的总能耗
				Double sum = getSum(vo);
				//平均每公里的能耗
				sum = sum / dto.getLength() * 1000;
				JSONObject obj = new JSONObject();
				obj.put("time", nowStart);
				obj.put("tunnel", dto.getName());
				obj.put("energy", sum);
				energyList.add(obj);
			}
			list.add(energyList);
			sd.setMonth(sd.getMonth() + 1);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
}
			