package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.transform.MeasAlarm;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**告警管理
 * @author shaosen
 * @date 2018年8月14日
 */
@Controller
@ResponseBody
public class AlarmController {
	@Autowired
	private AlarmService alarmService;
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;
	@Autowired
	private SectionService sectionService;
	
	
	/**
	 * 统计各个级别告警的数目
	 * @return {"msg":"请求成功","code":"200","data":[{"val":76,"key":"提示"},{"val":87,"key":"一般"},{"val":98,"key":"严重"},{"val":80,"key":"致命"}]}  
	 * @author shaosen
	 * @Date 2018年12月8日
	 */
	@RequestMapping(value="alarms/count",method=RequestMethod.GET)
	public JSONObject getAlarmCount() {
		List<JSONObject> result = new ArrayList<>();
		AlarmLevelEnum[] values = AlarmLevelEnum.values();
		for (AlarmLevelEnum alarmLevelEnum : values) {
			JSONObject js = new JSONObject();
			js.put("key", alarmLevelEnum.getName());
			int count = alarmService.getCountByLevel(alarmLevelEnum.getValue());
			js.put("val", count);
			result.add(js);
		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
	}
	
	
	/**接收告警并发送到MQ队列
	 * @param alarm
	 * @return  队列消息格式：{"alarmName":"严重级别的告警","alarmDate":1545278730010,"isDistribute":false,"plans":[{"name":"消防预案","id":4001},{"name":"通风预案","id":4003}],"tunnelId":1,"cleaned":false,"objectName":"监测对象2","alarmLevel":3,"id":10481,"objectId":1}
	 * @author shaosen
	 * @Date 2018年8月14日
	 */
	@RequestMapping(value = "alarms", method = RequestMethod.POST)
	public JSONObject add(@RequestBody MeasAlarm measAlarm) {
		
		LogUtil.debug("接收到MaxView发送的告警：" + measAlarm );
		MeasObj measObj = measObjModuleCenter.getMeasObj(measAlarm.getObjectId());
		if(StringTools.isNullOrEmpty(measObj)) {
			LogUtil.debug("监测对象[ " + measAlarm.getObjectId() + "]不存在");
			throw new BandWeaverException("监测对象[ " + measAlarm.getObjectId() + "]不存在");
			
		}
		
		Alarm alarm = new Alarm();
		alarm.setAlarmDate(DateUtil.setMillisTimestamp2Date(measAlarm.getTime()));
		alarm.setAlarmName(measAlarm.getAlarmName());
		alarm.setAlarmLevel(measAlarm.getAlarmSeverity());
		alarm.setTunnelId(measObj.getTunnelId());
		alarm.setObjectId(measAlarm.getObjectId());
		alarm.setObjectName(measObj.getName());
		alarm.setIsDistribute(DataType.getEnum(measObj.getDatatypeId()) == DataType.DISTRIBUTE ? true : false );
		alarm.setAlarmSource(measAlarm.getAlarmSource());
		alarm.setDescription(measAlarm.getAdditionalText());
		
		if(measAlarm.getLongitude() == null || measAlarm.getLongitude().trim().length() == 0) {
			alarm.setLongitude(measObj.getLongitude());
		}else {
			alarm.setLongitude(measAlarm.getLongitude());
		}
		
		if(measAlarm.getLatitude() == null || measAlarm.getLatitude().trim().length() == 0) {
			alarm.setLatitude(measObj.getLatitude());
		}else {
			alarm.setLatitude(measAlarm.getLatitude());
		}
		alarmService.add(alarm);
		return CommonUtil.success();
	}
	

	/**
	 * 批量清除告警 
	 * @param ids 告警id数组
	 * @param description 描述
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月8日
	 */
	@RequestMapping(value = "alarms/batch/clean",method = RequestMethod.POST)
	public JSONObject cleanAlarmBatch(@RequestBody AlarmVo vo) {
		alarmService.cleanAlarmBatch(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	
	/**清除告警 
	 * @param alarm
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月19日
	 */
	@RequestMapping(value = "alarms/clean",method = RequestMethod.POST)
	public JSONObject cleanAlarm(@RequestBody Alarm alarm) {
		alarmService.cleanAlarm(alarm);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**查询所有未清除的告警 
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月16日
	 */
	@RequestMapping(value = "alarms/non-cleaned/list",method = RequestMethod.GET)
	public JSONObject getAllNonCleanedAlarm() {
		List<AlarmDto> list = alarmService.getAllNonCleanedAlarm();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**查询所有未清除的告警数量
	 * @return   {"msg":"请求成功","code":"200","data":50}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "alarms/non-cleaned/count",method = RequestMethod.GET)
	public JSONObject getNonCleanedCount() {
		List<AlarmDto> list = alarmService.getAllNonCleanedAlarm();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list.size());
	}
	
	/**通过主键获取 
	 * @param id
	 * @return   {"msg":"请求成功","code":"200","data":{"id":2,"alarmDate":1535446119000,"alarmLevel":2,"tunnelId":4,"objectId":81,"description":"已阅","cleaned":true,"cleanedDate":1539742637000}}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "alarms/{id}",method = RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		Alarm alarm = alarmService.getById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, alarm);
	}
	
	/**告警分页查询 
	 * @param startTime 时间戳
	 * @param endTime 时间戳
	 * @param alarmLevel 告警等级，枚举，参考接口
	 * @param tunnelId 管廊id
	 * @param cleaned 告警是否清除 0：没清除 1：已清除
	 * @param objectIds 监测对象id数组
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @return  {"msg":"请求成功","code":"200","data":{"total":51,"list":[{"id":51,"alarmDate":1539679719000,"alarmLevelName":"致命","tunnel":{"id":3,"name":"经三路"},"objectId":71,"description":null,"cleaned":false,"cleanedDate":1539679719000},{"id":50,"alarmDate":1539593319000,"alarmLevelName":"致命","tunnel":{"id":2,"name":"实验路"},"objectId":80,"description":null,"cleaned":false,"cleanedDate":1539679719000},{"id":49,"alarmDate":1539506919000,"alarmLevelName":"一般","tunnel":{"id":4,"name":"经二路"},"objectId":94,"description":null,"cleaned":false,"cleanedDate":1539679719000},{"id":48,"alarmDate":1539420519000,"alarmLevelName":"致命","tunnel":{"id":2,"name":"实验路"},"objectId":44,"description":null,"cleaned":false,"cleanedDate":1539679719000},{"id":47,"alarmDate":1539334119000,"alarmLevelName":"一般","tunnel":{"id":3,"name":"经三路"},"objectId":44,"description":null,"cleaned":false,"cleanedDate":1539679719000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":11,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"lastPage":8,"firstPage":1}}
	 * @author shaosen
	 * @Date 2018年8月14日
	 */
	@RequestMapping(value="alarms/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody AlarmVo vo) {
		PageInfo<AlarmDto> pageInfo = alarmService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,pageInfo);
	}
	
	
	
	/**获取管廊内各类缺陷的数目 
	 * @return   {"msg":"请求成功","code":"200","data":[{"val":[{"val":6,"key":"提示"},{"val":0,"key":"一般"},{"val":0,"key":"严重"},{"val":0,"key":"致命"}],"key":"古城大街"},{"val":[{"val":0,"key":"提示"},{"val":0,"key":"一般"},{"val":0,"key":"严重"},{"val":0,"key":"致命"}],"key":"实验路"},{"val":[{"val":0,"key":"提示"},{"val":0,"key":"一般"},{"val":0,"key":"严重"},{"val":0,"key":"致命"}],"key":"经三路"},{"val":[{"val":0,"key":"提示"},{"val":0,"key":"一般"},{"val":0,"key":"严重"},{"val":0,"key":"致命"}],"key":"经二路"},{"val":[{"val":0,"key":"提示"},{"val":0,"key":"一般"},{"val":0,"key":"严重"},{"val":0,"key":"致命"}],"key":"纬三路"}]}
	 * @author shaosen
	 * @Date 2018年8月14日
	 */
	@RequestMapping(value = "tunnel/alarmcount", method = RequestMethod.GET)
	public JSONObject getCountOfDefect() {
		List<JSONObject> result = new ArrayList<>();
		
		List<TunnelSimpleDto> tList = tunnelService.getList();
		
		// 获取每个管廊的数据
		for (TunnelSimpleDto tunnel : tList) {
			JSONObject o = new JSONObject();
			o.put("key", tunnel.getName());
			// 获取管廊下所有报警类型的数目
			List<JSONObject> jList = new ArrayList<>();
			for (AlarmLevelEnum level : AlarmLevelEnum.values()) {
				JSONObject json = new JSONObject();
				json.put("key", level.getName());
				int count = alarmService.getCountByTunnelAndLevel(tunnel.getId(), level.getValue());
				json.put("val", count);
				jList.add(json);
			}
			o.put("val", jList);
			result.add(o);
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
	}

	
	/**获取未清除告警部分数据 
	 * @return   {"msg":"请求成功","code":"200","data":[{"id":1110,"alarmDate":1545979320000,"alarmName":"致命告警级别的告警","alarmLevelName":"致命告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1109,"alarmDate":1545979230000,"alarmName":"一般告警级别的告警","alarmLevelName":"一般告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1108,"alarmDate":1545979200000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1107,"alarmDate":1545979170000,"alarmName":"致命告警级别的告警","alarmLevelName":"致命告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1106,"alarmDate":1545979140000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1105,"alarmDate":1545979110000,"alarmName":"提示告警级别的告警","alarmLevelName":"提示告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1104,"alarmDate":1545979080000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1103,"alarmDate":1545979050000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1102,"alarmDate":1545979020000,"alarmName":"提示告警级别的告警","alarmLevelName":"提示告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1101,"alarmDate":1545978990000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1100,"alarmDate":1545978960000,"alarmName":"提示告警级别的告警","alarmLevelName":"提示告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1099,"alarmDate":1545978930000,"alarmName":"一般告警级别的告警","alarmLevelName":"一般告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1098,"alarmDate":1545978900000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1097,"alarmDate":1545978870000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1096,"alarmDate":1545978840000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1095,"alarmDate":1545978810000,"alarmName":"一般告警级别的告警","alarmLevelName":"一般告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1094,"alarmDate":1545978780000,"alarmName":"严重告警级别的告警","alarmLevelName":"严重告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1093,"alarmDate":1545978750000,"alarmName":"一般告警级别的告警","alarmLevelName":"一般告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1092,"alarmDate":1545978720000,"alarmName":"提示告警级别的告警","alarmLevelName":"提示告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null},{"id":1091,"alarmDate":1545978690000,"alarmName":"提示告警级别的告警","alarmLevelName":"提示告警","tunnel":{"id":1,"name":"古城大街"},"objectId":203012401,"objectName":"温度检测仪","isDistribute":false,"description":null,"cleaned":false,"cleanedDate":null,"alarmSource":null,"longitude":null,"latitude":null}]}
	 * @author shaosen
	 * @Date 2018年12月28日
	 */
	@RequestMapping(value="alarms/non-cleaned/part",method=RequestMethod.GET)
	public JSONObject getNoneCleanedAlarmTop20() {
		List<AlarmDto> list = alarmService.getAllNonCleanedAlarm();
		if(!list.isEmpty()) {
			list = list.stream().sorted(Comparator.comparing(AlarmDto::getAlarmDate).reversed()).collect(Collectors.toList());
		}
		
		List<AlarmDto> returnData = new ArrayList<>();
		if(list == null || list.isEmpty())
			return CommonUtil.success();
		if(list.size()>=20) {
			for (int i = 0; i < 20; i++) {
				returnData.add(list.get(i));
			}
		}else {
			returnData = list;
		}
		
		return CommonUtil.success(returnData);
	}
	
	
	/**获取各管廊告警次数
	 * @author shaosen
	 * @date 2019年1月10日
	 * @param   
	 * @return {"msg":"请求成功","code":"200","data":[{"value":12696,"key":"古城大街"},{"value":0,"key":"实验路"},{"value":0,"key":"经三路"},{"value":0,"key":"经二路"},{"value":0,"key":"纬三路"},{"value":0,"key":"监控中心"}]}  
	 */
	@RequestMapping(value="tunnels/alarm-count",method=RequestMethod.GET)
	public JSONObject getTunnelsAlarmCount() {
		List<JSONObject> rtData = new ArrayList<>();
		List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		for (TunnelSimpleDto t : tunnelList) {
			int count = alarmService.getCountByTunnel(t.getId());
			JSONObject json = new JSONObject();
			json.put("key", t.getName());
			json.put("val", count);
			rtData.add(json);
		}
		return CommonUtil.success(rtData);
	}
	
	
	/**获取每个月各级别告警次数
	 * @author shaosen
	 * @date 2019年1月10日
	 * @param   
	 * @return {"msg":"请求成功","code":"200","data":[{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.2","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.3","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.4","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.5","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.6","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.7","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.8","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.9","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.10","致命告警":0},{"一般告警":0,"提示告警":0,"严重告警":0,"key":"2018.11","致命告警":0},{"一般告警":483,"提示告警":521,"严重告警":503,"key":"2018.12","致命告警":532},{"一般告警":2661,"提示告警":2609,"严重告警":2679,"key":"2019.1","致命告警":2708}]}  
	 */
	@RequestMapping(value="alarms/level-count–everymonth",method=RequestMethod.GET)
	public JSONObject getLevelCountEverymonth() {
		
		List<JSONObject> rtData = new ArrayList<>();
		//获取去年开始日期
		Date beginDayOfYear = DateUtil.getBeginDayOfYear();
		Date beginDayOfLastYear = DateUtil.getBeginDayOfYear(DateUtil.getFrontDay(beginDayOfYear, 1));
		List<Alarm> alarmList = alarmService.getListFromYear(beginDayOfLastYear);

		List<Map<String, Date>> list = DateUtil.getBefore12Months();
	    for (int i = 0; i < list.size(); i++) {
	    	 Date startTime = list.get(list.size() - 1 - i).get("startDay");
             Date endTime = list.get(list.size() - 1 - i).get("endDay");

             JSONObject json = new JSONObject();
             json.put("key", DateUtil.getNowYear(startTime) + "." +DateUtil.getNowMonth(startTime));
             json.put(AlarmLevelEnum.DANGEROUS.getName(),getCountByStartTimeAndEndTimeAndLevel(alarmList,AlarmLevelEnum.DANGEROUS,startTime,endTime));
             json.put(AlarmLevelEnum.NORMAL.getName(),getCountByStartTimeAndEndTimeAndLevel(alarmList,AlarmLevelEnum.NORMAL,startTime,endTime));
             json.put(AlarmLevelEnum.PROMPT.getName(),getCountByStartTimeAndEndTimeAndLevel(alarmList,AlarmLevelEnum.PROMPT,startTime,endTime));
             json.put(AlarmLevelEnum.SERIOUS.getName(),getCountByStartTimeAndEndTimeAndLevel(alarmList,AlarmLevelEnum.SERIOUS,startTime,endTime));
             rtData.add(json);
	    }
		return CommonUtil.success(rtData);
	}


	private int getCountByStartTimeAndEndTimeAndLevel( List<Alarm> alarmList , AlarmLevelEnum ale, Date startTime, Date endTime) {
		 List<Alarm> ls = alarmList.stream().filter(x -> x.getAlarmLevel().intValue() == ale.getValue()
        		 && x.getAlarmDate().getTime() >= startTime.getTime()
        		 && x.getAlarmDate().getTime() <= endTime.getTime()).collect(Collectors.toList());
		return ls.size();
		
	}
	
	
	/**获取本年度,本月总告警数
	 * @author shaosen
	 * @date 2019年1月11日
	 * @param   
	 * @return {"msg":"请求成功","code":"200","data":[{"isRise":true,"value":10657,"key":"year"},{"isRise":true,"value":10657,"key":"month"}]}  
	 */
	@RequestMapping(value="alarm-total-count",method=RequestMethod.GET)
	public JSONObject getAlarmTotalCount() {
		//获取相关日期
		Date beginDayOfYear = DateUtil.getBeginDayOfYear();
		Date beginDayOfMonth = DateUtil.getBeginDayOfMonth();
		
		//获取去年开始日期
		Date frontDay = DateUtil.getFrontDay(beginDayOfYear, 1);
		Date lastYearBeginTime = DateUtil.getBeginDayOfYear(frontDay);
		
		//获取上个月开始日期
		Date frontDay2 = DateUtil.getFrontDay(beginDayOfMonth, 1);
		Date lastMonthBeginTime = DateUtil.getBeginDayOfMonth(frontDay2);
		
		
		List<Alarm> alarmList = alarmService.getListFromYear(lastYearBeginTime);
		long lastYearCt = alarmList.stream().filter(x -> x.getAlarmDate().getTime() >= lastYearBeginTime.getTime() 
				&& x.getAlarmDate().getTime() < beginDayOfYear.getTime()).count();
		
		long nowYearCt = alarmList.stream().filter(x -> x.getAlarmDate().getTime() >= beginDayOfYear.getTime()).count();
		
		long lastMonthCt = alarmList.stream().filter(x -> x.getAlarmDate().getTime() >= lastMonthBeginTime.getTime() 
				&& x.getAlarmDate().getTime() < beginDayOfMonth.getTime()).count();
		
		long nowMonthCt = alarmList.stream().filter(x -> x.getAlarmDate().getTime() >= beginDayOfMonth.getTime()).count();
		
		
		JSONObject yjs = new JSONObject();
		yjs.put("key", "year");
		yjs.put("value",  nowYearCt);
		yjs.put("isRise", nowYearCt > lastYearCt);
		
		JSONObject mjs = new JSONObject();
		mjs.put("key", "month");
		mjs.put("value", nowMonthCt);
		mjs.put("isRise", nowMonthCt > lastMonthCt);
		
		List<JSONObject> rtdata = new ArrayList<>();
		rtdata.add(yjs);
		rtdata.add(mjs);
		
		return CommonUtil.success(rtdata);
	}
	
	
	
	/**获取最新的20条告警 
	 * @return   
	 * @author shaosen
	 * @Date 2019年1月18日
	 */
	@RequestMapping(value="alarms/part",method=RequestMethod.GET)
	public JSONObject getAlarmTop20() {
		List<Alarm> list = alarmService.getAllList();
		if(list.size() == 0)
			return CommonUtil.success(new ArrayList<>());
		
		list = list.stream().sorted(Comparator.comparing(Alarm::getAlarmDate).reversed()).collect(Collectors.toList());
		
		List<Alarm> tmp = new ArrayList<>();
		if(list.size()>=20) {
			for (int i = 0; i < 20; i++) {
				tmp.add(list.get(i));
			}
		}else {
			tmp = list;
		}
		
		List<JSONObject> rtdata = new ArrayList<>();
		tmp.forEach(x -> {
			
			String location = "";
			MeasObj measObj = measObjModuleCenter.getMeasObj(x.getObjectId());
        	if(measObj != null) {
        		SectionDto section = sectionService.getSectionById(measObj.getSectionId());
        		if(section != null) {
        			location = section.getStore().getTunnel().getName() + section.getName();
        		}
        	}
			
			JSONObject json = new JSONObject();
			json.put("id", x.getId());
			json.put("location", location );
			json.put("alarmDate", x.getAlarmDate());
			json.put("alarmLevel", AlarmLevelEnum.getEnum(x.getAlarmLevel()).getName());
			json.put("name", x.getAlarmName());
			rtdata.add(json);
		});
		
		return CommonUtil.success(rtdata);
	}

}
