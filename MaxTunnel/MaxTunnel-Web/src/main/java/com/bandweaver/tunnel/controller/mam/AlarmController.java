package com.bandweaver.tunnel.controller.mam;

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
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.transform.MeasAlarm;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
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
	
	
	/**接收告警并发送到MQ队列
	 * @param alarm
	 * @return   
	 * @author shaosen
	 * @Date 2018年8月14日
	 */
	@RequestMapping(value = "alarms", method = RequestMethod.POST)
	public JSONObject add(@RequestBody MeasAlarm measAlarm) {
		
		LogUtil.info("接收到MaxView发送的告警：" + measAlarm );
		MeasObj measObj = measObjModuleCenter.getMeasObj(measAlarm.getObjectId());
		if(measObj == null) {
			LogUtil.info("监测对象[ " + measAlarm.getObjectId() + "]不存在");
			return null;
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
		List<Alarm> list = alarmService.getAllNonCleanedAlarm();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**查询所有未清除的告警数量
	 * @return   {"msg":"请求成功","code":"200","data":50}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "alarms/non-cleaned/count",method = RequestMethod.GET)
	public JSONObject getNonCleanedCount() {
		List<Alarm> list = alarmService.getAllNonCleanedAlarm();
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
	
	
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	
	
	/**获取管廊内各类缺陷的数目 
	 * @return   
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


}
