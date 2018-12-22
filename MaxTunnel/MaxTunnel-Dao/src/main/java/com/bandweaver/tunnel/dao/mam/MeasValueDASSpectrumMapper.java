package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDASSpectrum;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MeasValueDASSpectrumMapper {

    int addMeasValueDAS(MeasValueDASSpectrum measValueDASSpectrum);

    /**
     * 获取一段时间的数据
     * @return
     */
    List<MeasValueDASSpectrum> getListByObjectIdAndTime(@Param("objectId") int objectId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueDASBatch(List<MeasValueDASSpectrum> list);
	
	 /**
     * 批量删除
     */
    void deleteBatch(Date time);
}
