package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;

import java.util.List;

public interface MeasObjAIService {
    int add(MeasObjAI record);

    int update(MeasObjAI record);

    MeasObjAI getMeasObjAI(Integer id);

    List<MeasObjAI> getAllMeasObjAIs();
}
