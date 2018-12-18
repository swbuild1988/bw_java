package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDistributeDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjDistributeVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeasObjDistributeMapper {

    int insert(MeasObjDistribute record);
    
    int insertSelective(MeasObjDistribute measObjDistribute);

    int update(MeasObjDistribute record);

    MeasObjDistribute getMeasObjDistribute(@Param("id") Integer id);

    List<MeasObjDistribute> getAllMeasObjDistributes();

	void deleteByPrimaryKey(Integer id);

	List<MeasObjDistributeDto> dataGrid(MeasObjDistributeVo disVo);

	

}
