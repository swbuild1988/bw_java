package com.bandweaver.tunnel.common.biz.itf.common;

import com.bandweaver.tunnel.common.biz.pojo.common.TunnelLight;

import java.util.List;

public interface TunnelLightService {

    /**
     * 获得所有的管廊光源
     *
     * @return
     */
    List<TunnelLight> getTunnelLights();

    /**
     * 添加管廊光源
     *
     * @param tunnelLight
     */
    void insertTunnelLight(TunnelLight tunnelLight);

    /**
     * 删除管廊光源
     *
     * @param tunnelLight
     */
    void deleteTunnelLight(int id);

    /**
     * 创造管廊中的点光源
     *
     * @param tunnelId
     * @param areaId
     * @param storeId
     */
    void createTunnelLights(Integer tunnelId, Integer areaId, Integer storeId);
}
