package com.bandweaver.tunnel.service.mam.data;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class DataConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }
}
