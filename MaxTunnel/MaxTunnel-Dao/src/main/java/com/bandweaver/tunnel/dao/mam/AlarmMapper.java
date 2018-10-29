package com.bandweaver.tunnel.dao.mam;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	
}