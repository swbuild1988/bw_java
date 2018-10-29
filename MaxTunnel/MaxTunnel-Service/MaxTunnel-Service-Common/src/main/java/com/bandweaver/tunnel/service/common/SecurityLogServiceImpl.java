package com.bandweaver.tunnel.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.SecurityLogService;
import com.bandweaver.tunnel.common.biz.pojo.SecurityLog;
import com.bandweaver.tunnel.common.platform.util.BSCheckUtil;
import com.bandweaver.tunnel.dao.common.SecurityLogMapper;

@Service
public class SecurityLogServiceImpl implements SecurityLogService{

	@Autowired
	private SecurityLogMapper securityLogMapper;

	@Override
	public int save(SecurityLog scrtPojo) {
		return securityLogMapper.insert(scrtPojo);
	}

	@Override
	public List<SecurityLog> getSecurityLogList(SecurityLog securityLog) {
		return securityLogMapper.getSecurityLogList(securityLog);
	}
	
	
}
