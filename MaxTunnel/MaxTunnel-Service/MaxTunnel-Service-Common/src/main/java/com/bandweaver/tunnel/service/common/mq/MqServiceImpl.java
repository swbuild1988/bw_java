
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
	public void sendToAlarmUMQueue(String msg) {
		LogUtil.debug("发送到[UM告警]队列:" + msg );
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(Constants.QUEUE_ALARM_UM), msg);
	}
	
	@Override
	public void sendToAlarmVMQueue(String msg) {
		LogUtil.debug("发送到[VM告警]队列:" + msg );
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(Constants.QUEUE_ALARM_VM), msg);
	}

	@Override
	public void sendToPlanUMQueue(String msg) {
		LogUtil.debug("发送到[UM应急]队列:" + msg);
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(Constants.QUEUE_PLAN_UM), msg);
	}

	@Override
	public void sendToPlanVMQueue(String msg) {
		LogUtil.info("发送到[VM应急]队列:" + msg);
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(Constants.QUEUE_PLAN_VM), msg);
	}
	
}

