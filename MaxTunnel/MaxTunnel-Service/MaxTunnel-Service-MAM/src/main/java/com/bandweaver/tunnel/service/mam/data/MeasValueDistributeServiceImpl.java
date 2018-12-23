package com.bandweaver.tunnel.service.mam.data;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDistributeService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDistribute;
import com.bandweaver.tunnel.dao.mam.MeasValueDistributeMapper;

@Service
public class MeasValueDistributeServiceImpl implements MeasValueDistributeService {
    @Autowired
    private MeasValueDistributeMapper measValueDistributeMapper;

    @Override
    public int addMeasValueDis(MeasValueDistribute measValueDistribute) {
        return measValueDistributeMapper.addMeasValueDis(measValueDistribute);
    }

    @Override
    public List<MeasValueDistribute> getListByObjectAndTime(int objectId, Date startTime, Date endTime) {
        return measValueDistributeMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
    }

	@Override
	public void addMeasValueDisBatch(List<MeasValueDistribute> list) {
		measValueDistributeMapper.addMeasValueDisBatch(list);
	}
}
