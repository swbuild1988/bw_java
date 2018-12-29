package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.SecurityLog;

public interface SecurityLogService {

	int save(SecurityLog scrtPojo);

	/**
	 * @Description: 查询安全日志
	 * @param @param securityLog
	 * @param @return   
	 * @return List<SecurityLog>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	List<SecurityLog> getSecurityLogList(SecurityLog securityLog);

}
