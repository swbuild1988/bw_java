package com.bandweaver.tunnel.service.mam.alarm;

import java.util.*;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.dao.mam.AlarmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmMapper alarmMapper;
	@Autowired
	private MqService mqService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;
	@Autowired
	private VideoServerService videoServerService;
	@Autowired
	private MeasObjService measObjService;


	@Override
	public void add(Alarm alarm) {
		
		// save to DB
		alarm.setCleaned(false);
		alarmMapper.insertSelective(alarm);

		MeasObj measObj = measObjModuleCenter.getMeasObj(alarm.getObjectId());
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(alarm);

		List<JSONObject> planList = new ArrayList<>(10);
		List<VideoServerDto> videoList = new ArrayList<>(10);
		List<JSONObject> cvList = new ArrayList<>(10);
		getPlansAndVideosAndCv(measObj, planList, videoList, cvList);
		jsonObject.put("plans", planList);
		jsonObject.put("videos", videoList);
		jsonObject.put("cvList", cvList);
		jsonObject.put("sectionId", measObj == null ? null : measObj.getSectionId());

		// send to MQ
		mqService.sendToAlarmUMQueue(jsonObject.toJSONString());
		mqService.sendToAlarmVMQueue(jsonObject.toJSONString());
	}

	private void getPlansAndVideosAndCv(MeasObj measObj, List<JSONObject> planList, List<VideoServerDto> videoList, List<JSONObject> cvList) {
		if (measObj != null) {
			// 获取监测对象绑定的预案
			String planIds = measObj.getPlanIds();
			List<Integer> planIdList = CommonUtil.convertStringToList(planIds);
			for (Integer planId : planIdList) {
				JSONObject json = new JSONObject();
				json.put("id", planId);
				json.put("name", ProcessTypeEnum.getEnum(planId).getName());
				json.put("processKey", ProcessTypeEnum.getEnum(planId).getProcessKey());
				planList.add(json);
			}

			// 获取监测对象绑定的视频
			String videoIds = measObj.getVideoIds();
			List<Integer> videoIdList = CommonUtil.convertStringToList(videoIds);
			// 如果没有关联任何视频，则默认查找监测对象所在section的所有视频
			if (videoIdList.isEmpty()) {
				List<VideoDto> videoDtoList = videoServerService.getVideosBySection(measObj.getSectionId());
				for (VideoDto videoDto : videoDtoList) {
					videoList.add(videoDto.getVideoServerDto());
				}

			} else {
				// 查找绑定的视频
				for (Integer videoId : videoIdList) {
					VideoServerDto videoServer = videoServerService.getVideoServer(videoId);
					videoList.add(videoServer);
				}
			}

			// 获取监测极值
			cvList = measObjService.getMeasObjMaxOrMinValue(measObj.getTunnelId(), measObj.getStoreId(), measObj.getAreaId());

		}

	}


	@Override
	public int getCountByTunnelAndLevel(Integer tunnelId, int level) {
		return alarmMapper.getCountByTunnelAndLevel(tunnelId,level);
	}


	@Override
	public void addBatch(List<Alarm> list) {
		alarmMapper.addBatch(list);
	}


	@Override
	public PageInfo<AlarmDto> dataGrid(AlarmVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<AlarmDto> list = getByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public List<AlarmDto> getByCondition(AlarmVo vo) {
		 List<AlarmDto> list = alarmMapper.getByCondition(vo);
		 return list == null ? Collections.emptyList() : list ;
	}


	@Override
	public List<AlarmDto> getAllNonCleanedAlarm() {
		List<AlarmDto> list = alarmMapper.getAllNonCleanedAlarm();
		return list == null ? Collections.emptyList() : list ;
	}

	@Override
	public Alarm getById(Integer id) {
		return alarmMapper.selectByPrimaryKey(id);
	}

	@Override
	public void cleanAlarm(Alarm alarm) {
		Alarm alm = alarmMapper.selectByPrimaryKey(alarm.getId());
		if(alm == null || alm.getCleaned()) {
			return;
		}
		String desc = alm.getDescription() == null ? "" : alm.getDescription();
		if(alarm.getDescription() != null && alarm.getDescription().trim().length() != 0) 
			alm.setDescription(desc + " - " + alarm.getDescription());
		else
			alm.setDescription(desc);
	
		alm.setCleaned(true);
		alm.setCleanedDate(DateUtil.getCurrentDate());
		alarmMapper.updateByPrimaryKeySelective(alm);
	}

	
	@Override
	public void cleanAlarmBatch(AlarmVo vo) {
		List<Integer> ids = vo.getIds();
		for (Integer id : ids) {
			Alarm alarm = new Alarm();
			alarm.setId(id);
			alarm.setDescription(vo.getDescription());
			cleanAlarm(alarm);
		}
		
	}


	@Override
	public int getCountByObjectIds(List<Integer> objectIdList, Date startTime, Date endTime) {
		return alarmMapper.getCountByObjectIds(objectIdList,startTime,endTime);
	}


	@Override
	public List<AlarmDto> startPage(int start, int end,List<Integer> objectIdList, Date startTime, Date endTime) {
		List<AlarmDto> list = alarmMapper.startPage(start,end,objectIdList,startTime,endTime);
		return list == null ? Collections.emptyList() : list ;
	}


	@Override
	public int getCountByLevel(int level) {
		return alarmMapper.getCountByLevel(level);
		
	}


	@Override
	public int getCountByTunnel(Integer id) {
		return alarmMapper.getCountByTunnel(id);
	}


	@Override
	public List<Alarm> getListFromYear(Date beginDayOfYear) {
		List<Alarm> list = alarmMapper.getListFromYear(beginDayOfYear);
		return list == null ? Collections.emptyList() : list;
	}


	@Override
	public int getCountByTime(Date date) {
		return alarmMapper.getCountByTime(date);
	}


	@Override
	public List<Alarm> getAllList() {
		List<Alarm> list = alarmMapper.getAllAlarm();
		return list == null ? Collections.emptyList() : list;
	}


	@Override
	public List<AlarmDto> getListByStartTimeAndEndTime(Date startTime,Date endTime) {
		List<AlarmDto> list = alarmMapper.getListByStartTimeAndEndTime(startTime,endTime);
		return list == null ? Collections.emptyList() : list;
	}
}

