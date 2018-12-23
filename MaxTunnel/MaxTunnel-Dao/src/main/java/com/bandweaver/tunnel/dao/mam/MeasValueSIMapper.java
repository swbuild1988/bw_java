package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSI;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeasValueSIMapper {
    int addMeasValueSI(MeasValueSI measValueSI);

    /**
     * 获取一段时间的数据
     * @return
     */
    List<MeasValueSI> getListByObjectIdAndTime(@Param("objectId") int objectId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueSIBatch(List<MeasValueSI> list);

	List<MeasValueSI> getByObjectId(Integer objectId);
	
	 /**
     * 批量删除
     */
    void deleteBatch(Date time);
}
