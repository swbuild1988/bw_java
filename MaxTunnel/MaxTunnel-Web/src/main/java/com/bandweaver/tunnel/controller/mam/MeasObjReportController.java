package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.StatisticalTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.vo.mam.report.MeasObjReportVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**监测对象数据统计
 * @author shaosen
 * @date 2018年9月15日
 */
@Controller
@ResponseBody
public class MeasObjReportController {

	@Autowired
	private MeasObjReportService measObjReportService;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;
	
	/**监测对象数据统计分页查询 
	 * @param objectId 监测对象id
	 * @param objectType 监测对象类型 枚举
	 * @param tunnelId	管廊id
	 * @param timeType 时间类型 枚举
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum 
	 * @param pageSize 
	 * @return  {"msg":"请求成功","code":"200","data":{"total":92,"list":[{"id":1,"objectId":1024,"objectTypeName":"甲烷","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":9,"objectId":1041,"objectTypeName":"湿度","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":8,"objectId":1040,"objectTypeName":"湿度","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":4,"objectId":1031,"objectTypeName":"硫化氢","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":3,"objectId":1029,"objectTypeName":"甲烷","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":19,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"lastPage":8,"firstPage":1}} 
	 * @author shaosen
	 * @Date 2018年9月15日
	 */
	@RequestMapping(value="measobjs-report/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody MeasObjReportVo vo) {
		PageInfo<MeasObjReportDto> pageInfo = measObjReportService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,pageInfo);
	}
	
	
	/**最近一天各个管廊的温度，湿度，硫化氢，氧气，电表等检测仪随着时间变化数值变化曲线图 
	 * @param tunnelId 管廊id
	 * @param typeValue objectType枚举  
	 * @param statisticalType StatisticalTypeEnum数据统计类型枚举
	 * @return  {"msg":"请求成功","code":"200","data":[{"val":[{"val":0.0,"key":"0时"},{"val":0.0,"key":"1时"},{"val":0.0,"key":"2时"},{"val":0.0,"key":"3时"},{"val":0.0,"key":"4时"},{"val":0.0,"key":"5时"},{"val":0.0,"key":"6时"},{"val":0.0,"key":"7时"},{"val":0.0,"key":"8时"},{"val":0.0,"key":"9时"},{"val":0.0,"key":"10时"},{"val":0.0,"key":"11时"},{"val":0.0,"key":"12时"}],"key":"监测对象1013"},{"val":[{"val":0.0,"key":"0时"},{"val":0.0,"key":"1时"},{"val":0.0,"key":"2时"},{"val":0.0,"key":"3时"},{"val":0.0,"key":"4时"},{"val":0.0,"key":"5时"},{"val":0.0,"key":"6时"},{"val":0.0,"key":"7时"},{"val":0.0,"key":"8时"},{"val":0.0,"key":"9时"},{"val":0.0,"key":"10时"},{"val":0.0,"key":"11时"},{"val":0.0,"key":"12时"}],"key":"温度监测仪1"},{"val":[{"val":0.0,"key":"0时"},{"val":0.0,"key":"1时"},{"val":0.0,"key":"2时"},{"val":0.0,"key":"3时"},{"val":0.0,"key":"4时"},{"val":0.0,"key":"5时"},{"val":0.0,"key":"6时"},{"val":0.0,"key":"7时"},{"val":0.0,"key":"8时"},{"val":0.0,"key":"9时"},{"val":0.0,"key":"10时"},{"val":0.0,"key":"11时"},{"val":0.0,"key":"12时"}],"key":"温度监测仪2"}]}
	 * @author shaosen
	 * @Date 2018年11月10日
	 */
	@RequestMapping(value= "tunnels/{id}/measobjs-report/one-day/object-type/{objectType}/statistical-type/{statisticalType}/diagram",method = RequestMethod.GET)
	public JSONObject getDayDiagram(
			@PathVariable("id") Integer tunnelId,
			@PathVariable("objectType")Integer objectType,
			@PathVariable("statisticalType") Integer statisticalType) {
		
		List<JSONObject> result = new ArrayList<>();
		
		//获取今天的开始时间
		Date dayBegin = DateUtil.getDayBegin();
		Set<Integer> objectSet = getObjectSet(tunnelId, objectType, dayBegin, TimeEnum.HOUR.getValue());
		
		for (Integer objectId : objectSet) {
			JSONObject j1 = new JSONObject();
			j1.put("key", measObjModuleCenter.getMeasObj(objectId).getName());
			
			List<JSONObject> jList = new ArrayList<>();
			
			//获取startTime & endTime
			Calendar c = Calendar.getInstance();
			int hours = c.get(Calendar.HOUR_OF_DAY);
			for (int i = hours; i > 0; i--) {
				Date hour = DateUtil.getBeforHour(new Date(), i);
				Date startTime = DateUtil.getHourBeginTime(hour);
				Date endTime = DateUtil.getHourEndTime(hour);

				Calendar cl = Calendar.getInstance();
				cl.setTime(startTime);
				int currentTime = cl.get(Calendar.HOUR_OF_DAY);
				
				JSONObject j2 = new JSONObject();
				j2.put("key", currentTime + "时");
				
				List<MeasObjReportDto> li = measObjReportService.getListByObjectIdAndTimeAndTimeType(objectId, startTime, endTime, TimeEnum.HOUR.getValue());
				
				StatisticalTypeEnum statisticalTypeEnum = StatisticalTypeEnum.getEnum(statisticalType);
				if(li != null && li.size() > 0 ) {
					if(StatisticalTypeEnum.MIN_VALUE == statisticalTypeEnum ) {
						j2.put("val", li.get(0).getMinValue());
					}else if(StatisticalTypeEnum.MAX_VALUE == statisticalTypeEnum ){
						j2.put("val", li.get(0).getMaxValue());
					}else if(StatisticalTypeEnum.AVG_VALUE == statisticalTypeEnum){
						j2.put("val", li.get(0).getAvgValue());
					}
				}else {
					j2.put("val", 0);
				}
				jList.add(j2);
			
			}
			
			j1.put("val", jList);
			result.add(j1);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
	}
	
	
	
	
	/**
	 * 最近一周各个管廊的温度，湿度，硫化氢，氧气，电表等检测仪随着时间变化数值变化曲线图
	 */
	
	@RequestMapping(value= "tunnels/{id}/measobjs-report/one-week/object-type/{objectType}/statistical-type/{statisticalType}/diagram",method = RequestMethod.GET)
	public JSONObject getWeekDiagram(
			@PathVariable("id") Integer tunnelId,
			@PathVariable("objectType")Integer objectType,
			@PathVariable("statisticalType") Integer statisticalType ) {
		
		List<JSONObject> result = new ArrayList<>();
		
		//获取本周开始时间
		Date weekBegin = DateUtil.getBeginDayOfWeek();
		LogUtil.info("weekBegin:" + weekBegin);
		Set<Integer> objectSet = getObjectSet(tunnelId, objectType, weekBegin,TimeEnum.DAY.getValue());
		
		for (Integer objectId : objectSet) {
			JSONObject j1 = new JSONObject();
			j1.put("key", measObjModuleCenter.getMeasObj(objectId).getName());
			
			List<JSONObject> jList = new ArrayList<>();
			
			//get startTime & endTime
			Calendar c = Calendar.getInstance();
			int week = c.get(Calendar.DAY_OF_WEEK) - 1;//每周从周一开始
			LogUtil.info("week:" + week );
			for (int i = week -1; i > 0; i--) {
				Date frontDay = DateUtil.getFrontDay(DateUtil.getCurrentDate(), i);
				Date startTime = DateUtil.getDayStartTime(frontDay);
				Date endTime = DateUtil.getDayEndTime(frontDay);
				
				JSONObject j2 = new JSONObject();
				j2.put("key", DateUtil.getDate2YYYYMMdd(frontDay));
				
				List<MeasObjReportDto> li = measObjReportService.getListByObjectIdAndTimeAndTimeType(objectId, startTime, endTime, TimeEnum.DAY.getValue());
				StatisticalTypeEnum statisticalTypeEnum = StatisticalTypeEnum.getEnum(statisticalType);
				if(li != null && li.size() > 0 ) {
					
					if(StatisticalTypeEnum.MIN_VALUE == statisticalTypeEnum ) {
						j2.put("val", li.get(0).getMinValue());
					}else if(StatisticalTypeEnum.MAX_VALUE == statisticalTypeEnum ){
						j2.put("val", li.get(0).getMaxValue());
					}else if(StatisticalTypeEnum.AVG_VALUE == statisticalTypeEnum){
						j2.put("val", li.get(0).getAvgValue());
					}
				}else {
					j2.put("val", 0);
				}
				
				jList.add(j2);
			}
			j1.put("val", jList);
			result.add(j1);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}
	
	
	
	/**
	 * 最近一月各个管廊的温度，湿度，硫化氢，氧气，电表等检测仪随着时间变化数值变化曲线图
	 * {"msg":"请求成功","code":"200","data":[{"val":[{"val":0,"key":"20181101"},{"val":0,"key":"20181102"},{"val":0,"key":"20181103"},{"val":0,"key":"20181104"},{"val":0,"key":"20181105"},{"val":0,"key":"20181106"},{"val":0,"key":"20181107"},{"val":0.0,"key":"20181108"},{"val":0,"key":"20181109"},{"val":0,"key":"20181110"},{"val":0,"key":"20181111"}],"key":"监测对象1013"},{"val":[{"val":0,"key":"20181101"},{"val":0,"key":"20181102"},{"val":0,"key":"20181103"},{"val":0,"key":"20181104"},{"val":0,"key":"20181105"},{"val":0,"key":"20181106"},{"val":0,"key":"20181107"},{"val":0.0,"key":"20181108"},{"val":0,"key":"20181109"},{"val":0,"key":"20181110"},{"val":0,"key":"20181111"}],"key":"温度监测仪1"},{"val":[{"val":0,"key":"20181101"},{"val":0,"key":"20181102"},{"val":0,"key":"20181103"},{"val":0,"key":"20181104"},{"val":0,"key":"20181105"},{"val":0,"key":"20181106"},{"val":0,"key":"20181107"},{"val":0.0,"key":"20181108"},{"val":0,"key":"20181109"},{"val":0,"key":"20181110"},{"val":0,"key":"20181111"}],"key":"温度监测仪2"}]}
	 */
	
	@RequestMapping(value= "tunnels/{id}/measobjs-report/one-month/object-type/{objectType}/statistical-type/{statisticalType}/diagram",method = RequestMethod.GET)
	public JSONObject getMonthDiagram(@PathVariable("id") Integer tunnelId,
			@PathVariable("objectType")Integer objectType,
			@PathVariable("statisticalType")Integer statisticalType) {
		
		List<JSONObject> result = new ArrayList<>();
		
		//获取本周开始时间
		Date monthBegin = DateUtil.getBeginDayOfMonth();
		Set<Integer> objectSet = getObjectSet(tunnelId, objectType, monthBegin,TimeEnum.DAY.getValue());
		
		
		for (Integer objectId : objectSet) {
			
			JSONObject j1 = new JSONObject();
			j1.put("key", measObjModuleCenter.getMeasObj(objectId).getName());
			
			List<JSONObject> jList = new ArrayList<>();
			
			//get startTime & endTime
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.DAY_OF_MONTH);
			for (int i = month -1; i > 0; i--) {
				Date frontDay = DateUtil.getFrontDay(DateUtil.getCurrentDate(), i);
				Date startTime = DateUtil.getDayStartTime(frontDay);
				Date endTime = DateUtil.getDayEndTime(frontDay);

				JSONObject j2 = new JSONObject();
				j2.put("key", DateUtil.getDate2YYYYMMdd(frontDay));
				
				List<MeasObjReportDto> li = measObjReportService.getListByObjectIdAndTimeAndTimeType(objectId, startTime, endTime, TimeEnum.DAY.getValue());
				StatisticalTypeEnum statisticalTypeEnum = StatisticalTypeEnum.getEnum(statisticalType);
				if(li != null && li.size() > 0 ) {
					
					if(StatisticalTypeEnum.MIN_VALUE == statisticalTypeEnum ) {
						j2.put("val", li.get(0).getMinValue());
					}else if(StatisticalTypeEnum.MAX_VALUE == statisticalTypeEnum ){
						j2.put("val", li.get(0).getMaxValue());
					}else if(StatisticalTypeEnum.AVG_VALUE == statisticalTypeEnum){
						j2.put("val", li.get(0).getAvgValue());
					}
				}else {
					j2.put("val", 0);
				}
				jList.add(j2);
			}
			j1.put("val", jList);
			result.add(j1);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}
	


	public Set<Integer> getObjectSet(Integer tunnelId, Integer objectType, Date startTime,Integer timeType) {
		//query params
		MeasObjReportVo vo = new MeasObjReportVo();
		vo.setTunnelId(tunnelId);
		vo.setObjectType(objectType);
		vo.setStartTime(startTime);
		vo.setTimeType(timeType);
		List<MeasObjReportDto> list = measObjReportService.getByCondition(vo);
		
		Set<Integer> object_id_set = new HashSet<>();
		for (MeasObjReportDto measObjReportDto : list) {
			object_id_set.add(measObjReportDto.getObjectId());
		}
		return object_id_set;
	}
	
	
	
}
