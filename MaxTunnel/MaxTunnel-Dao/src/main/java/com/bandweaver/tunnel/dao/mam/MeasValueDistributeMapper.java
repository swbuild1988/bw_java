package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDistribute;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeasValueDistributeMapper {
    int addMeasValueDis(MeasValueDistribute measValueDistribute);
    int insertSelective(MeasValueDistribute measValueDistribute);

    List<MeasValueDistribute> getListByObjectIdAndTime(@Param("objectId") int objectId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueDisBatch(List<MeasValueDistribute> list);
	
	 /**
     * 批量删除
     */
    void deleteBatch(Date time);
}