package com.bandweaver.tunnel.dao.mam;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.common.biz.vo.mam.report.MeasObjReportVo;

public interface MeasObjReportMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteBatch(Date time);

    int insert(MeasObjReport record);

    int insertSelective(MeasObjReport record);

    MeasObjReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeasObjReport record);

    int updateByPrimaryKey(MeasObjReport record);

	MeasObjReport getByObjectIdAndStartTime(@Param("objectId")Integer objectId,@Param("startTime") Date startTime);

	List<MeasObjReportDto> getByCondition(MeasObjReportVo vo);

}