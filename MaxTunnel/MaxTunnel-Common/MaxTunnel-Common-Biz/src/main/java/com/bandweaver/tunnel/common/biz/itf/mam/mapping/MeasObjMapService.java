package com.bandweaver.tunnel.common.biz.itf.mam.mapping;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.mam.mapping.MeasObjMap;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjMapVo;
import com.github.pagehelper.PageInfo;

public interface MeasObjMapService {

	MeasObjMap getByObjectIdAndInputValue(Integer objectId, Integer inputValue);
	
	MeasObjMap getById(Integer id );

	void add(MeasObjMap measObjMap);

	void delete(Integer id);

	void deleteBatch(List<Integer> list);

	void update(MeasObjMap pojo);

	MeasObjMap getMaxViewMeasObj(Integer objectId, Integer inputValue);

	PageInfo<MeasObjMap> dataGrid(MeasObjMapVo vo);

}
