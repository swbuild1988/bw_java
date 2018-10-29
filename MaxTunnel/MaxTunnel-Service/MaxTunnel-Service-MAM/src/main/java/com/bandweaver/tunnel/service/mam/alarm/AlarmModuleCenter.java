package com.bandweaver.tunnel.service.mam.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.AlarmMapper;

@Service
public class AlarmModuleCenter implements ModuleCenterInterface {

	@Autowired
	private AlarmMapper alarmMapper;
	private HashMap<Integer, Alarm> alarmHashMap;

	@Override
	public void start() {
		LogUtil.info(" AlarmModuleCenter init start ! ");
		initData();
	}

	@Override
	public void stop() {
	}

	private void initData() {

		alarmHashMap = new HashMap<>();
		List<Alarm> list = alarmMapper.getAllAlarm();
		for (Alarm alarm : list) {
			alarmHashMap.put(alarm.getId(), alarm);
		}

	}

	public List<Alarm> getAlarms() {
		return new ArrayList<Alarm>(alarmHashMap.values());
	}

	public Alarm getAlarm(Integer id) {
		return alarmHashMap.get(id);
	}

	public void insert(Alarm alarm) {
		alarmHashMap.put(alarm.getId(), alarm);
	}

	public void remove(Integer id) {
		alarmHashMap.remove(id);
	}
}
