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
         List<MeasObjDto> list = measObjMapper.getMeasObjByCondition(vo);
         return list == null ? Collections.emptyList() : list ;
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
                Double cv = measObjModuleCenter.getMeasObjAI(objId).getCV();
            	return cv == null ? 0 : cv;
            case DI:
                return measObjModuleCenter.getMeasObjDI(objId).getCV() ? 1 : 0;
            case SI:
                Integer cv2 = measObjModuleCenter.getMeasObjSI(objId).getCV();
                return cv2 == null ? 0: cv2;
            default:
                break;
        }
        return 0;
    }

	@Override
	public List<MeasObj> getMeasObjByTargetVal(String targetValue) {
		ArrayList<MeasObj> list = new ArrayList<>();
		String[] strArr = targetValue.split(",");
		for (String objId : strArr) {
			MeasObj measObj = measObjModuleCenter.getMeasObj(DataTypeUtil.toInteger(objId));
			if(measObj == null) continue;
			list.add(measObj);
		}
		LogUtil.info("获取指定目标对象：" + list );
		return list;
	}

	@Override
	public List<MeasObj> getMeasObjsByTargetValAndVars(String targetValue, Integer sectionId) {
		Integer objectTypeId = DataTypeUtil.toInteger(targetValue);
		List<MeasObj> list = measObjMapper.getListBySectionIDAndObjectTypeID(sectionId,objectTypeId);
		
		LogUtil.info("所在区段:" + sectionId + "\n"
				+ "检测类型：" + ObjectType.getEnum(objectTypeId).getName() + "\n"
				+ "结果列表：" + list );
		
		if(list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		return list;
	}
}
