package com.bandweaver.tunnel.service.mam.measobj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDIDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjDIService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjDIMapper;
@Service
public class MeasObjDIServiceImpl implements MeasObjDIService{

	@Autowired
	private MeasObjDIMapper measObjDIMapper;
	
	@Override
	public ListPageUtil<MeasObjDIDto> getMeasObjDIByCondition(MeasObjVo vo) {
    	
    	List<MeasObjDIDto> list = measObjDIMapper.getMeasObjDIByCondition(vo);
    	LogUtil.info("list:" + list);
    	ListPageUtil<MeasObjDIDto> pageInfo = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
    	LogUtil.info("pageInfo:" + pageInfo);
		return pageInfo;
	}

}
