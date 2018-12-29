package com.bandweaver.tunnel.common.biz.itf;

import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;

public interface MqService {

	void sendToAlarmUMQueue(String msg);

	void sendToAlarmVMQueue(String msg);

	void sendToPlanUMQueue(String msg);

	void sendToPlanVMQueue(String msg);

}
