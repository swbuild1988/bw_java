package com.bandweaver.tunnel.service.em.windplan;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

import javax.annotation.Resource;

@Service
public class WindPlanProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Object message){
        amqpTemplate.convertAndSend((String)PropertiesUtil.getValue("mq.queue.plan.wind"),message.toString());
        LogUtil.info("Send message : " + message.toString());
    }
}
