package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSIDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjSIVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeasObjSIMapper {

    int insert(MeasObjSI record);
    
    int insertSelective(MeasObjSI record);

    int update(MeasObjSI record);

    MeasObjSI getMeasObjSI(@Param("id") Integer id);

    List<MeasObjSI> getAllMeasObjSIs();

	void addBatch(List<MeasObjSI> SIList);

	void deleteByPrimaryKey(Integer id);

	List<MeasObjSIDto> dataGrid(MeasObjSIVo siVo);

}
