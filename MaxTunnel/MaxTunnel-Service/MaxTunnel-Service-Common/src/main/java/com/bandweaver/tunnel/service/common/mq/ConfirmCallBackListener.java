package com.bandweaver.tunnel.service.common.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * 确认后回调
 */
@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements RabbitTemplate.ConfirmCallback {
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
	}
}
