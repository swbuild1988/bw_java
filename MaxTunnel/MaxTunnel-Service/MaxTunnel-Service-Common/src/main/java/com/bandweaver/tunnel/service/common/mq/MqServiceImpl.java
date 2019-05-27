package com.bandweaver.tunnel.service.common.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void send(String message) {
        amqpTemplate.send(PropertiesUtil.getString(Constants.QUEUE_EXCHANGE), "", new Message(message.getBytes(), new MessageProperties()));
    }

    @Override
    public String createQueue() {
        try {
            String queue = connectionFactory.createConnection().createChannel(false).queueDeclare().getQueue();
            return queue;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void sendToAlarmUMQueue(String msg) {
        LogUtil.info("发送到[UM告警]队列:" + msg);
        amqpTemplate.convertAndSend(PropertiesUtil.getString(Constants.QUEUE_ALARM_UM), msg);
    }

    @Override
    public void sendToAlarmVMQueue(String msg) {
        LogUtil.info("发送到[VM告警]队列:" + msg);
        amqpTemplate.convertAndSend(PropertiesUtil.getString(Constants.QUEUE_ALARM_VM), msg);
    }

    @Override
    public void sendToPlanUMQueue(String msg) {
        LogUtil.info("发送到[UM应急]队列:" + msg);
        amqpTemplate.convertAndSend(PropertiesUtil.getString(Constants.QUEUE_PLAN_UM), msg);
    }

    @Override
    public void sendToPlanVMQueue(String msg) {
        LogUtil.info("发送到[VM应急]队列:" + msg);
        amqpTemplate.convertAndSend(PropertiesUtil.getString(Constants.QUEUE_PLAN_VM), msg);
    }

}


