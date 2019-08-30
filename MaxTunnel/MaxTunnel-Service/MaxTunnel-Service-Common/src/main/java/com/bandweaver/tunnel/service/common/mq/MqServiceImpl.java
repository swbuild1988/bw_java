package com.bandweaver.tunnel.service.common.mq;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.rabbitmq.client.Channel;
import org.activiti.engine.repository.ModelQuery;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

import java.io.IOException;
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
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = (User) session.getAttribute(Constants.SESSION_USER_INFO);
            //生成队列名   userName-yyyyMMddHHmmss
            String date = DateUtil.getDate2StringFormat(new Date(),"yyyyMMddHHmmss");
            String queueName = user.getName() + "-" + date;
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

    @Override
    public void deleteQueue(String queueName) {
        LogUtil.info("删除队列queueName:" + queueName);
        Channel channel = mqModuleCenter.getChannel();
        try {
            channel.queueDelete(queueName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllQueue() {
        LogUtil.info("删除所有的队列");
        Channel channel = mqModuleCenter.getChannel();
        try {
            channel.queueDelete("aaa");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


