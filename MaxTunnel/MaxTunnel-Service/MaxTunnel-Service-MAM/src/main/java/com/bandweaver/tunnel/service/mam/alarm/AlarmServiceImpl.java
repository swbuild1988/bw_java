package com.bandweaver.tunnel.service.mam.alarm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.dao.mam.AlarmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmMapper alarmMapper;
	@Autowired
    private AmqpTemplate amqpTemplate;
	@Autowired
	private AlarmModuleCenter alarmModuleCenter;
	
	private static final String ROUTING_KEY = "mq.queue.alarm";

	@Override
	public void add(Alarm alarm) {
		
		//save to DB
		alarm.setCleaned(false);
		alarmMapper.insertSelective(alarm);
		LogUtil.info("返回主键ID：" + alarm.getId() );
		//save to Cache
		alarmModuleCenter.insert(alarm);
		//send to MQ
		sendMsg(alarm);
	}

	private void sendMsg(Alarm alarm) {
		LogUtil.info("Send to MQ:" + JSON.toJSONString(alarm) );
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(ROUTING_KEY), JSON.toJSONString(alarm));
		
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

	private List<AlarmDto> getByCondition(AlarmVo vo) {
		return alarmMapper.getByCondition(vo);
	}

	@Override
	public void cleanAlarm(Integer id, String description) {
		Alarm alarm = alarmModuleCenter.getAlarm(id);
		alarm.setDescription(alarm.getDescription() + "|" + description);
		alarm.setCleaned(true);
		alarm.setCleanedDate(DateUtil.getCurrentDate());
		alarmMapper.updateByPrimaryKeySelective(alarm);
		
		//清除缓存
		alarmModuleCenter.remove(id);
	}

	@Override
	public List<Alarm> getAllNonCleanedAlarm() {
		List<Alarm> list = new ArrayList<>();
		List<Alarm> alarms = alarmModuleCenter.getAlarms();
		for (Alarm alarm : alarms) {
			if(alarm.getCleaned()!= null && !alarm.getCleaned()) {
				list.add(alarm);
			}
		}
		return list;
	}

	@Override
	public Alarm getById(Integer id) {
		return alarmModuleCenter.getAlarm(id);
	}

	@Override
	public void cleanAlarm(Alarm alarm) {
		Alarm alm = alarmModuleCenter.getAlarm(alarm.getId());
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
		alarmModuleCenter.remove(alarm.getId());
		
	}
}
