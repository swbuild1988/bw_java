package com.bandweaver.tunnel.service.common.module;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.itf.ScheduleJobService;
@Service
public class COMModuleCenter implements ModuleCenterInterface {

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@Override
	public void start() {
		
		try {
			scheduleJobService.init();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
