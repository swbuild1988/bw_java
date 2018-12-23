package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSIDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.github.pagehelper.PageInfo;

public interface MeasObjSIService {

    MeasObjSI getMeasObjSI(Integer id);

    List<MeasObjSI> getAllMeasObjSIs();

	PageInfo<MeasObjSIDto> getByCondition(MeasObjVo vo);
}
