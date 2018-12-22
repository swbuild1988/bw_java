package com.bandweaver.tunnel.service.mam.data;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueSIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSI;
import com.bandweaver.tunnel.dao.mam.MeasValueSIMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeasValueSIServiceImpl implements MeasValueSIService {
    @Autowired
    private MeasValueSIMapper measValueSIMapper;

    @Override
    public int addMeasValueSI(MeasValueSI measValueSI) {
        return measValueSIMapper.addMeasValueSI(measValueSI);
    }

    @Override
    public List<MeasValueSI> getListByObjectAndTime(int objectId, Date startTime, Date endTime) {
        return measValueSIMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
    }

	@Override
	public void addMeasValueSIBatch(List<MeasValueSI> list) {
		measValueSIMapper.addMeasValueSIBatch(list);
	}
}
