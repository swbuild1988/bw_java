package com.bandweaver.tunnel.dao.common;

import com.bandweaver.tunnel.common.biz.pojo.common.TunnelRun;

public interface TunnelRunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TunnelRun record);

    int insertSelective(TunnelRun record);

    TunnelRun selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TunnelRun record);

    int updateByPrimaryKey(TunnelRun record);

	TunnelRun getRunInfo();
}