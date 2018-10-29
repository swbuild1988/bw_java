package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.SecurityLog;

public interface SecurityLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SecurityLog record);

    int insertSelective(SecurityLog record);

    SecurityLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SecurityLog record);

    int updateByPrimaryKey(SecurityLog record);

	/**
	 * @Description: 查询列表
	 * @param @param securityLog
	 * @param @return   
	 * @return List<SecurityLog>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	List<SecurityLog> getSecurityLogList(SecurityLog securityLog);
}