package com.bandweaver.tunnel.service.mam.alarm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.alibaba.fastjson.JSON;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.rabbitmq.client.Channel;

/**
 *  在spring-rabbitmq中定义了接收者
 */
public class AlarmConsumer implements ChannelAwareMessageListener {

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			// 消息手动确认
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

			// 将byte数组转换成json对象
			LogUtil.info("Receive message : " + JSON.parse(new String(message.getBody())));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
    
}
