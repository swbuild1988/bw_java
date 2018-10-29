package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeasValueDIMapper {
    int addMeasValueDI(MeasValueDI measValueDI);

    /**
     * 获取一段时间的数据
     * @return
     */
    List<MeasValueDI> getListByObjectIdAndTime(@Param("objectId") int objectId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * @Description:批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueDIBatch(List<MeasValueDI> list);

	List<MeasValueDI> getByObjectId(Integer objectId);
	
	 /**
     * 批量删除
     */
    void deleteBatch(Date time);
}
