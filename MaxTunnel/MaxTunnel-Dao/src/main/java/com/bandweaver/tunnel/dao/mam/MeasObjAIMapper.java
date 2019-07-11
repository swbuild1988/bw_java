package com.bandweaver.tunnel.dao.mam;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;

public interface MeasObjAIMapper {

	int insert(MeasObjAI record);

	int insertSelective(MeasObjAI record);

	int update(MeasObjAI record);

	MeasObjAI getMeasObjAI(@Param("id") Integer id);

	List<MeasObjAI> getAllMeasObjAIs();

	void addBatch(List<MeasObjAI> aiList);

	void deleteByPrimaryKey(Integer id);

	List<MeasObjAIDto> getMeasObjAIByCondition(MeasObjVo vo);

}
