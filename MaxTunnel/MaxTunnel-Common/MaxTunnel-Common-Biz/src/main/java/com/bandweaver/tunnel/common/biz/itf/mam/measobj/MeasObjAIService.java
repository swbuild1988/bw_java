package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MeasObjAIService {
    int add(MeasObjAI record);

    int update(MeasObjAI record);

    MeasObjAI getMeasObjAI(Integer id);

    List<MeasObjAI> getAllMeasObjAIs();

	PageInfo<MeasObjAIDto> getByCondition(MeasObjVo vo);
}
