package com.bandweaver.tunnel.service.common.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
@Service
public class MqServiceImpl implements MqService {

	@Autowired
    private AmqpTemplate amqpTemplate;
	
	@Override
	public void send2AlarmQueue(String msg) {
		LogUtil.debug("发送到[告警]队列:" + msg );
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(Constants.ALARM_QUEUE), msg);
	}

	@Override
	public void send2PlanQueue(String queue, String msg) {
		LogUtil.info("发送到[平台应急预案]队列:" + msg);
		amqpTemplate.convertAndSend(queue, msg);
	}

	@Override
	public void send2BigScreenQueue(String msg) {
		LogUtil.info("发送到[大屏应急预案]队列:" + msg);
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(Constants.BIGSCREEN_QUEUE), msg);
	}
	
}
