package com.bandweaver.tunnel.common.biz.itf.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSI;

import java.util.Date;
import java.util.List;

public interface MeasValueSIService {
    int addMeasValueSI(MeasValueSI measValueSI);

    List<MeasValueSI> getListByObjectAndTime(int objectId, Date startTime, Date endTime);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueSIBatch(List<MeasValueSI> list);
}
