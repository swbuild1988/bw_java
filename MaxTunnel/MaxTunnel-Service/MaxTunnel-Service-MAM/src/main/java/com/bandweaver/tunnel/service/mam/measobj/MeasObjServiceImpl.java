package com.bandweaver.tunnel.service.mam.measobj;

import java.util.*;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIParam;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import org.activiti.engine.impl.json.JsonObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
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
    @Autowired
    private SectionService sectionService;
    //    @Autowired
//    private RedisTemplate redisTemplate;
    @Autowired
    private VideoServerService videoServerService;


    //    @Caching(put = @CachePut(key = "'measObj' + #p0.id"), evict = @CacheEvict())
    @Override
    public int add(MeasObj measObj) {
        return measObjMapper.insertSelective(measObj);
    }

    @Override
    public MeasObj get(Integer id) {
        return measObjModuleCenter.getMeasObj(id);
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

        if (vo.getObjtypeIds() != null && vo.getObjtypeIds().isEmpty()) {
            vo.setObjtypeIds(null);//防止mapper.xml拼接sql出错
        }
        if (vo.getTunnelIds() != null && vo.getTunnelIds().isEmpty()) {
            vo.setTunnelIds(null);//防止mapper.xml拼接sql出错
        }
        if (vo.getStoreIds() != null && vo.getStoreIds().isEmpty()) {
            vo.setStoreIds(null);//防止mapper.xml拼接sql出错
        }
        if (vo.getSectionIds() != null && vo.getSectionIds().isEmpty()) {
            vo.setSectionIds(null);//防止mapper.xml拼接sql出错
        }
        if (vo.getIds() != null && vo.getIds().isEmpty()) {
            vo.setIds(null);//防止mapper.xml拼接sql出错
        }
        List<MeasObjDto> list = measObjMapper.getMeasObjByCondition(vo);
        return list == null ? Collections.emptyList() : list;
    }

    @Override
    public List<MeasObj> getMeasObjByLongitudeAndLatitudeAndHeight(String longitude, String latitude, String height) {
        return measObjMapper.getMeasObjByLongitudeAndLatitudeAndHeight(longitude, latitude, height);
    }

    @Override
    public List<MeasObj> getAllMeasObjList() {
        return measObjModuleCenter.getMeasObjs();
    }

    @Override
    public List<MeasObj> getListByIds(List<Integer> ids) {
        return measObjModuleCenter.getMeasObjsByIds(ids);
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
        List<MeasObjDto> list = measObjMapper.getMeasObjByCondition(vo);
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
                Double cv = measObjModuleCenter.getMeasObjAI(objId).getCv();
                return cv == null ? 0 : cv;
            case DI:
                return measObjModuleCenter.getMeasObjDI(objId).getCv() ? 1 : 0;
            case SI:
                Integer cv2 = measObjModuleCenter.getMeasObjSI(objId).getCv();
                return cv2 == null ? 0 : cv2;
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
            if (measObj == null) continue;
            list.add(measObj);
        }
        LogUtil.info("获取指定目标对象：" + list);
        return list;
    }

    @Override
    public Set<MeasObj> getMeasObjsByTargetValAndSection(String targetValue, List<Section> sectionList) {
        Integer objectTypeId = DataTypeUtil.toInteger(targetValue);

        Set<MeasObj> set = new HashSet<>();
        //查询所有仓里指定类型的监测对象
        for (Section section : sectionList) {
            List<MeasObj> list = measObjMapper.getListBySectionIDAndObjectTypeID(section.getId(), objectTypeId);
            LogUtil.info("所在区段:" + section.getName() + "\n"
                    + "监测类型：" + ObjectType.getEnum(objectTypeId).getName() + "\n"
                    + "结果列表：" + list);
            set.addAll(list);
        }
        return set;
    }



    @Override
    public void setPlanIds(int objtypeId, String planIds) {
        measObjMapper.updatePlanIdsByObjtypeId(objtypeId, planIds);

        // 更新缓存
        List<MeasObj> collect = measObjModuleCenter.getMeasObjs().stream().filter(a -> a.getObjtypeId() == objtypeId)
                .collect(Collectors.toList());
        collect.stream().forEach(b -> {  b.setPlanIds(planIds); measObjModuleCenter.updateMeasObj(b); });
    }



    @Override
    public void setVideoIds(int id, String videoIds) {
        measObjMapper.updateVideoIdsByKeyId(id, videoIds);

        // 更新缓存
        MeasObj measObj = measObjModuleCenter.getMeasObj(id);
        measObj.setVideoIds(videoIds);
        measObjModuleCenter.updateMeasObj(measObj);
    }


    @Override
    public List<VideoDto> getLocalSectionVideoList(int sectionId) {
        List<VideoDto> videoDtoList = videoServerService.getVideosBySection(sectionId);
        return videoDtoList;


    }

    @Override
    public List<JSONObject> getMeasObjMaxOrMinValue(Integer tunnelId, Integer storeId, Integer areaId) {
        List<MeasObjAI> measObjAIList = measObjModuleCenter.getMeasObjAIs();
        if (tunnelId != null) {
            measObjAIList = measObjAIList.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
        }
        if (storeId != null) {
            measObjAIList = measObjAIList.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
        }
        if (areaId != null) {
            measObjAIList = measObjAIList.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());
        }


        // 分别查询温度，湿度等的极限值
        if (measObjAIList != null && measObjAIList.size() > 0) {
            MeasObjAIParam temperature = new MeasObjAIParam(Double.MIN_VALUE);
            MeasObjAIParam humidity = new MeasObjAIParam(Double.MIN_VALUE);
            MeasObjAIParam oxygen = new MeasObjAIParam(Double.MAX_VALUE);
            MeasObjAIParam h2s = new MeasObjAIParam(Double.MIN_VALUE);
            MeasObjAIParam ch4 = new MeasObjAIParam(Double.MIN_VALUE);
            MeasObjAIParam co = new MeasObjAIParam(Double.MIN_VALUE);

            for (ObjectType objType : ObjectType.values()) {
                int typeId = objType.getValue();
                MeasObjAI measObjAi = new MeasObjAI();
                List<MeasObjAI> tmps = new ArrayList<>();

                switch (objType) {
                    case TEMPERATURE:// 温度
                        tmps = measObjAIList.stream().filter(a -> a.getObjtypeId().intValue() == typeId).collect(Collectors.toList());
                        if(!tmps.isEmpty()) {
                            measObjAi = tmps.stream().max(Comparator.comparing(MeasObjAI::getCv)).get();
                            temperature.setCv(measObjAi.getCv());
                            temperature.setObjId(measObjAi.getId());
                            temperature.setObjTypeName(objType.getName());
                            temperature.setSectionId(measObjAi.getSectionId());
                        }

                        break;
                    case HUMIDITY:// 湿度
                        tmps = measObjAIList.stream().filter(a -> a.getObjtypeId().intValue() == typeId).collect(Collectors.toList());
                        if(!tmps.isEmpty()) {
                            measObjAi = tmps.stream().max(Comparator.comparing(MeasObjAI::getCv)).get();
                            humidity.setCv(measObjAi.getCv());
                            humidity.setObjId(measObjAi.getId());
                            humidity.setObjTypeName(objType.getName());
                            humidity.setSectionId(measObjAi.getSectionId());
                        }

                        break;
                    case OXYGEN:// 氧气
                        tmps = measObjAIList.stream().filter(a -> a.getObjtypeId().intValue() == typeId).collect(Collectors.toList());
                        if(!tmps.isEmpty()) {
                            measObjAi = tmps.stream().min(Comparator.comparing(MeasObjAI::getCv)).get();
                            oxygen.setCv(measObjAi.getCv());
                            oxygen.setObjId(measObjAi.getId());
                            oxygen.setObjTypeName(objType.getName());
                            oxygen.setSectionId(measObjAi.getSectionId());
                        }

                        break;
                    case H2S:// 硫化氢
                        tmps = measObjAIList.stream().filter(a -> a.getObjtypeId().intValue() == typeId).collect(Collectors.toList());
                        if(!tmps.isEmpty()) {
                            measObjAi = tmps.stream().max(Comparator.comparing(MeasObjAI::getCv)).get();
                            h2s.setCv(measObjAi.getCv());
                            h2s.setObjId(measObjAi.getId());
                            h2s.setObjTypeName(objType.getName());
                            h2s.setSectionId(measObjAi.getSectionId());
                        }

                        break;
                    case CH4:// 甲烷
                        tmps = measObjAIList.stream().filter(a -> a.getObjtypeId().intValue() == typeId).collect(Collectors.toList());
                        if(!tmps.isEmpty()) {
                            measObjAi = tmps.stream().max(Comparator.comparing(MeasObjAI::getCv)).get();
                            ch4.setCv(measObjAi.getCv());
                            ch4.setObjId(measObjAi.getId());
                            ch4.setObjTypeName(objType.getName());
                            ch4.setSectionId(measObjAi.getSectionId());
                        }

                        break;
                    case CO:// 一氧化碳
                        tmps = measObjAIList.stream().filter(a -> a.getObjtypeId().intValue() == typeId).collect(Collectors.toList());
                        if(!tmps.isEmpty()) {
                            measObjAi = tmps.stream().max(Comparator.comparing(MeasObjAI::getCv)).get();
                            co.setCv(measObjAi.getCv());
                            co.setObjId(measObjAi.getId());
                            co.setObjTypeName(objType.getName());
                            co.setSectionId(measObjAi.getSectionId());
                        }
                        break;

                    default:
                        break;
                }
            }

            List<MeasObjAIParam> paramList = new ArrayList<>();
            paramList.add(temperature);
            paramList.add(humidity);
            paramList.add(oxygen);
            paramList.add(h2s);
            paramList.add(ch4);
            paramList.add(co);

            List<JSONObject> list = getJsonObjList(paramList);
            return list;
        }
        return Collections.emptyList();
    }


    /**
     * convert to Json
     */
    private List<JSONObject> getJsonObjList(List<MeasObjAIParam> paramList) {
        List<JSONObject> list = new ArrayList<>();

        // 判断监测对象是否存在，如果不存在跳过
        for (MeasObjAIParam param : paramList) {
            if (param.getObjTypeName() == null) {
                continue;
            }
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("key", param.getObjTypeName());
            jsonobj.put("val", param.getCv());
            SectionDto dto = sectionService.getSectionById(param.getSectionId());
            jsonobj.put("location", dto == null ? "" : dto.getName());
            jsonobj.put("unit",ObjectType.getEnum(param.getObjTypeName()).getUnit() );
            list.add(jsonobj);
        }
        return list;
    }

}
