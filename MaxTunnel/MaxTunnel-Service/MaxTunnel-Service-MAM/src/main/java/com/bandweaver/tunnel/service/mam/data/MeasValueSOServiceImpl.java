package com.bandweaver.tunnel.service.mam.data;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueSOService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import com.bandweaver.tunnel.dao.mam.MeasValueSOMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeasValueSOServiceImpl implements MeasValueSOService {
    @Autowired
    private MeasValueSOMapper measValueSOMapper;

    @Override
    public int addMeasValueSO(MeasValueSO measValueSO) {
        return measValueSOMapper.addMeasValueSO(measValueSO);
    }

    @Override
    public List<MeasValueSO> getListByObjectAndTime(int objectId, Date startTime, Date endTime) {
        return measValueSOMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
    }

	@Override
	public void addMeasValueSOBatch(List<MeasValueSO> list) {
		measValueSOMapper.addMeasValueSOBatch(list);
	}
}
