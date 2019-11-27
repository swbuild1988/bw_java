package com.bandweaver.tunnel.controller.test;


import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.*;
import com.bandweaver.tunnel.common.biz.itf.common.TunnelLightService;
import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeDataService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.pojo.xml.ComplexObjectConvert;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.dao.mam.MeasValueAIMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
public class TestController {
    @Autowired
    private ConsumeService consumeService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private MeasValueAIMapper measValueAIMapper;
    @Autowired
    private ConsumeDataService consumeDataService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private MeasObjService measObjService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private TunnelLightService tunnelLightService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private XMLService xmlService;
    @Autowired
    private SectionService sectionService;


    /**
     * 测试添加每个管廊的moid和总能耗
     *
     * @return
     */
    @RequestMapping(value = "test/add-consume", method = RequestMethod.GET)
    public JSONObject addTestConsumes() {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        // 循环添加每个管廊的moid（只用于能耗）和能耗
        for (TunnelSimpleDto dto : tunnels) {
            String tmp_id = dto.getId() + "0008";
            MeasObj measObj = new MeasObj();
            measObj.setId(Integer.parseInt(tmp_id));
            measObj.setTunnelId(dto.getId());
            measObj.setAreaId(0);
            measObj.setStoreId(0);
            measObj.setActived(true);
            measObj.setObjtypeId(31);
            measObj.setDatatypeId(1);
            measObj.setName("总电表");
            measObjModuleCenter.insertMeasObj(measObj);

            Consume consume = new Consume();
            consume.setTunnelId(dto.getId());
            consume.setAreaId(0);
            consume.setStoreId(0);
            consume.setObjectType(0);
            consume.setEnergyType(0);
            consume.setObjectId(measObj.getId());
            consumeService.insert(consume);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 每八小时查看一次电表，并将数值保存到measValueAI中
     *
     * @return
     */
    @RequestMapping(value = "test/add-value", method = RequestMethod.GET)
    public JSONObject addTestValue() {
        // 设置存储数据的开始和结束时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = null;
        Date ed = null;
        try {
            sd = sdf.parse("2018-6-1");
            ed = sdf.parse("2019-6-1");
        } catch (Exception e) {

        }

        // 获取每个管廊的用于能耗的mo
        MeasObjVo vo = new MeasObjVo();
        vo.setObjtypeId(31);
        vo.setAreaId(0);
        vo.setStoreId(0);
        vo.setSectionId(0);
        List<MeasObjDto> objList = measObjService.getMeasObjByCondition(vo);
        // 初始化电表值
        List<Double> cvList = new ArrayList<>();
        // 获取管廊信息
        List<TunnelDto> tunnelList = new ArrayList<>();
        for (int i = 0; i < objList.size(); i++) {
            cvList.add(0.0);
            TunnelDto dto = tunnelService.getDtoById(objList.get(i).getTunnelId());
            tunnelList.add(dto);
        }

        while (sd.before(ed)) {
            //LogUtil.info("before add time : " + sd);
            // 遍历每个管廊的mo，保存数据
            for (int i = 0; i < objList.size(); i++) {
                // 获取[8,12]随机数
                double random = Math.random() * 4 + 8;
                double sum = random * tunnelList.get(i).getLength() / 100 + cvList.get(i);
                // 保留两位小数
                BigDecimal bg = new BigDecimal(sum);
                sum = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                // 保存电表读数
                cvList.set(i, sum);
                LogUtil.info("cvList.get(" + i + ") : " + cvList.get(i));

                MeasValueAI measValueAI = new MeasValueAI();
                measValueAI.setTime(sd);
                measValueAI.setObjectId(objList.get(i).getId());
                measValueAI.setCv(sum);
                measValueAIMapper.addMeasValueAI(measValueAI);
            }

            sd = new Date(sd.getTime() + 8 * 60 * 60 * 1000);
            //LogUtil.info("after add time : " + sd);
        }


        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 查看每天的能耗，并保存到consumeData表中
     *
     * @return
     */
    @RequestMapping(value = "test/add-consumedata", method = RequestMethod.GET)
    public JSONObject addTestConsumeData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = null;
        Date ed = null;
        try {
            sd = sdf.parse("2018-6-2");
            ed = sdf.parse("2019-6-2");
        } catch (Exception e) {

        }
        while (sd.before(ed)) {
            getDatas(sd);
            sd = new Date(sd.getTime() + 24 * 60 * 60 * 1000);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    private void getDatas(Date time) {
        // 获取昨天开始时间
        Date startTime = DateUtil.getDayStartTime(DateUtil.getFrontDay(time, 1));
        // 获取今天的开始时间
        Date endTime = DateUtil.getDayStartTime(time);

        // 获取consume表所有电表(包括不存在的电表)
        List<ConsumeDto> consumes = consumeService.getConsumes();
        // for循环插入数据
        for (ConsumeDto dto : consumes) {
            ConsumeData data = new ConsumeData();
            data.setId(dto.getId());
            data.setTime(startTime);
            Double sum = 0.0;
            // 判断是否存在电表
            if (dto.getObjectId() != null) {
                List<MeasValueAI> ais = measValueAIMapper.getListByObjectIdAndTime(dto.getObjectId(), startTime, endTime);
                //统计aiValue表中某一电表一天内的能耗
                if (ais.size() > 0)
                    sum = MathUtil.sub(ais.get(0).getCv(), ais.get(ais.size() - 1).getCv());
                data.setDirect(sum);
            } else if (dto.getCompute() != null) {
                String[] s = dto.getCompute().split(",");
                //统计没有电表时一天内的合计能耗
                for (String ss : s) {
                    List<MeasValueAI> ais = measValueAIMapper.getListByObjectIdAndTime(Integer.valueOf(ss), startTime, endTime);
                    //统计aiValue表中某一电表一天内的能耗
                    if (ais.size() > 0) {
                        Double sum1 = MathUtil.sub(ais.get(0).getCv(), ais.get(ais.size() - 1).getCv());
                        sum = MathUtil.add(sum, sum1);
                    }
                }
                data.setIndirect(sum);
            }
            //添加
            consumeDataService.insert(data);
        }
    }

    /**
     * 手动添加告警
     * @param level 告警等级，值为1,2,3,4
     */
    @RequestMapping(value = "test/add_alarm/{level}", method = RequestMethod.GET)
    public JSONObject sendMQMessage(@PathVariable("level") Integer level) {
        Alarm alarm = new Alarm();
        alarm.setAlarmDate(new Date());

        //int i = MathUtil.getRandomInt(1, 4);
        alarm.setAlarmLevel(level);
        alarm.setAlarmName("温度测试告警");
        alarm.setObjectId(222032401);
        alarm.setObjectName("温度检测仪");
        alarm.setTunnelId(1);
        alarm.setAlarmSource("");
        alarm.setCleaned(false);
        alarm.setIsDistribute(false);
        //alarm.setCleanedDate(new Date());
        alarm.setDescription("");
        alarm.setLatitude("");
        alarm.setLongitude("");
        alarmService.add(alarm);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 手动添加告警
     * @param level 告警级别
     * @param tunnelId 管廊
     * @param areaId 区域
     * @param storeId 管仓
     * @param objtypeId 对象类型
     * @return
     * @author ya.liu
     * @Date 2019年9月12日
     */
    @RequestMapping(value = "test/add_alarm/{level}", method = RequestMethod.POST)
    public JSONObject addAlarms(@PathVariable("level") Integer level, @RequestBody MeasObjVo vo) {
    	List<MeasObjDto> list = measObjService.getMeasObjByCondition(vo);
    	if(list == null || list.size() < 1)
    		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, "该区域下没有符合条件的监测对象！");
    	// 随机获取
    	int i = MathUtil.getRandomInt(1, list.size()) - 1;
    	Alarm alarm = new Alarm();
        alarm.setAlarmDate(new Date());
        alarm.setAlarmLevel(level);
        alarm.setAlarmName(list.get(i).getObjtypeName() + "测试告警");
        alarm.setObjectId(list.get(i).getId());
        alarm.setObjectName(list.get(i).getName());
        alarm.setTunnelId(vo.getTunnelId());
        alarm.setAlarmSource("");
        alarm.setCleaned(false);
        alarm.setIsDistribute(false);
        alarm.setDescription("");
        alarm.setLatitude("");
        alarm.setLongitude("");
        alarmService.add(alarm);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /*
     * 添加管廊光源
     */
    @RequestMapping(value = "test/add_lights", method = RequestMethod.GET)
    public JSONObject createTunnelLights() {
        int tunnelId = 1;
        List<Integer> storeIds = new ArrayList<>();
        storeIds.add(1014);

        for (int i = 0; i < storeIds.size(); i++) {
            tunnelLightService.createTunnelLights(tunnelId, null, storeIds.get(i));
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /*
     * 添加设备
     */
    @RequestMapping(value = "test/add_equipments", method = RequestMethod.GET)
    public JSONObject addEquipments() {
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();
        for (MeasObj measObj : measObjs) {
            Equipment equipment = new Equipment();
            equipment.setName(measObj.getName());
            // 设定设备类型
            int typeId = 0;
            int modelId = 0;
            if (measObj.getObjtypeId() == null) continue;
            switch (ObjectType.getEnum(measObj.getObjtypeId())) {
                case CO:
                case CH4:
                case H2S:
                case OXYGEN:
                case TEMPERATURE:
                case HUMIDITY:
                case LIQUID:
                    typeId = 3;
                    modelId = 4;
                    break;

                case ALTEROR:
                case ENTRANCE_GUARD:
                case ELECTRONIC_COVERS:
                case INFRARED:
                    typeId = 1;
                    modelId = 1;
                    break;

                case VIDEO:
                    typeId = 2;
                    modelId = 3;
                    break;

                case BLINDS:
                case PUMP:
                case FAN:
                    typeId = 4;
                    modelId = 21;
                    break;

                default:
                    continue;
            }
            equipment.setType(typeId);
            equipment.setAssetNo("SN_" + measObj.getId().toString());
            equipment.setCrtTime(new Date());
            equipment.setRunTime(new Date());
            equipment.setAlarmNo(0);
            equipment.setStatus(1);
            equipment.setTunnelId(measObj.getTunnelId());
            equipment.setVenderId(3);
            equipment.setModelId(modelId);
            equipment.setObjId(measObj.getId());
            equipment.setSectionId(measObj.getSectionId());
            equipment.setQaTerm("11");
            equipment.setRatedVoltage("220V");
            equipment.setRatedVoltage("11");
            equipment.setFactory("11");
            equipment.setBrand("11");
            equipmentService.addEquipment(equipment);
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    
    /*
     * 
     */
    @RequestMapping(value = "test/change_measobjs")
    public JSONObject changeMeasObj() {

        List<ComplexObjectConvert> complexObjectConverts = xmlService.getXMLAllInfo().getComplexObjectConverts();

        for (ComplexObjectConvert complexObjectConvert : complexObjectConverts) {
            ObjectType objectType = ObjectType.getEnum(complexObjectConvert.getObjectType());

            // 获取所有的类型为objectType的对象
            List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs().stream().filter(a -> a.getObjtypeId().intValue() == objectType.getValue()).collect(Collectors.toList());
            for (MeasObj measObj : measObjs) {

                if (measObj.getDatatypeId() == DataType.ComplexObject.getValue()) continue;

                MeasObj tmpObj = new MeasObj();
                // 先复制一份备份
                BeanUtils.copyProperties(measObj, tmpObj);
                // 设为复杂结构
                tmpObj.setDatatypeId(DataType.ComplexObject.getValue());

                measObjModuleCenter.deleteObj(measObj.getId());
                measObjModuleCenter.insertMeasObj(tmpObj);
            }
        }
        
        // 修改声光报警、红外、照明的datatypeId 3->2
        // 获取声光报警和红外的监测对象
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs().stream().filter(a -> a.getObjtypeId().intValue() == ObjectType.ALTEROR.getValue()
        		|| a.getObjtypeId().intValue() == ObjectType.INFRARED.getValue()
        		|| a.getObjtypeId().intValue() == ObjectType.LIGHT.getValue()).collect(Collectors.toList());
        for (MeasObj measObj : measObjs) {

        	if(measObj.getDatatypeId() == DataType.DI.getValue()) continue;
            
        	MeasObj tmpObj = new MeasObj();
            // 先复制一份备份
            BeanUtils.copyProperties(measObj, tmpObj);
            // 设为复杂结构
            tmpObj.setDatatypeId(DataType.DI.getValue());

            measObjModuleCenter.deleteObj(measObj.getId());
            measObjModuleCenter.insertMeasObj(tmpObj);
        }
        
        
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /*
     * 删除监测对象
     */
    @RequestMapping(value = "test/delete_measobjs")
    public JSONObject deleteMeasObj() {
    	// 获取所有监测对象
    	List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();
    	
    	for (MeasObj measObj : measObjs) {
    		// 去除监控中心的视频以及定位设备
    		if(measObj.getId().intValue() < 7000000) continue;
    		measObjModuleCenter.deleteObj(measObj.getId());
    	}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    // 去除区域和舱室前缀(如：实验路-3区->3区)
    @RequestMapping(value = "test/update_area_store")
    public JSONObject updateAreaAndStore() {
    	// 获取所有区
    	List<AreaDto> areas = areaService.getList();
    	for(AreaDto dto : areas) {
    		String [] strs = dto.getName().split("-");
    		if(strs.length < 2) continue;
    		dto.setName(strs[1]);
    		areaService.update(dto);
    	}
    	// 获取所有舱
    	List<Store> stores = storeService.getList();
    	for(Store dto : stores) {
    		String [] strs = dto.getName().split("-");
    		if(strs.length < 2) continue;
    		dto.setName(strs[1]);
    		storeService.update(dto);
    	}
    	// 获取所有section
    	List<SectionDto> sections = sectionService.getAllSections();
    	for(SectionDto dto : sections) {
    		String areaName = areaService.getAreasById(dto.getAreaId()).getName();
    		String storeName = storeService.getStoreById(dto.getStoreId()).getName();
    		dto.setName(areaName + "-" + storeName);
    		sectionService.update(dto);
    	}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
}
