package com.bandweaver.tunnel.service.mam.alarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.CountResult;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.dao.mam.AlarmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmMapper alarmMapper;
//	@Autowired
//	private AlarmModuleCenter alarmModuleCenter;
	@Autowired
	private MqService mqService;
	

	@Override
	public void add(Alarm alarm) {
		
		//save to DB
		alarm.setCleaned(false);
		alarmMapper.insertSelective(alarm);
		LogUtil.info("返回主键ID：" + alarm.getId() );
		//save to Cache
//		alarmModuleCenter.insert(alarm);
		//send to MQ
		mqService.send2AlarmQueue(alarm);
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
	public List<Alarm> getAllNonCleanedAlarm() {
		List<Alarm> list = new ArrayList<>();
//		List<Alarm> alarms = alarmModuleCenter.getAlarms();
		List<Alarm> alarms = alarmMapper.getAllAlarm();
		for (Alarm alarm : alarms) {
			if(alarm.getCleaned()!= null && !alarm.getCleaned()) {
				list.add(alarm);
			}
		}
		return list;
	}

	@Override
	public Alarm getById(Integer id) {
//		return alarmModuleCenter.getAlarm(id);
		return alarmMapper.selectByPrimaryKey(id);
	}

	@Override
	public void cleanAlarm(Alarm alarm) {
//		Alarm alm = alarmModuleCenter.getAlarm(alarm.getId());
		Alarm alm = alarmMapper.selectByPrimaryKey(alarm.getId());
		if(alm == null) {
			return;
		}
		
		if(alarm.getDescription() != null && alarm.getDescription().trim().length() != 0) 
			alm.setDescription(alm.getDescription() + "|" + alarm.getDescription());
		else
			alm.setDescription(alm.getDescription());
	
		alm.setCleaned(true);
		alm.setCleanedDate(DateUtil.getCurrentDate());
		alarmMapper.updateByPrimaryKeySelective(alm);
		
		//清除缓存
//		alarmModuleCenter.remove(alarm.getId());
		
	}


	@Override
	public List<CountResult> getObjCountByTimeOrderByDesc(Date startTime, Date endTime) {
		List<CountResult> list = alarmMapper.getObjCountByTimeOrderByDesc(startTime,endTime);
		return list == null ? Collections.emptyList() : list ;
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
}
