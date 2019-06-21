package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSODto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MeasObjSOService {
    int add(MeasObjSO record);

    int update(MeasObjSO record);

    MeasObjSO getMeasObjSO(Integer id);

    List<MeasObjSO> getAllMeasObjSOs();
    
    PageInfo<MeasObjSODto> dataGrid(MeasObjVo vo);
}
