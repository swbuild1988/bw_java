package com.bandweaver.tunnel.service.common.module;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.itf.ScheduleJobService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
@Service
public class COMModuleCenter implements ModuleCenterInterface {

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@Override
	public void start() {
		long beginTime = System.currentTimeMillis();
		
		try {
			scheduleJobService.init();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		LogUtil.info(	"*********************************\n"
						+ "描述：加载所有定时任务\n"
						+ "耗时：" + (endTime - beginTime) + "ms\n"
						+ "*********************************");
		
	}

	@Override
	public void stop() {}

}
