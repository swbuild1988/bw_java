package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeasObjAIMapper {

    int insert(MeasObjAI record);
    int insertSelective(MeasObjAI record);

    int update(MeasObjAI record);

    MeasObjAI getMeasObjAI(@Param("id") Integer id);

    List<MeasObjAI> getAllMeasObjAIs();

	void addBatch(List<MeasObjAI> aiList);
	void deleteByPrimaryKey(Integer id);


}
