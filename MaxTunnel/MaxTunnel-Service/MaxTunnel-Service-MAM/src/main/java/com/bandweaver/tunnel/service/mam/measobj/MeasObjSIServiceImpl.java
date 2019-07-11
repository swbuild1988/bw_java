package com.bandweaver.tunnel.service.mam.measobj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSIDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSIService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjSIMapper;
@Service
public class MeasObjSIServiceImpl implements MeasObjSIService {

	@Autowired
	private MeasObjSIMapper measObjSIMapper;
	
	@Override
	public MeasObjSI getMeasObjSI(Integer id) {
		return measObjSIMapper.getMeasObjSI(id);
	}

	@Override
	public List<MeasObjSI> getAllMeasObjSIs() {
		return measObjSIMapper.getAllMeasObjSIs();
	}

	@Override
	public ListPageUtil<MeasObjSIDto> getMeasObjSIByCondition(MeasObjVo vo) {
    	List<MeasObjSIDto> list = measObjSIMapper.getMeasObjSIByCondition(vo);
    	LogUtil.info("list:" + list);
    	ListPageUtil<MeasObjSIDto> pageInfo = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
    	LogUtil.info("pageInfo:" + pageInfo);
		return pageInfo;
	}

}
