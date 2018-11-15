package com.bandweaver.tunnel.service.common.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
@Service
public class MqServiceImpl implements MqService {

	@Autowired
    private AmqpTemplate amqpTemplate;
	
	private static final String ALARM_QUEUE = "mq.queue.alarm";
	private static final String BIGSCREEN_QUEUE = "mq.queue.bigscreen";

	@Override
	public void send2AlarmQueue(Alarm alarm) {
		LogUtil.info("发送到[告警]队列:" + JSON.toJSONString(alarm) );
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(ALARM_QUEUE), JSON.toJSONString(alarm));
	}

	@Override
	public void send2PlanQueue(String queue, String msg) {
		LogUtil.info("发送到[平台应急预案]队列:" + msg);
		amqpTemplate.convertAndSend(queue, msg);
	}

	@Override
	public void send2BigScreenQueue(String msg) {
		LogUtil.info("发送到[大屏应急预案]队列:" + msg);
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(BIGSCREEN_QUEUE), msg);
	}
	
}
