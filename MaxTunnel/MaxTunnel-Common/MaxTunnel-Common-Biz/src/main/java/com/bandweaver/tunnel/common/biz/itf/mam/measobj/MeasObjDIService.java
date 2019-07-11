package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDIDto;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;

public interface MeasObjDIService {

	ListPageUtil<MeasObjDIDto> getMeasObjDIByCondition(MeasObjVo vo);

}
