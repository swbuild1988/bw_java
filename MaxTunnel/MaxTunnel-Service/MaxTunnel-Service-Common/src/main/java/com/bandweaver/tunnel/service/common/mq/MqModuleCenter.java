package com.bandweaver.tunnel.service.common.mq;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqModuleCenter implements ModuleCenterInterface {

    @Autowired
    private ConnectionFactory connectionFactory;

    private Channel channel = null;

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void start() {
        LogUtil.info("MQ ModuleCenter Start !");
        if (channel == null) {
            try {
                channel = connectionFactory.createConnection().createChannel(false);
                channel.exchangeDeclare(PropertiesUtil.getString(Constants.QUEUE_EXCHANGE), "fanout");
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
    }

    @Override
    public void stop() {

    }
}
