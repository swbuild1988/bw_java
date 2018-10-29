package com.bandweaver.tunnel.common.biz.itf.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDI;

import java.util.Date;
import java.util.List;

public interface MeasValueDIService {
    int addMeasValueDI(MeasValueDI measValueDI);

    List<MeasValueDI> getListByObjectAndTime(int objectId, Date startTime, Date endTime);

	/**
	 * @Description:  批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addMeasValueDIBatch(List<MeasValueDI> list);
}
