package com.bandweaver.tunnel.dao.mam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.mam.CountResult;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;

public interface AlarmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Alarm record);

    int insertSelective(Alarm record);

    Alarm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Alarm record);

    int updateByPrimaryKey(Alarm record);

	int getCountByTunnelAndLevel(@Param("tunnelId")Integer tunnelId, @Param("level")int level);

	void addBatch(List<Alarm> list);

	List<AlarmDto> getByCondition(AlarmVo vo);

	List<Alarm> getAllAlarm();

	List<CountResult> getObjCountByTimeOrderByDesc(@Param("startTime")Date startTime, @Param("endTime")Date endTime);

	int getCountByObjectIds(@Param("list")List<Integer> list, @Param("startTime")Date startTime,@Param("endTime") Date endTime);

	List<AlarmDto> startPage(@Param("start")int start, @Param("end")int end, @Param("list")List<Integer> list,@Param("startTime")Date startTime, @Param("endTime")Date endTime);

	
}