package com.bandweaver.tunnel.common.biz.itf.mam.report;

import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.common.biz.vo.mam.report.MeasObjReportVo;
import com.github.pagehelper.PageInfo;

public interface MeasObjReportService {

	void add(MeasObjReport report);

	MeasObjReport getByObjectIdAndStartTime(Integer id, Date startTime);

	List<MeasObjReportDto> getListByObjectIdAndTimeAndTimeType(Integer id, Date startTime, Date endTime, Integer value);

	PageInfo<MeasObjReportDto> dataGrid(MeasObjReportVo vo);
	
	int deleteBatch(Date time);
	
	List<MeasObjReportDto> getByCondition(MeasObjReportVo vo);

	void updateByPrimaryKeySelective(MeasObjReport report);
}
