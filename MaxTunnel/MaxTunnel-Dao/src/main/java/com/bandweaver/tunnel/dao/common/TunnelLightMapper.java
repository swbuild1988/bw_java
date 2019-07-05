package com.bandweaver.tunnel.dao.common;

import com.bandweaver.tunnel.common.biz.pojo.common.TunnelLight;

import java.util.List;

public interface TunnelLightMapper {

    List<TunnelLight> getTunnelLights();

    List<TunnelLight> getTunnelLightsBySection(int sectionId);

    void insertSelective(TunnelLight tunnelLight);

    void deleteByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(TunnelLight tunnelLight);
}
