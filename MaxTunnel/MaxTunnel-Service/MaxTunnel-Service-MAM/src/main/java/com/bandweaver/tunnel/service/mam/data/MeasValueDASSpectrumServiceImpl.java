package com.bandweaver.tunnel.service.mam.data;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDASSpectrumService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDASSpectrum;
import com.bandweaver.tunnel.dao.mam.MeasValueDASSpectrumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeasValueDASSpectrumServiceImpl implements MeasValueDASSpectrumService {
    @Autowired
    private MeasValueDASSpectrumMapper measValueDASSpectrumMapper;

    @Override
    public int addMeasValueDAS(MeasValueDASSpectrum measValueDASSpectrum) {
        return measValueDASSpectrumMapper.addMeasValueDAS(measValueDASSpectrum);
    }

    @Override
    public List<MeasValueDASSpectrum> getListByObjectAndTime(int objectId, Date startTime, Date endTime) {
        return measValueDASSpectrumMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
    }

	@Override
	public void addMeasValueDASBatch(List<MeasValueDASSpectrum> list) {
		measValueDASSpectrumMapper.addMeasValueDASBatch(list);
	}
}
