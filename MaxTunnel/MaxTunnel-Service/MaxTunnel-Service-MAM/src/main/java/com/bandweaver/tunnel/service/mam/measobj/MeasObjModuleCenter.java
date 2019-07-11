package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import com.bandweaver.tunnel.common.biz.itf.mam.locator.LocatorService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.mam.*;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.*;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.common.biz.pojo.xml.ComplexObjectConvert;
import com.bandweaver.tunnel.common.biz.pojo.xml.Config;
import com.bandweaver.tunnel.common.biz.pojo.xml.ConvertType;
import com.bandweaver.tunnel.dao.mam.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;

import sun.util.logging.resources.logging;

import javax.annotation.PreDestroy;

@Service
public class MeasObjModuleCenter implements ModuleCenterInterface {
    @Autowired
    private MeasObjMapper measObjMapper;
    @Autowired
    private MeasObjAIMapper measObjAIMapper;
    @Autowired
    private MeasObjDIMapper measObjDIMapper;
    @Autowired
    private MeasObjSIMapper measObjSIMapper;
    @Autowired
    private MeasObjSOMapper measObjSOMapper;
    @Autowired
    private MeasObjDistributeMapper measObjDistributeMapper;
    @Autowired
    private VideoModuleCenter videoModuleCenter;
    @Autowired
    private LocatorService locatorService;
    @Autowired
    private MeasObjReportService measObjReportService;
    @Autowired
    private SectionService sectionService;

    /**
     * value table
     */
    @Autowired
    private MeasValueAIMapper measValueAIMapper;
    @Autowired
    private MeasValueDIMapper measValueDIMapper;
    @Autowired
    private MeasValueSIMapper measValueSIMapper;
    @Autowired
    private MeasValueSOMapper measValueSOMapper;
    @Autowired
    private MeasValueDistributeMapper measValueDistributeMapper;
    @Autowired
    private MeasValueDASSpectrumMapper measValueDASSpectrumMapper;
    @Autowired
    private MeasValueTabdictMapper measValueTabdictMapper;
    @Autowired
    private XMLService xmlService;

    private HashMap<Integer, MeasObj> measObjHashMap;
    private HashMap<Integer, MeasObjAI> measObjAIHashMap;
    private HashMap<Integer, MeasObjDI> measObjDIHashMap;
    private HashMap<Integer, MeasObjSI> measObjSIHashMap;
    private HashMap<Integer, MeasObjSO> measObjSOHashMap;
    private HashMap<Integer, MeasObjDistribute> measObjDistributeHashMap;

    public List<MeasObj> getMeasObjs() {
        return new ArrayList<MeasObj>(measObjHashMap.values());
    }

    public List<MeasObjAI> getMeasObjAIs() {
        return new ArrayList<MeasObjAI>(measObjAIHashMap.values());
    }

    public List<MeasObjDI> getMeasObjDIs() {
        return new ArrayList<MeasObjDI>(measObjDIHashMap.values());
    }

    public List<MeasObjSI> getMeasObjSIs() {
        return new ArrayList<MeasObjSI>(measObjSIHashMap.values());
    }

    public List<MeasObjSO> getMeasObjSOs() {
        return new ArrayList<MeasObjSO>(measObjSOHashMap.values());
    }

    public List<MeasObjDistribute> getMeasObjDistribute() {
        return new ArrayList<MeasObjDistribute>(measObjDistributeHashMap.values());
    }

    public MeasObj getMeasObj(int id) {
        return measObjHashMap.get(id);
    }

    public MeasObjAI getMeasObjAI(int id) {
        return measObjAIHashMap.get(id);
    }

    public MeasObjDI getMeasObjDI(int id) {
        return measObjDIHashMap.get(id);
    }

    public MeasObjSI getMeasObjSI(int id) {
        return measObjSIHashMap.get(id);
    }

    public MeasObjSO getMeasObjSO(int id) {
        return measObjSOHashMap.get(id);
    }

    /**
     * 获得复杂类型的值
     *
     * @param id
     * @return
     */
    public JSONObject getMeasObjComplexObjectCV(int id) {
        MeasObj measObj = measObjHashMap.get(id);
        ComplexObjectConvert complexObjectConvert = getComplexObjectConvertByMeasObj(measObj);
        if (complexObjectConvert == null) return null;

        JSONObject jsonObject = new JSONObject();

        for (ConvertType convertType : complexObjectConvert.getConvertTypes()) {
            String attribute = convertType.getCode();

            DataType dataType = DataType.getEnum(getDataTypeByConvertType(convertType));
            int tmpId = getConvertObjectId(measObj, convertType);

            JSONObject tmpCV = new JSONObject();
            tmpCV.put("descript", convertType.getDescribe());

            switch (dataType) {
                case AI:
                    tmpCV.put("value", measObjAIHashMap.get(tmpId).getCv());
                    break;

                case SI:
                    tmpCV.put("value", measObjSIHashMap.get(tmpId).getCv());
                    break;

                case DI:
                    tmpCV.put("value", measObjDIHashMap.get(tmpId).getCv());
                    break;

                case SO:
                    tmpCV.put("value", measObjSOHashMap.get(tmpId).getCv());
                    break;

                default:
                    continue;
            }

            jsonObject.put(attribute, tmpCV);
        }

        return jsonObject;
    }

    public List<MeasObj> getMeasObjsByIds(List<Integer> ids) {
        List<MeasObj> results = new ArrayList<>();
        for (Integer id : ids) {
            results.add(measObjHashMap.get(id));
        }
        return results;
    }

    public List<MeasObjAI> getMeasObjAIListByIds(List<Integer> ids) {
        List<MeasObjAI> results = new ArrayList<>();
        for (Integer id : ids) {
            MeasObjAI measObjAI = measObjAIHashMap.get(id);
            if (!StringTools.isNullOrEmpty(measObjAI)) {
                results.add(measObjAI);
            }
        }
        return results;
    }

    public List<MeasObjDI> getMeasObjDIListByIds(List<Integer> ids) {
        List<MeasObjDI> results = new ArrayList<>();
        for (Integer id : ids) {
            results.add(measObjDIHashMap.get(id));
        }
        return results;
    }

    public Object getMeasObjSIListByIds(List<Integer> ids) {
        List<MeasObjSI> results = new ArrayList<>();
        for (Integer id : ids) {
            results.add(measObjSIHashMap.get(id));
        }
        return results;
    }

    public Object getMeasObjSOListByIds(List<Integer> ids) {
        List<MeasObjSO> results = new ArrayList<>();
        for (Integer id : ids) {
            results.add(measObjSOHashMap.get(id));
        }
        return results;
    }

    public void insertMeasObj(MeasObj measObj) {
        measObj.setActived(true);

        if (measObj.getObjtypeId().intValue() != 0) {
            ObjectType objectType = ObjectType.getEnum(measObj.getObjtypeId());
            measObj.setDatatypeId(objectType.getDataType());
        }

        if (measObjHashMap.containsKey(measObj.getId())) {
            return;
        }

        // 根据storeid & areaid查找
        Section section = sectionService.getSectionByStoreAndArea(measObj.getStoreId(), measObj.getAreaId());
        if (section != null) {
            measObj.setSectionId(section.getId());
        } else {
            measObj.setSectionId(-1);
        }


        // 塞进数据库
        measObjMapper.insertSelective(measObj);
        insertObj2OwnDB(measObj);

        // 数据库成功后塞进缓存
        measObjHashMap.put(measObj.getId(), measObj);
        insertObj2OwnHashMap(measObj);
    }

    public void addMeasObjHashMap(MeasObj measObj) {
        measObjHashMap.put(measObj.getId(), measObj);
    }

    public void updateMeasObj(MeasObj measObj) {
        if (!measObjHashMap.containsKey(measObj.getId())) {
            return;
        }

        // 先更新数据库
        if (measObj.getStoreId() != null && measObj != null) {
            Section section = sectionService.getSectionByStoreAndArea(measObj.getStoreId(), measObj.getAreaId());
            if (section != null) {
                measObj.setSectionId(section.getId());
            } else {
                measObj.setSectionId(-1);
            }
        } else measObj.setSectionId(null);
        measObjMapper.updateByPrimaryKeySelective(measObj);

        // 再更新缓存
        measObjHashMap.put(measObj.getId(), measObj);
    }

    /**
     * 更新ai缓存
     *
     * @param measValueAI
     * @author shaosen
     * @Date 2018年10月18日
     */
    public void updateMeasObjAIValue(MeasValueAI measValueAI) {
        if (!measObjAIHashMap.containsKey(measValueAI.getObjectId()))
            return;

        // 更新缓存
        MeasObjAI ai = measObjAIHashMap.get(measValueAI.getObjectId());

        if (ai.getRefreshTime() != null && ai.getRefreshTime().getTime() >= measValueAI.getTime().getTime())
            return;
        ai.setCv(measValueAI.getCv());
        ai.setRefreshTime(measValueAI.getTime());
    }

    /**
     * 更新di缓存
     *
     * @param measValueDI
     * @author shaosen
     * @Date 2018年10月18日
     */
    public void updateMeasObjDIValue(MeasValueDI measValueDI) {
        if (!measObjDIHashMap.containsKey(measValueDI.getObjectId()))
            return;

        // 更新缓存
        MeasObjDI di = measObjDIHashMap.get(measValueDI.getObjectId());
        if (di.getRefreshTime() != null && di.getRefreshTime().getTime() >= measValueDI.getTime().getTime())
            return;
        di.setCv(measValueDI.getCv());
        di.setRefreshTime(measValueDI.getTime());
    }

    /**
     * 更新si缓存
     *
     * @param measValueSI
     * @author shaosen
     * @Date 2018年10月18日
     */
    public void updateMeasObjSIValue(MeasValueSI measValueSI) {
        if (!measObjSIHashMap.containsKey(measValueSI.getObjectId()))
            return;

        // 更新缓存
        MeasObjSI si = measObjSIHashMap.get(measValueSI.getObjectId());
        if (si.getRefreshTime() != null && si.getRefreshTime().getTime() >= measValueSI.getTime().getTime())
            return;
        si.setCv(measValueSI.getCv());
        si.setRefreshTime(measValueSI.getTime());
    }

    /**
     * 更新分布式缓存
     *
     * @param measValueDistribute
     * @author shaosen
     * @Date 2018年10月22日
     */
    public void updateMeasObjDistributeValue(MeasValueDistribute measValueDistribute) {
        if (!measObjDistributeHashMap.containsKey(measValueDistribute.getObjectId()))
            return;
        MeasObjDistribute tmp = measObjDistributeHashMap.get(measValueDistribute.getObjectId());
        if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueDistribute.getTime().getTime())
            return;

        tmp.setDcv(measValueDistribute.getDcv());
        tmp.setRefreshTime(measValueDistribute.getTime());
        tmp.setSpacePrecision(measValueDistribute.getSpacePrecision());
    }

    /**
     * 更新so缓存
     *
     * @param measValueSO
     * @author shaosen
     * @Date 2018年10月22日
     */
    public void updateMeasObjSOValue(MeasValueSO measValueSO) {
        if (!measObjSOHashMap.containsKey(measValueSO.getObjectId()))
            return;

        MeasObjSO tmp = measObjSOHashMap.get(measValueSO.getObjectId());

        if (!tmp.isActived()) {
            return;
        }
        if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueSO.getTime().getTime())
            return;

        // 如果是定位类型，转换之
        if (tmp.getObjtypeId() == ObjectType.POSITION.getValue()) {
            locatorService.update(tmp, measValueSO);
        } else {
            tmp.setRefreshTime(measValueSO.getTime());
            tmp.setCv(measValueSO.getCv());
        }
    }

    public void insertMeasObjSOValue(MeasValueSO measValueSO) {
        if (!measObjSOHashMap.containsKey(measValueSO.getObjectId()))
            return;

        MeasObjSO tmp = measObjSOHashMap.get(measValueSO.getObjectId());
        if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueSO.getTime().getTime())
            return;

        tmp.setRefreshTime(measValueSO.getTime());
        tmp.setCv(measValueSO.getCv());
        measObjSOMapper.update(tmp);
        measValueSOMapper.addMeasValueSO(measValueSO);
        // LogUtil.info("after insert MeasObjSO : " + tmp);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        // HashMap在put的时候指定初始化大小，避免put元素时数组扩容，扩容时需要重新计算元素下标并转移到新数组，是个耗时的过程
        measObjHashMap = new HashMap<>();
        measObjAIHashMap = new HashMap<>();
        measObjDIHashMap = new HashMap<>();
        measObjSIHashMap = new HashMap<>();
        measObjSOHashMap = new HashMap<>();
        measObjDistributeHashMap = new HashMap<>();

        List<MeasObj> measObjs = measObjMapper.getAllMeasObjList();
        for (MeasObj measObj : measObjs) {
            measObjHashMap.put(measObj.getId(), measObj);
        }
        List<MeasObjAI> measObjAIS = measObjAIMapper.getAllMeasObjAIs();
        for (MeasObjAI tmp : measObjAIS) {
            if (tmp.getCv() == null) tmp.setCv(0.0);
            measObjAIHashMap.put(tmp.getId(), tmp);
        }
        List<MeasObjDI> measObjDIS = measObjDIMapper.getAllMeasObjDIs();
        for (MeasObjDI tmp : measObjDIS) {
            measObjDIHashMap.put(tmp.getId(), tmp);
        }
        List<MeasObjSI> measObjSIs = measObjSIMapper.getAllMeasObjSIs();
        for (MeasObjSI tmp : measObjSIs) {
            if (tmp.getCv() == null) tmp.setCv(0);
            measObjSIHashMap.put(tmp.getId(), tmp);
        }
        List<MeasObjSO> measObjSOs = measObjSOMapper.getAllMeasObjSOs();
        for (MeasObjSO tmp : measObjSOs) {
            measObjSOHashMap.put(tmp.getId(), tmp);
        }

        List<MeasObjDistribute> measObjDistributes = measObjDistributeMapper.getAllMeasObjDistributes();
        for (MeasObjDistribute tmp : measObjDistributes) {
            measObjDistributeHashMap.put(tmp.getId(), tmp);
        }
    }

    private void insertObj2OwnDB(MeasObj measObj) {
        DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());

        switch (dataType) {
            case AI:
                MeasObjAI measObjAI = MeasObjAI.fromMeasObj(measObj);
                measObjAI.setRefreshTime(new Date());
                measObjAIMapper.insertSelective(measObjAI);
                break;

            case DI:
                MeasObjDI measObjDI = MeasObjDI.fromMeasObj(measObj);
                measObjDI.setRefreshTime(new Date());
                measObjDIMapper.insertSelective(measObjDI);
                break;

            case SI:
                MeasObjSI measObjSI = MeasObjSI.fromMeasObj(measObj);
                measObjSIMapper.insertSelective(measObjSI);
                break;

            case SO:
                MeasObjSO measObjSO = MeasObjSO.fromMeasObj(measObj);
                measObjSOMapper.insertSelective(measObjSO);
                break;

            case DISTRIBUTE:
                MeasObjDistribute measObjDistribute = MeasObjDistribute.fromMeasObj(measObj);
                measObjDistributeMapper.insertSelective(measObjDistribute);
                break;

            case VIDEO:
                Video video = Video.fromMeasObj(measObj);
                video.setServerId(1);
                video.setVideoSceneId(0);
                video.setVideoExtendSceneId(0);
                video.setChannelNo(1);
                videoModuleCenter.insertVideo2DB(video);
                break;

            case ComplexObject:             // 复杂结构，需要将他的孩子存到对应的数据库

                ComplexObjectConvert complexObjectConvert = getComplexObjectConvertByMeasObj(measObj);
                if (complexObjectConvert == null) return;
                for (ConvertType convertType : complexObjectConvert.getConvertTypes()) {

                    MeasObj tmpObj = new MeasObj();
                    // 将ID换成正常的ID，这里简单处理了下，以后还需正规
                    tmpObj.setId(getConvertObjectId(measObj, convertType));
                    tmpObj.setName(measObj.getName() + "_" + convertType.getDescribe());
                    tmpObj.setActived(true);
                    tmpObj.setTunnelId(measObj.getTunnelId());
                    tmpObj.setAreaId(measObj.getAreaId());
                    tmpObj.setStoreId(measObj.getStoreId());
                    tmpObj.setSectionId(measObj.getSectionId());
                    tmpObj.setObjtypeId(ObjectType.NONE.getValue());
                    tmpObj.setDatatypeId(getDataTypeByConvertType(convertType));

                    // 将tmpObj塞进数据库,DO就不塞了，或者后期加上
                    insertMeasObj(tmpObj);
                }

                break;

            default:
                break;
        }
    }

    private void insertObj2OwnHashMap(MeasObj measObj) {
        DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
        switch (dataType) {
            case AI:
                MeasObjAI measObjAI = MeasObjAI.fromMeasObj(measObj);
                measObjAIHashMap.put(measObjAI.getId(), measObjAI);
                break;

            case DI:
                MeasObjDI measObjDI = MeasObjDI.fromMeasObj(measObj);
                measObjDIHashMap.put(measObjDI.getId(), measObjDI);
                break;

            case SI:
                MeasObjSI measObjSI = MeasObjSI.fromMeasObj(measObj);
                measObjSIHashMap.put(measObjSI.getId(), measObjSI);
                break;

            case SO:
                MeasObjSO measObjSO = MeasObjSO.fromMeasObj(measObj);
                measObjSOHashMap.put(measObjSO.getId(), measObjSO);
                break;

            case DISTRIBUTE:
                MeasObjDistribute measObjDistribute = MeasObjDistribute.fromMeasObj(measObj);
                measObjDistributeHashMap.put(measObjDistribute.getId(), measObjDistribute);
                break;

            case VIDEO:
                Video video = Video.fromMeasObj(measObj);
                video.setServerId(1);
                video.setVideoSceneId(0);
                video.setVideoExtendSceneId(0);
                video.setChannelNo(1);
                videoModuleCenter.insertVideo2HashMap(video);
                break;


            case ComplexObject:             // 复杂结构，需要将他的孩子存到对应的缓存中
//                ComplexObjectConvert complexObjectConvert = getComplexObjectConvertByMeasObj(measObj);
//                if (complexObjectConvert == null) return;
//                for (ConvertType convertType : complexObjectConvert.getConvertTypes()) {
//
//                    MeasObj tmpObj = new MeasObj();
//                    // 将ID换成正常的ID，这里简单处理了下，以后还需正规
//                    tmpObj.setId(getConvertObjectId(measObj, convertType));
//                    tmpObj.setName(measObj.getName() + "_" + convertType.getDescribe());
//                    tmpObj.setActived(true);
//                    tmpObj.setTunnelId(measObj.getTunnelId());
//                    tmpObj.setAreaId(measObj.getAreaId());
//                    tmpObj.setStoreId(measObj.getStoreId());
//                    tmpObj.setSectionId(measObj.getSectionId());
//                    tmpObj.setObjtypeId(ObjectType.NONE.getValue());
//                    tmpObj.setDatatypeId(getDataTypeByConvertType(convertType));
//
//                    // 将tmpObj塞进数据库,DO就不塞了，或者后期加上
//                    insertObj2OwnHashMap(tmpObj);
//                }

                break;

            default:
                break;
        }
    }

    /**
     * 获取复杂类型的转换内容
     *
     * @param measObj
     * @return
     */
    public ComplexObjectConvert getComplexObjectConvertByMeasObj(MeasObj measObj) {
        if (measObj.getDatatypeId().intValue() != DataType.ComplexObject.getValue()) return null;
        Config config = xmlService.getXMLAllInfo();
        List<ComplexObjectConvert> complexObjectConverts = config.getComplexObjectConverts();
        ComplexObjectConvert complexObjectConvert = complexObjectConverts.stream().filter(a -> a.getObjectType() == measObj.getObjtypeId().intValue()).findFirst().get();
        return complexObjectConvert;
    }

    public int getConvertObjectId(MeasObj measObj, ConvertType convertType) {
        return measObj.getId().intValue() / 100 * 100 + convertType.getConvertId();
    }

    /**
     * 根据转换的类型来获取对应的DataType
     *
     * @param convertType
     * @return
     */
    private int getDataTypeByConvertType(ConvertType convertType) {
        switch (convertType.getType()) {
            case 0:             // 开光量输入DI
                return DataType.DI.getValue();

            case 1:             // 开光量输出DO
                return DataType.DO.getValue();

            case 2:             // 模拟量输入AI
                return DataType.AI.getValue();

            default:
                return 0;
        }
    }

    @Override
    public void start() {
        initData();
    }

    @Override
    public void stop() {

        // GC垃圾回收
        measObjHashMap = null;
        measObjAIHashMap = null;
        measObjDIHashMap = null;
        measObjSIHashMap = null;
        measObjSOHashMap = null;
        measObjDistributeHashMap = null;
    }

    public void deleteObj(Integer id) {

        // delete Cache
        MeasObj measObj = measObjHashMap.get(id);
        if (measObj == null)
            return;
        measObjHashMap.remove(id);
        deleteObjFromOwnHashMap(measObj);

        // delete DB
        measObjMapper.deleteByPrimaryKey(id);
        deleteObjFromOwnDB(measObj);

    }

    private void deleteObjFromOwnDB(MeasObj measObj) {
        DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
        switch (dataType) {
            case AI:
                measObjAIMapper.deleteByPrimaryKey(measObj.getId());
                break;

            case DI:
                measObjDIMapper.deleteByPrimaryKey(measObj.getId());
                break;

            case SI:
                measObjSIMapper.deleteByPrimaryKey(measObj.getId());
                break;

            case SO:
                measObjSOMapper.deleteByPrimaryKey(measObj.getId());
                break;

            case DISTRIBUTE:
                measObjDistributeMapper.deleteByPrimaryKey(measObj.getId());
                break;

            case VIDEO:
                // TODO
                break;

            case ComplexObject:

                ComplexObjectConvert complexObjectConvert = getComplexObjectConvertByMeasObj(measObj);

                if (complexObjectConvert == null) return;
                for (ConvertType convertType : complexObjectConvert.getConvertTypes()) {
                    MeasObj tmpObj = new MeasObj();
                    // 将ID换成正常的ID，这里简单处理了下，以后还需正规
                    tmpObj.setId(getConvertObjectId(measObj, convertType));
                    tmpObj.setDatatypeId(getDataTypeByConvertType(convertType));


                    deleteObjFromOwnDB(tmpObj);
                }

                break;

            default:
                break;
        }

    }

    private void deleteObjFromOwnHashMap(MeasObj measObj) {

        DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
        switch (dataType) {
            case AI:
                measObjAIHashMap.remove(measObj.getId());
                break;

            case DI:
                measObjDIHashMap.remove(measObj.getId());
                break;

            case SI:
                measObjSIHashMap.remove(measObj.getId());
                break;

            case SO:
                measObjSOHashMap.remove(measObj.getId());
                break;

            case DISTRIBUTE:
                measObjDistributeHashMap.remove(measObj.getId());
                break;

            case VIDEO:
                // TODO
                break;

            case ComplexObject:
                ComplexObjectConvert complexObjectConvert = getComplexObjectConvertByMeasObj(measObj);

                if (complexObjectConvert == null) return;
                for (ConvertType convertType : complexObjectConvert.getConvertTypes()) {
                    MeasObj tmpObj = new MeasObj();
                    // 将ID换成正常的ID，这里简单处理了下，以后还需正规
                    tmpObj.setId(getConvertObjectId(measObj, convertType));
                    tmpObj.setDatatypeId(getDataTypeByConvertType(convertType));

                    deleteObjFromOwnHashMap(tmpObj);
                }

                break;

            default:
                break;
        }

    }

}
