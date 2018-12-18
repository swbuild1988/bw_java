package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeasValueAIMapper {

    int addMeasValueAI(MeasValueAI measValueAI);

    /**
     * 添加多条数据
     * @param measValueAIS
     * @return
     */
    int addMeasValueAIBatch(List<MeasValueAI> list);

    /**
     * 获取一段时间的数据
     * @return
     */
    List<MeasValueAI> getListByObjectIdAndTime(@Param("objectId") Integer objectId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    
    List<MeasValueAI> getByObjectId(Integer objectId);
    
    /**
     * 批量删除
     */
    void deleteBatch(Date time);

	int getCountBymoIdsAndTime(@Param("list")List<Integer> list, @Param("startTime")Date startTime,@Param("endTime") Date endTime);

	List<MeasValueAI> pageQuery(@Param("start")Integer start, @Param("end")Integer end, @Param("list")List<Integer> list, @Param("startTime")Date startTime,@Param("endTime") Date endTime);

	List<MeasValueAI> getListByMoIdsAndTime(@Param("list")List<Integer> list, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

	List<Date> getX(@Param("startTime")Date startTime,@Param("endTime") Date endTime);
}
