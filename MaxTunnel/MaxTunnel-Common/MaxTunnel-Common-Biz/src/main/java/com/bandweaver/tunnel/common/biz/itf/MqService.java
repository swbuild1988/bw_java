package com.bandweaver.tunnel.common.biz.itf;

import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;

public interface MqService {

	/**发送消息到告警队列 
	 * @param alarm   
	 * @author shaosen
	 * @Date 2018年10月29日
	 */
	void send2AlarmQueue(Alarm alarm);

	/**发送消息到应急预案队列 
	 * @param queue
	 * @param msg   
	 * @author shaosen
	 * @Date 2018年10月29日
	 */
	void send2PlanQueue(String queue, String msg);

	/**发送到大屏应急预案队列 
	 * @param string   
	 * @author shaosen
	 * @Date 2018年11月13日
	 */
	void send2BigScreenQueue(String msg);

}
