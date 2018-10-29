package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;

import java.util.List;

public interface MeasObjSIService {

    MeasObjSI getMeasObjSI(Integer id);

    List<MeasObjSI> getAllMeasObjSIs();
}
