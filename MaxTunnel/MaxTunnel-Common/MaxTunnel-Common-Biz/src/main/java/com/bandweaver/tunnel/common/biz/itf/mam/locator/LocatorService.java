package com.bandweaver.tunnel.common.biz.itf.mam.locator;

import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;

import java.util.List;

public interface LocatorService {

    /**
     * 启动设备
     *
     */
    void startLocator(int objId, Object user);

    /**
     * 停用设备
     *
     */
    void stopLocator(int objId);

    /**
     * 更新地理位置
     *
     */
    void update(MeasObjSO measObjSO, MeasValueSO measValueSO);

    /**
     * 获得所有的设备信息
     *
     * @return 所有定位设备信息
     */
    List<MeasObjSO> getAllLocator();

    /**
     * 获得所有的在线设备信息
     *
     * @return 所有定位设备信息
     */
    List<MeasObjSO> getAllActivedLocator();
}
