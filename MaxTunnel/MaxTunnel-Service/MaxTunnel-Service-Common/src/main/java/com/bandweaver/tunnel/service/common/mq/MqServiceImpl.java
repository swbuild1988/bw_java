package com.bandweaver.tunnel.service.common.mq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

import java.util.Date;

@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private MqModuleCenter mqModuleCenter;

    @Override
    public void send(String message) {
        try {
//        amqpTemplate.send(PropertiesUtil.getString(Constants.QUEUE_EXCHANGE), "", new Message(message.getBytes(), new MessageProperties()));
            Channel channel = mqModuleCenter.getChannel();
            channel.basicPublish(PropertiesUtil.getString(Constants.QUEUE_EXCHANGE), "", null, message.getBytes("UTF-8"));

            LogUtil.info("发送消息：" + message);
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    /**
     * 发送消息，带上类型
     *
     * @param type
     * @param message
     */
    @Override
    public void sendByType(String type, String message) {
        JSONObject object = new JSONObject();
        object.put("type", type);
        object.put("content", message);

        send(object.toJSONString());
    }

    @Override
    public String createQueue() {
        try {
            Channel channel = mqModuleCenter.getChannel();
            String queueName = "tunnel-" + (new Date()).getTime();
            boolean durable = true;
            boolean exclusive = false;
            boolean auto_delete = false;
            channel.queueDeclare(queueName, durable, exclusive, auto_delete, null);
            channel.queueBind(queueName, PropertiesUtil.getString(Constants.QUEUE_EXCHANGE), "");

            return queueName;
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


