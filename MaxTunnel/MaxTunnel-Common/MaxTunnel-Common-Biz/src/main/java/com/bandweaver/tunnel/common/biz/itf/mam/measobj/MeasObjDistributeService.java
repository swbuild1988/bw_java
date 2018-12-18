package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDistributeDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.github.pagehelper.PageInfo;

public interface MeasObjDistributeService {
    int add(MeasObjDistribute record);

    int update(MeasObjDistribute record);

    MeasObjDistribute getMeasObjDistribute(Integer id);

    List<MeasObjDistribute> getAllMeasObjDistributes();

	PageInfo<MeasObjDistributeDto> getByCondition(MeasObjVo vo);
}
