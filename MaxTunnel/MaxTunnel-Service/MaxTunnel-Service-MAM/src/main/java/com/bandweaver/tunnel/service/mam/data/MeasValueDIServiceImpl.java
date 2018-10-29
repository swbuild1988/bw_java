package com.bandweaver.tunnel.service.mam.data;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDI;
import com.bandweaver.tunnel.dao.mam.MeasValueDIMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeasValueDIServiceImpl implements MeasValueDIService {
    @Autowired
    private MeasValueDIMapper measValueDIMapper;

    @Override
    public int addMeasValueDI(MeasValueDI measValueDI) {
        return measValueDIMapper.addMeasValueDI(measValueDI);
    }

    @Override
    public List<MeasValueDI> getListByObjectAndTime(int objectId, Date startTime, Date endTime) {
        return measValueDIMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
    }

	@Override
	public void addMeasValueDIBatch(List<MeasValueDI> list) {
		measValueDIMapper.addMeasValueDIBatch(list);
	}
}
