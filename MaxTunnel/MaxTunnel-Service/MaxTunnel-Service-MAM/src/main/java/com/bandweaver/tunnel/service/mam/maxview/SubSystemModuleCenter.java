package com.bandweaver.tunnel.service.mam.maxview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.SubSystemService;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

@Service
public class SubSystemModuleCenter implements ModuleCenterInterface {

	@Autowired
	private SubSystemService subSystemService;
	@Autowired
	private TunnelService tunnelService;
	
	private HashMap<Integer, String> sessionMap;
	
	public String getSessionID(Integer configId) {
		return sessionMap.get(configId);
	}
	
	public void setSessionID(Integer configId,String sessionID) {
		sessionMap.put(configId,sessionID);
	}

	@Override
	public void start() {
	/*	sessionMap = new HashMap<>();
		List<TunnelSimpleDto> list = tunnelService.getListWithoutPermission();
		
		HashSet<Integer> set = new HashSet<>();
		for (TunnelSimpleDto tunnelSimpleDto : list) {
			TunnelDto dto = tunnelService.getDtoById(tunnelSimpleDto.getId());
			set.add(dto.getMaxviewConfigId());
		}*/

		List<Integer> list = tunnelService.getSubSystemIdList();
		for (Integer configId : list) {
			
			Callable<String> task = new Callable<String>() {
    			
    			@Override
    			public String call() throws Exception {
    				// 执行可能超时的操作
    				subSystemService.login(configId);
    				return "success";
    			}
    		};
    		
    		ExecutorService executorService = Executors.newSingleThreadExecutor();
    		Future<String> future = executorService.submit(task);
    		String result = null;
    		// 获取超时时间
    		long s = 60;
    		try {
    			try {
					s = PropertiesUtil.getLongValue(Constants.INIT_TIMEOUT);
				} catch (Exception e) {
					
				}
    			result = future.get(s, TimeUnit.SECONDS);
    		} catch(InterruptedException e) {
    			e.printStackTrace();
    		} catch(ExecutionException e) {
    			e.printStackTrace();
    		}catch(TimeoutException e) {
    			LogUtil.info("登陆时间已超过预定时间：" + s + "秒！");
    		}finally {
    			executorService.shutdown();
    		}
		}
		

	}

	@Override
	public void stop() {
	}

	
}
