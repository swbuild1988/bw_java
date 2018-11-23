package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.ScheduleJobDto;
import com.bandweaver.tunnel.common.biz.pojo.ScheduleJob;
import com.bandweaver.tunnel.common.biz.vo.ScheduleJobVo;

public interface ScheduleJobMapper {
    int deleteByPrimaryKey(Integer jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Integer jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

	List<ScheduleJobDto> getDtoListByCondition(ScheduleJobVo vo);

	List<ScheduleJob> getByJobStatus(int jobStatus);

	ScheduleJob getByJobNameAndJobGroup(@Param("jobName")String jobName);
}