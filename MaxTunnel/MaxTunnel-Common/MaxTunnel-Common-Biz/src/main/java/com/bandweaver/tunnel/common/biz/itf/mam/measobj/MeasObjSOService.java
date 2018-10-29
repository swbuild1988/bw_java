package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;

import java.util.List;

public interface MeasObjSOService {
    int add(MeasObjSO record);

    int update(MeasObjSO record);

    MeasObjSO getMeasObjSO(Integer id);

    List<MeasObjSO> getAllMeasObjSOs();
}
