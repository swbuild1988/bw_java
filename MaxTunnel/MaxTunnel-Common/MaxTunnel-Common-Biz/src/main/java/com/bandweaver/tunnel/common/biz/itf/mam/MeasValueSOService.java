package com.bandweaver.tunnel.common.biz.itf.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;

import java.util.Date;
import java.util.List;

public interface MeasValueSOService {
    int addMeasValueSO(MeasValueSO measValueSO);

    List<MeasValueSO> getListByObjectAndTime(int objectId, Date startTime, Date endTime);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueSOBatch(List<MeasValueSO> list);
}
