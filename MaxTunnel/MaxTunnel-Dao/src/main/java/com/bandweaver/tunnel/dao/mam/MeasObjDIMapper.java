package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDIDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;

import java.util.List;

public interface MeasObjDIMapper {

	List<MeasObjDI> getAllMeasObjDIs();

	void insert(MeasObjDI measObjDI);
	
	void insertSelective(MeasObjDI measObjDI);

	void addBatch(List<MeasObjDI> aiList);

	MeasObjDI getMeasObjDI(Integer id);

	void update(MeasObjDI measObjDI);

	void deleteByPrimaryKey(Integer id);

	List<MeasObjDIDto> getMeasObjDIByCondition(MeasObjVo vo);

}
