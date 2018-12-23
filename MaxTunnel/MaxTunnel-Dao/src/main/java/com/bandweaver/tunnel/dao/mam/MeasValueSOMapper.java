package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeasValueSOMapper {
	int addMeasValueSO(MeasValueSO measValueSO);

	List<MeasValueSO> getListByObjectIdAndTime(@Param("objectId") int objectId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	void addMeasValueSOBatch(List<MeasValueSO> list);

	List<MeasValueSO> getByObjectId(Integer id);
	
	 /**
     * 批量删除
     */
    void deleteBatch(Date time);
}
