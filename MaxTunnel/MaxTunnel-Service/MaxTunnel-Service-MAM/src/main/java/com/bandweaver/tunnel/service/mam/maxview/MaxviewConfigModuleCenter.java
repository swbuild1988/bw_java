package com.bandweaver.tunnel.service.mam.maxview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.MaxviewConfigService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;

@Service
public class MaxviewConfigModuleCenter implements ModuleCenterInterface {

	@Autowired
	private MaxviewConfigService maxviewConfigService;
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
		long beginTime = System.currentTimeMillis();
		sessionMap = new HashMap<>();
		List<TunnelSimpleDto> list = tunnelService.getList();
		
		HashSet<Integer> set = new HashSet<>();
		for (TunnelSimpleDto tunnelSimpleDto : list) {
			TunnelDto dto = tunnelService.getDtoById(tunnelSimpleDto.getId());
			set.add(dto.getMaxviewConfigId());
		}
		
		for (Integer configId : set) {
			maxviewConfigService.login(configId);
		}
		
		long endTime = System.currentTimeMillis();
		LogUtil.info(	"*********************************\n"
						+ "描述：登录MaxView终端\n"
						+ "耗时：" + (endTime - beginTime) +"ms\n"
						+ "*********************************"	);
	
	}

	@Override
	public void stop() {
	}

	
}
