package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSODto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjSOVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeasObjSOMapper {

    int insert(MeasObjSO record);
    
    int insertSelective(MeasObjSO record);

    int update(MeasObjSO record);

    MeasObjSO getMeasObjSO(@Param("id") Integer id);

    List<MeasObjSO> getAllMeasObjSOs();

	void addBatch(List<MeasObjSO> aiList);

	void deleteByPrimaryKey(Integer id);

	List<MeasObjSODto> getByCondition(MeasObjSOVo vo);
}
