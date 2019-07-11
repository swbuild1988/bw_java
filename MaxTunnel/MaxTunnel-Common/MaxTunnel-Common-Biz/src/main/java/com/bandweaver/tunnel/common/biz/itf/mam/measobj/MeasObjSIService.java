package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSIDto;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;

public interface MeasObjSIService {

    MeasObjSI getMeasObjSI(Integer id);

    List<MeasObjSI> getAllMeasObjSIs();

	ListPageUtil<MeasObjSIDto> getMeasObjSIByCondition(MeasObjVo vo);
}
