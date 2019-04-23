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
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.SubSystemService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;

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
		sessionMap = new HashMap<>();
		List<TunnelSimpleDto> list = tunnelService.getList();
		
		HashSet<Integer> set = new HashSet<>();
		for (TunnelSimpleDto tunnelSimpleDto : list) {
			TunnelDto dto = tunnelService.getDtoById(tunnelSimpleDto.getId());
			set.add(dto.getMaxviewConfigId());
		}
		
		for (Integer configId : set) {
			subSystemService.login(configId);
		}
		

	}

	@Override
	public void stop() {
	}

	
}
