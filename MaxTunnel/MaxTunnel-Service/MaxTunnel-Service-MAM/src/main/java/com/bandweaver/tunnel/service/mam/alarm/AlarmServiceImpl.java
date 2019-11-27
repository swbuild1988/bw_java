package com.bandweaver.tunnel.service.mam.alarm;

import java.util.*;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.MonitorTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;
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
	private MeasObjModuleCenter measObjModuleCenter;
	@Autowired
	private VideoModuleCenter videoModuleCenter;
	@Autowired
	private MeasObjService measObjService;
	@Autowired
	private SectionService sectionService;

	@Override
	public void add(Alarm alarm) {
		JSONObject jsonObject = getJsonByAlarm(alarm);
		// 将消息广播出去
		mqService.sendByType("Alarm", jsonObject.toJSONString());
		// 刷新告警数量
		mqService.sendByType("personalInfo", "");
		// save to DB
		if (alarm.getCleaned() == null)
			alarm.setCleaned(false);
		alarmMapper.insertSelective(alarm);

	}

	private JSONObject getJsonByAlarm(Alarm alarm) {
		MeasObj measObj = measObjModuleCenter.getMeasObj(alarm.getObjectId());
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(alarm);

		if (measObj != null) {
			Integer sectionId = measObj.getSectionId();
			SectionDto dto = sectionService.getSectionById(sectionId);
			String location = dto.getStore().getTunnel().getName() + dto.getStore().getName() + dto.getArea().getName();

			List<JSONObject> planList = new ArrayList<>(10);
			List<VideoDto> videoList = new ArrayList<>(10);
			List<JSONObject> cvList = new ArrayList<>(10);
			getPlansAndVideosAndCv(measObj, planList, videoList, cvList);
			jsonObject.put("plans", planList);
			jsonObject.put("videos", videoList);
			jsonObject.put("cvList", cvList);
			jsonObject.put("sectionId", sectionId);
			jsonObject.put("location", location);
		}
		return jsonObject;
	}

	private void getPlansAndVideosAndCv(MeasObj measObj, List<JSONObject> planList, List<VideoDto> videoList,
			List<JSONObject> cvList) {
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
				List<VideoDto> videoDtoList = videoModuleCenter.getVideoDtos().stream()
						.filter(v -> v.getSectionId().intValue() == measObj.getSectionId().intValue())
						.collect(Collectors.toList());
				videoList.addAll(videoDtoList);
			} else {
				// 查找绑定的视频
				for (Integer videoId : videoIdList) {
					VideoDto videoServer = videoModuleCenter.getVideoDto(videoId);
					videoList.add(videoServer);
				}
			}

			// 获取监测极值
			cvList.addAll(measObjService.getMeasObjMaxOrMinValue(measObj.getTunnelId(), measObj.getStoreId(),
					measObj.getAreaId(), MonitorTypeEnum.ENVIRONMENTAL.getValue()));

		}

	}

	@Override
	public int getCountByTunnelAndLevel(Integer tunnelId, int level) {
		return alarmMapper.getCountByTunnelAndLevel(tunnelId, level);
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
		if (vo.getAlarmLevels() == null || vo.getAlarmLevels().size() < 1)
			vo.setAlarmLevels(null);
		List<AlarmDto> list = alarmMapper.getByCondition(vo);
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public List<AlarmDto> getAllNonCleanedAlarm() {
		List<AlarmDto> list = alarmMapper.getAllNonCleanedAlarm();
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public JSONObject getById(Integer id) {
		Alarm alarm = alarmMapper.selectByPrimaryKey(id);
		return getJsonByAlarm(alarm);
	}

	@Override
	public void cleanAlarm(Alarm alarm) {
		Alarm alm = alarmMapper.selectByPrimaryKey(alarm.getId());
		if (alm == null || alm.getCleaned()) {
			return;
		}
		String desc = alm.getDescription() == null ? "" : alm.getDescription();
		if (alarm.getDescription() != null && alarm.getDescription().trim().length() != 0)
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
		return alarmMapper.getCountByObjectIds(objectIdList, startTime, endTime);
	}

	@Override
	public List<AlarmDto> startPage(int start, int end, List<Integer> objectIdList, Date startTime, Date endTime) {
		List<AlarmDto> list = alarmMapper.startPage(start, end, objectIdList, startTime, endTime);
		return list == null ? Collections.emptyList() : list;
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
	public List<AlarmDto> getListByStartTimeAndEndTime(Date startTime, Date endTime) {
		List<AlarmDto> list = alarmMapper.getListByStartTimeAndEndTime(startTime, endTime);
		return list == null ? Collections.emptyList() : list;
	}
}
