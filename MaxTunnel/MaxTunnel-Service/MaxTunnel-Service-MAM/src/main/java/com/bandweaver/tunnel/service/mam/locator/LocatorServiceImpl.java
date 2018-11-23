package com.bandweaver.tunnel.service.mam.locator;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.Locator;
import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;
import com.bandweaver.tunnel.common.biz.itf.mam.locator.LocatorService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocatorServiceImpl implements LocatorService {

    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;

    /**
     * 启动设备
     *
     * @param objId
     * @param username
     */
    @Override
    public void startLocator(int objId, Object user) {
        MeasObjSO measObjSO = measObjModuleCenter.getMeasObjSO(objId);
        measObjSO.setActived(true);

        Locator locator = new Locator();
        locator.setOwner(user);
//--------------绑定人员定位的经纬度，以便定位人员信息，后期删除该代码----------------
        Point3D p = new Point3D();
        int a = (int)(Math.random()*100);
        Double lon = MathUtil.add(112.4940279, a*0.0001);
        Double lat = MathUtil.add(37.7062123, a*0.0001);
        p.setLng(lon);
        p.setLat(lat);
        locator.setPosition(p);
//---------------------------------------------------------------------
        measObjSO.setCV(JSONObject.toJSONString(locator));

        // 更新状态（actived)
        measObjModuleCenter.updateMeasObj(measObjSO);
        // 更新值
        MeasValueSO measValueSO = new MeasValueSO();
        measValueSO.setObjectId(measObjSO.getId());
        measValueSO.setCV(measObjSO.getCV());
        measValueSO.setTime(new Date());
        // measObjModuleCenter.updateMeasObjSOValue(measValueSO);
        // 直接将数据塞进数据库
        measObjModuleCenter.insertMeasObjSOValue(measValueSO);
    }

    /**
     * 停用设备，将用户什么的剔除
     *
     * @param objId
     */
    @Override
    public void stopLocator(int objId) {
        MeasObjSO measObjSO = measObjModuleCenter.getMeasObjSO(objId);
        measObjSO.setActived(false);

        Locator locator = new Locator();
        measObjSO.setCV(JSONObject.toJSONString(locator));


        // 更新状态（actived)
        measObjModuleCenter.updateMeasObj(measObjSO);
        // 更新值
        MeasValueSO measValueSO = new MeasValueSO();
        measValueSO.setObjectId(measObjSO.getId());
        measValueSO.setCV(new JSONObject().toJSONString());
        measValueSO.setTime(new Date());
//        measObjModuleCenter.updateMeasObjSOValue(measValueSO);
        // 直接将数据塞进数据库
        measObjModuleCenter.insertMeasObjSOValue(measValueSO);
    }


    /**
     * 先判断传过来的cv中是否是Locator类型
     * 如果是locator类型，则是自己内部传过来的，直接赋值更新
     * 如果不是，则为适配器传过来的位置信息，将位置信息覆盖原来的position
     */
    @Override
    public void update(MeasObjSO measObjSO, MeasValueSO measValueSO) {


        Locator locator = (Locator) JSONObject.parseObject(measValueSO.getCV(), Locator.class);
        LogUtil.info("传来的locator：" + locator);
        // 适配器传过来的
        if (locator == null || locator.getOwner() == null) {

            // 获取传过来的3D信息
            Point3D position = (Point3D) JSONObject.parseObject(measValueSO.getCV(), Point3D.class);
            // 获取原来的信息，主要是用户信息
            locator = (Locator) JSONObject.parseObject(measObjSO.getCV(), Locator.class);
            if (locator == null) locator = new Locator();
            locator.setPosition(position);

            measObjSO.setCV(JSONObject.toJSONString(locator));
            measObjSO.setRefreshTime(measValueSO.getTime());

        } else {
            // 内部传过来的，如start， stop
            measObjSO.setCV(measValueSO.getCV());
            measObjSO.setRefreshTime(measValueSO.getTime());
        }

    }

    /**
     * 获得所有的设备信息
     *
     * @return 所有定位设备信息
     */
    @Override
    public List<MeasObjSO> getAllLocator() {

        // 获得所有的定位设备信息
        List<MeasObjSO> measObjSOS = measObjModuleCenter.getMeasObjSOs().stream()
                .filter(a -> a.getObjtypeId() == ObjectType.POSITION.getValue())
                .collect(Collectors.toList());

        return measObjSOS;
    }

    /**
     * 获得所有的在线设备信息
     *
     * @return 所有定位设备信息
     */
    @Override
    public List<MeasObjSO> getAllActivedLocator() {

        // 获得所有的在线定位设备信息
        List<MeasObjSO> measObjSOS = measObjModuleCenter.getMeasObjSOs().stream()
                .filter(a -> (a.getObjtypeId() == ObjectType.POSITION.getValue() && a.isActived()))
                .collect(Collectors.toList());

        return measObjSOS;
    }
}
