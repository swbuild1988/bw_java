package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;

import java.util.List;

public interface MeasObjDistributeService {
    int add(MeasObjDistribute record);

    int update(MeasObjDistribute record);

    MeasObjDistribute getMeasObjDistribute(Integer id);

    List<MeasObjDistribute> getAllMeasObjDistributes();
}
