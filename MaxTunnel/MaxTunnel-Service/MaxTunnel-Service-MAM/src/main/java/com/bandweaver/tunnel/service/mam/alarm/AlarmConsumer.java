package com.bandweaver.tunnel.service.mam.alarm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.alibaba.fastjson.JSON;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.rabbitmq.client.Channel;
import sun.rmi.runtime.Log;

/**
 * 在spring-rabbitmq中定义了接收者
 */
public class AlarmConsumer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            // 将byte数组转换成json对象
            LogUtil.info("Receive message : " + JSON.parse(new String(message.getBody())));
            // 手动ack,确保消息被消费了，当消费完成后在删除队列里的消息，否则一直存在于队列中，直到发送ack为止
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Throwable e) {
            LogUtil.error(e.toString());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}
