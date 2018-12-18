package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDIDto;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.github.pagehelper.PageInfo;

public interface MeasObjDIService {

	PageInfo<MeasObjDIDto> getByCondition(MeasObjVo vo);

}
