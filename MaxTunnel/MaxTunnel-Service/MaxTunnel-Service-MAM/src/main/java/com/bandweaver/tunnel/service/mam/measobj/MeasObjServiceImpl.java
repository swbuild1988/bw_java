package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjAIMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjDIMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueAIMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MeasObjServiceImpl implements MeasObjService {
    @Autowired
    private MeasObjMapper measObjMapper;
    @Autowired
    private MeasObjAIMapper measObjAIMapper;
    @Autowired
    private MeasObjDIMapper measObjDIMapper;
    @Autowired
    private MeasValueAIMapper measValueAIMapper;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
//    @Autowired
//    private RedisTemplate redisTemplate;

    //    @Caching(put = @CachePut(key = "'measObj' + #p0.id"), evict = @CacheEvict())
    @Override
    public int add(MeasObj measObj) {
        return measObjMapper.insertSelective(measObj);
    }

    @Override
    public MeasObj get(Integer id) {
        MeasObj o = measObjMapper.selectByPrimaryKey(id);
        return o;
    }

    @Override
    public List<MeasObj> getList() {
        return null;
    }

    /**
     * 更新
     *
     * @param obj
     */
    @Override
    public void updateObj(MeasObj obj) {
        measObjMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void addObjBatch(List<MeasObj> list) {
        measObjMapper.addObjBatch(list);
    }

    @Override
    public List<MeasObjDto> getMeasObjByCondition(MeasObjVo vo) {
        return measObjMapper.getMeasObjByCondition(vo);
    }

    @Override
    public List<MeasObj> getMeasObjByLongitudeAndLatitudeAndHeight(String longitude, String latitude, String height) {
        return measObjMapper.getMeasObjByLongitudeAndLatitudeAndHeight(longitude, latitude, height);
    }

    @Override
    public List<MeasObj> getAllMeasObjList() {
        return measObjMapper.getAllMeasObjList();
    }

    @Override
    public List<MeasObj> getListByIds(List<Integer> ids) {
        return measObjMapper.getListByIds(ids);
    }

    @Transactional
    @Override
    public void addTestData(List<MeasObj> objList, List<MeasObjAI> aiList, List<MeasObjDI> diList, List<MeasValueAI> aiValList) {
        measObjMapper.addObjBatch(objList);
        measObjAIMapper.addBatch(aiList);
//		measObjDIMapper.addBatch(diList);
        measValueAIMapper.addMeasValueAIBatch(aiValList);

    }

    @Override
    public PageInfo<MeasObjDto> dataGrid(MeasObjVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<MeasObjDto> list = getMeasObjByCondition(vo);
        return new PageInfo<>(list);
    }

    @Override
    public List<Integer> getIdsByTunnelId(Integer id) {
        return measObjMapper.getIdsByTunnelId(id);
    }

    @Override
    public List<MeasObjDto> getMeasObjBySectionId(Integer id) {
        MeasObjVo measObjVo = new MeasObjVo();
        measObjVo.setSectionId(id);
        return getMeasObjByCondition(measObjVo);
    }

    @Override
    public double getMeasObjCVByIdAndDataType(Integer objId, Integer datatypeId) {
        DataType dataType = DataType.getEnum(datatypeId);
        switch (dataType) {
            case AI:
                return measObjModuleCenter.getMeasObjAI(objId).getCV();
            case DI:
                return measObjModuleCenter.getMeasObjDI(objId).getCV() ? 1 : 0;
            case SI:
                return measObjModuleCenter.getMeasObjSI(objId).getCV();
            default:
                break;
        }
        return 0;
    }

	@Override
	public List<MeasObj> getMeasObjByTargetVal(Integer targetValue) {
		MeasObj measObj = measObjModuleCenter.getMeasObj(targetValue);
		if(measObj == null) {
			return Collections.emptyList();
		}
		ArrayList<MeasObj> list = new ArrayList<>();
		list.add(measObj);
		return list;
	}

	@Override
	public List<MeasObj> getMeasObjsByTargetValAndVars(Integer targetValue, Integer sectionId) {
		DataType dataType = DataType.getEnum(targetValue);
		if(dataType == null) {
			throw new RuntimeException("Error:枚举值"+ targetValue +"不存在");
		}
		List<MeasObj> list = measObjMapper.getListBySectionIDAndDataTypeID(sectionId,targetValue);
		if(list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		return list;
	}
}
