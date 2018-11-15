package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeasObjDIMapper {

	List<MeasObjDI> getAllMeasObjDIs();

	void insert(MeasObjDI measObjDI);
	
	void insertSelective(MeasObjDI measObjDI);

	void addBatch(List<MeasObjDI> aiList);

	MeasObjDI getMeasObjDI(Integer id);

	void update(MeasObjDI measObjDI);

	void deleteByPrimaryKey(Integer id);



}
