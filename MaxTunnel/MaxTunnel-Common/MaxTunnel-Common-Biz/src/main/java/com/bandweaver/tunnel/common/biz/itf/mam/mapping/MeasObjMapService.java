package com.bandweaver.tunnel.common.biz.itf.mam.mapping;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.mam.mapping.MeasObjMap;

public interface MeasObjMapService {

	List<MeasObjMap> getByObjectIdAndInputValue(Integer objectId, Integer inputValue);
	
	MeasObjMap getById(Integer id );

	void add(MeasObjMap measObjMap);

	void delete(Integer id);

	void deleteBatch(List<Integer> list);

	void update(MeasObjMap pojo);

	void doAction(Integer objectId, Integer inputValue);

}
