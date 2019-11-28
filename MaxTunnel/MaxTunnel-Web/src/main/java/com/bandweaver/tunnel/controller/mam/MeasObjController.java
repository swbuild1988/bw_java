package com.bandweaver.tunnel.controller.mam;

import java.util.*;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.MonitorTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.SubSystemService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDI;
import com.bandweaver.tunnel.common.biz.pojo.xml.ComplexObjectConvert;
import com.bandweaver.tunnel.common.biz.pojo.xml.ConvertType;
import com.bandweaver.tunnel.common.biz.pojo.xml.ObjTypeParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueSIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
import com.bandweaver.tunnel.common.biz.vo.StoreVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**
 * 监测对象管理
 *
 * @author shaosen
 * @date 2018年12月22日
 */
@Controller
@ResponseBody
public class MeasObjController {
    @Autowired
    private MeasObjService measObjService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private MeasValueAIService measValueAIService;
    @Autowired
    private MeasValueSIService measValueSIService;
    @Autowired
    private XMLService xmlService;
    @Autowired
    private SubSystemService subSystemService;
    @Autowired
    private EquipmentService equipmentService;


    /**
     * 添加measObj
     *
     * @param name        名称 String
     * @param tunnelId    所在管廊
     * @param storeId     所在管仓
     * @param areaId      所在区域
     * @param objtypeId
     * @param actived
     * @param description 描述
     * @param longitude   经度 String
     * @param latitude    纬度 String
     * @param height      高度 String
     * @param deviation   偏移量 String
     * @return
     * @author shaosen
     * @date 2018年5月28日
     */
    @RequiresPermissions("measobj:add")
    @RequestMapping(value = "measobjs", method = RequestMethod.POST)
    public JSONObject addObj(@RequestBody MeasObj obj) {
        measObjModuleCenter.insertMeasObj(obj);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * ajax检验监测对象ID是否重复
     *
     * @param id
     * @return
     * @author shaosen
     * @Date 2018年11月21日
     */
    @RequestMapping(value = "measobjs/{id}/ajax", method = RequestMethod.GET)
    public JSONObject ajax(@PathVariable Integer id) {
        MeasObj measObj = measObjModuleCenter.getMeasObj(id);
        boolean result = measObj == null ? true : false;
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }


    /**
     * 批量添加
     *
     * @param list
     * @return JSONObject
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequiresPermissions("measobj:add")
    @RequestMapping(value = "measobjs/batch", method = RequestMethod.POST)
    public JSONObject addObjBatch(@RequestBody List<MeasObj> list) {
        for (MeasObj obj : list) {
            measObjModuleCenter.insertMeasObj(obj);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 更新obj
     *
     * @param obj
     * @return
     * @author shaosen
     * @Date 2018年9月3日
     */
    @RequiresPermissions("measobj:update")
    @RequestMapping(value = "measobjs", method = RequestMethod.PUT)
    public JSONObject editObj(@RequestBody MeasObj obj) {
        measObjModuleCenter.updateMeasObj(obj);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 批量更新obj
     *
     * @param obj
     * @return
     * @author ya.liu
     * @Date 2019年7月31日
     */
    @RequiresPermissions("measobj:update")
    @RequestMapping(value = "measobjs/batch", method = RequestMethod.PUT)
    public JSONObject editObjBatch(@RequestBody List<MeasObj> list) {
        for (MeasObj obj : list) {
            measObjModuleCenter.updateMeasObj(obj);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 根据id查询obj
     *
     * @param id objID
     * @return
     * @author shaosen
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "measobjs/{id}", method = RequestMethod.GET)
    public JSONObject getObj(@PathVariable("id") Integer id) {
        MeasObj measObj = measObjModuleCenter.getMeasObj(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, measObj);
    }


    /**
     * 删除MeasObj
     *
     * @param id
     * @return
     * @author shaosen
     * @Date 2018年11月8日
     */
    @RequiresPermissions("measobj:delete")
    @RequestMapping(value = "measobjs/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteObj(@PathVariable Integer id) {
        measObjModuleCenter.deleteObj(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @author shaosen
     * @Date 2018年11月8日
     */
    @RequiresPermissions("measobj:delete")
    @RequestMapping(value = "measobjs/batch/{ids}", method = RequestMethod.DELETE)
    public JSONObject deleteObjBatch(@PathVariable String ids) {

        String[] arr = ids.split(",");
        for (String str : arr) {
            measObjModuleCenter.deleteObj(DataTypeUtil.toInteger(str));
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 监测对象分页查询
     *
     * @param storeId    管舱id
     * @param areaId     区域id
     * @param tunnelId   管廊id
     * @param name       监测对象名称（支持模糊查询）
     * @param datatypeId 枚举
     * @param objtypeIds list，格式：[1,2,3,4...]
     * @param pageNum    必须
     * @param pageSize   必须
     * @return {"msg":"请求成功","code":"200","data":{"total":1,"list":[{"id":8009,"tunnelId":3,"storeId":3,"areaId":1,"sectionId":42,"name":"风机类电表","datatypeId":1,"datatypeName":"模拟量输入","objtypeId":31,"objtypeName":"风机类电表","actived":true,"description":null,"longitude":"0.4386016987524316","latitude":"0.48531866384309663","height":"0.6030770379809333","deviation":0.28828624319183,"cv":101.476279675469,"valueAIList":null,"section":{"id":42,"name":"20区-污水仓","tunnelId":null,"totalCableNumber":5,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1535611491000,"store":{"id":4,"name":"污水仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":4,"name":"污水仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":20,"name":"20区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}}],"pageNum":1,"pageSize":5,"size":1,"startRow":1,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
     * @author shaosen
     * @date 2018年7月18日
     */
    @RequiresPermissions("measobj:list")
    @RequestMapping(value = "measobjs/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody MeasObjVo vo) {
        PageInfo<MeasObjDto> pageInfo = measObjService.dataGrid(vo);

        List<MeasObjDto> objDtoList = pageInfo.getList();
        for (MeasObjDto measObjDto : objDtoList) {
            double cv = measObjService.getMeasObjCVByIdAndDataType(measObjDto.getId(), measObjDto.getDatatypeId());
            measObjDto.setCv(cv);

            // 设置预案显示
            List<String> plansName = new ArrayList<>(10);
            String planIds = measObjDto.getPlanIds();
            if (planIds != null && planIds.length() > 0) {
                List<Integer> planIdList = CommonUtil.convertStringToList(planIds);
                for (Integer planId : planIdList) {
                    String name = ProcessTypeEnum.getEnum(planId).getName();
                    plansName.add(name);
                }
            }
            measObjDto.setPlansName(plansName);

            // 设置视频名称显示
            List<String> videosName = new ArrayList<>(10);
            String videoIds = measObjDto.getVideoIds();
            if (videoIds != null && videoIds.length() > 0) {
                List<Integer> videoIdList = CommonUtil.convertStringToList(videoIds);
                for (Integer videoId : videoIdList) {
                    String videoName = measObjModuleCenter.getMeasObj(videoId).getName();
                    videosName.add(videoName);
                }
            }
            measObjDto.setVideosName(videosName);

        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }


    /**
     * 环境监测和安防监测接口
     *
     * @param tunnelId  必填
     * @param storeId   可选
     * @param areaId    可选
     * @param objtypeId 单选
     * @return {"msg":"请求成功","code":"200","data":[{"area":"10区","minValue":10.0,"maxValue":40.0,"name":"温度检测仪","id":210012401,"store":"污水仓","curValue":28.81},{"area":"10区","minValue":10.0,"maxValue":40.0,"name":"温度检测仪","id":210022401,"store":"电力舱","curValue":14.7},{"area":"10区","minValue":10.0,"maxValue":40.0,"name":"温度检测仪","id":210022402,"store":"电力舱","curValue":12.99},{"area":"10区","minValue":10.0,"maxValue":40.0,"name":"温度检测仪","id":210032401,"store":"综合仓","curValue":7.24},{"area":"10区","minValue":10.0,"maxValue":40.0,"name":"温度检测仪","id":210042401,"store":"燃气舱","curValue":26.58},{"area":"10区","minValue":10.0,"maxValue":40.0,"name":"温度检测仪","id":210052401,"store":"设备间","curValue":16.67}]}
     * @author shaosen
     * @Date 2018年12月22日
     */
    @RequiresPermissions("measobj:list")
    @RequestMapping(value = "measobjs/list", method = RequestMethod.POST)
    public JSONObject getObjectListByCondition(@RequestBody JSONObject reqJson) {
        CommonUtil.hasAllRequired(reqJson, "tunnelId");
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();
        Integer tunnelId = reqJson.getInteger("tunnelId");
        Integer storeId = reqJson.getInteger("storeId");
        Integer areaId = reqJson.getInteger("areaId");
        Integer objtypeId = reqJson.getInteger("objtypeId");

        if (tunnelId != null) {
            measObjs = measObjs.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
        }
        if (storeId != null) {
            measObjs = measObjs.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
        }
        if (areaId != null) {
            measObjs = measObjs.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());
        }
        if (objtypeId != null) {
            measObjs = measObjs.stream().filter(a -> a.getObjtypeId().intValue() == objtypeId.intValue()).collect(Collectors.toList());
        }
        return CommonUtil.success(getMeasObjInfo(measObjs));
    }

    @RequiresPermissions("measobj:list")
    @RequestMapping(value = "measobjs/{id}/curValues", method = RequestMethod.GET)
    public JSONObject getObjectValueById(@PathVariable("id") int id) {
        MeasObj measObj = measObjModuleCenter.getMeasObj(id);

        // 获取所有孩子的的值并刷新返回
        refreshMeasObjInfo(measObj);

        List<MeasObj> measObjs = new ArrayList<>();
        measObjs.add(measObj);
        List<JSONObject> jsonObjects = getMeasObjInfo(measObjs);
        return CommonUtil.success(jsonObjects.get(0));
    }

    /**
     * 通过管舱以及父类、区段获取监测对象
     *
     * @param storeId
     * @param areaId
     * @return
     * @author ya.liu
     * @Date 2019年5月23日
     */
    @RequiresPermissions("measobj:list")
    @RequestMapping(value = "stores/{storeId}/areas/{areaId}/measobjs", method = RequestMethod.GET)
    public JSONObject getObjectListByStoreIdAndAreaId(@PathVariable("storeId") Integer storeId, @PathVariable("areaId") Integer areaId) {
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();
        // 去除相机
        measObjs = measObjs.stream().filter(a -> !a.getDatatypeId().equals(DataType.VIDEO.getValue())).collect(Collectors.toList());
        if (storeId != null) {
            List<Integer> storeIds = new ArrayList<>();
            StoreDto store = storeService.getStoreById(storeId);
            if (storeId.equals(store.getParentId())) {
                StoreVo vo = new StoreVo();
                vo.setParentId(storeId);
                List<StoreDto> storeList = storeService.getStoresByCondition(vo);
                storeList.forEach(a -> storeIds.add(a.getId()));
            } else
                storeIds.add(storeId);
            measObjs = measObjs.stream().filter(a -> storeIds.contains(a.getStoreId())).collect(Collectors.toList());
        }
        if (areaId != null) {
            measObjs = measObjs.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());
        }
        return CommonUtil.success(getMeasObjInfo(measObjs));
    }

    private boolean refreshMeasObjInfo(MeasObj measObj) {

        switch (DataType.getEnum(measObj.getDatatypeId())) {
            case AI:
                MeasValueAI measValueAI = subSystemService.refreshAI(measObj.getId().intValue());
                measObjModuleCenter.updateMeasObjAIValue(measValueAI);
                break;

            case DI:

                MeasValueDI measValueDI = subSystemService.refreshDI(measObj.getId().intValue());
                measObjModuleCenter.updateMeasObjDIValue(measValueDI);
                break;

            case SI:

                break;

            case SO:

                break;

            case ComplexObject:
                ComplexObjectConvert complexObjectConvert = measObjModuleCenter.getComplexObjectConvertByMeasObj(measObj);
                for (ConvertType convertType : complexObjectConvert.getConvertTypes()) {
                    if (convertType.getType() == 0) {                //开光量输入
                        MeasObj tmpObj = new MeasObj();
                        tmpObj.setDatatypeId(DataType.DI.getValue());
                        tmpObj.setId(measObjModuleCenter.getConvertObjectId(measObj, convertType));
                        refreshMeasObjInfo(tmpObj);

                    } else if (convertType.getType() == 2) {             //模拟量输入
                        MeasObj tmpObj = new MeasObj();
                        tmpObj.setDatatypeId(DataType.AI.getValue());
                        tmpObj.setId(measObjModuleCenter.getConvertObjectId(measObj, convertType));
                        refreshMeasObjInfo(tmpObj);

                    }
                }
                break;

            default:

                break;
        }


        return true;
    }

    // 获取监测对象值
    private List<JSONObject> getMeasObjInfo(List<MeasObj> measObjs) {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        List<AreaDto> areas = areaService.getList();
        List<Store> stores = storeService.getList();

        List<JSONObject> returnData = new ArrayList<>();
        Integer decimal = xmlService.getXMLAllInfo().getDecimal();
        for (MeasObj measObjDto : measObjs) {

            JSONObject json = getMeasObjCurValue(measObjDto, tunnels, areas, stores, decimal);

            returnData.add(json);
        }
        return returnData;
    }

    /**
     * 求管廊环境监测数据极值
     *
     * @param reqJson
     * @return
     * @author shaosen
     * @Date 2018年12月28日
     */
    @RequestMapping(value = "measobjs/max-cv", method = RequestMethod.POST)
    public JSONObject getMaxCVByTunnelId(@RequestBody JSONObject reqJson) {

        Integer tunnelId = reqJson.getInteger("tunnelId");
        Integer storeId = reqJson.getInteger("storeId");
        Integer areaId = reqJson.getInteger("areaId");
        int monitorType = MonitorTypeEnum.ENVIRONMENTAL.getValue();

        List<JSONObject> list = measObjService.getMeasObjMaxOrMinValue(tunnelId, storeId, areaId, monitorType);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);

    }


    /**
     * 根据id集合获取list
     *
     * @param ids
     * @return
     * @author shaosen
     * @date 2018年6月8日
     */
    @RequestMapping(value = "/measobjs/ids", method = RequestMethod.POST)
    public JSONObject getObjByIds(@RequestBody Map<String, Object> map) {
        List<Integer> idList = CommonUtil.convertStringToList(DataTypeUtil.toString(map.get("ids")));
        List<MeasObj> list = measObjModuleCenter.getMeasObjsByIds(idList);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**
     * 获取所有obj
     *
     * @return JSONObject
     * @author shaosen
     * @date 2018年6月7日
     */
    @RequestMapping(value = "measobjs", method = RequestMethod.GET)
    public JSONObject getAllMeasObjList() {
        List<MeasObj> list = measObjModuleCenter.getMeasObjs();
        list = list.stream().filter(obj -> !obj.getObjtypeId().equals(0)).collect(Collectors.toList());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 根据经纬度和高度查询50米空间内的所有数据
     *
     * @param longitude
     * @param latitude
     * @param height
     * @return JSONObject
     * @author shaosen
     * @date 2018年6月5日
     */
    @Deprecated
    @RequestMapping(value = "measobjs/gps", method = RequestMethod.POST)
    public JSONObject getMeasObjByLongitudeAndLatitudeAndHeight(@RequestBody Map<String, Object> map) {

        double longitude = DataTypeUtil.toDouble(map.get("longitude"));
        double latitude = DataTypeUtil.toDouble(map.get("latitude"));
        double height = DataTypeUtil.toDouble(map.get("height"));

        //从缓存读取数据
        List<MeasObj> objList = measObjModuleCenter.getMeasObjs();
        List<MeasObj> resultList = new ArrayList<>();

        for (MeasObj measObj : objList) {
            double _longitude = DataTypeUtil.toDouble(measObj.getLongitude());
            double _latitude = DataTypeUtil.toDouble(measObj.getLatitude());
            double _height = DataTypeUtil.toDouble(measObj.getHeight());

            double distance = GPSUtil.GetDistance(longitude, latitude, _longitude, _latitude);
            double differenceHeight = Math.abs(_height - height);
            double a = Math.pow(distance, 2);
            double b = Math.pow(differenceHeight, 2);

            if (a + b <= 50.0 * 50.0) {
                LogUtil.info(" Distance : " + Math.sqrt(a + b));
                resultList.add(measObj);
            }
        }
        LogUtil.info("共返回【" + resultList.size() + "】个坐标");
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, resultList);

    }


    /**
     * 通过管仓和区段获取对象信息
     *
     * @param vo 查询参数areaId，storeId
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "section/measobjs", method = RequestMethod.POST)
    public JSONObject getMeasObjByAreaAndStore(@RequestBody SectionVo vo) {
        Section section = sectionService.getSectionByStoreAndArea(vo.getStoreId(), vo.getAreaId());
        if (section == null) {
            throw new RuntimeException("该仓和区段获取不到对象信息！");
        }
        List<MeasObjDto> ls = measObjService.getMeasObjBySectionId(section.getId());
        List<Map<String, Object>> list = new ArrayList<>();
        for (MeasObjDto obj : ls) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", obj.getId());
            map.put("val", obj.getName());
            list.add(map);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**
     * 获取缓存中温度，甲烷，含氧量最值
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"unit":"℃","name":"最高温度","location":"古城大街-18区-电力舱","value":29.99},{"unit":"ppm","name":"最高甲烷","location":"古城大街-20区-污水舱","value":29.99},{"unit":"%","name":"最低含氧量","location":"古城大街-16区-设备间","value":0.0}]}
     * @author shaosen
     * @date 2019年1月11日
     */
    @RequestMapping(value = "extreme-datas", method = RequestMethod.GET)
    public JSONObject getExtremeDatas() {
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();
        List<MeasObjAI> measObjAIs = measObjModuleCenter.getMeasObjAIs();
        Date currentDate = DateUtil.getCurrentDate();

        JSONObject rt1 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "max", ObjectType.TEMPERATURE);
        JSONObject rt2 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "max", ObjectType.HUMIDITY);
        JSONObject rt3 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "max", ObjectType.CH4);
        JSONObject rt4 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "min", ObjectType.OXYGEN);
        JSONObject rt5 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "max", ObjectType.H2S);
        JSONObject rt6 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "max", ObjectType.LIQUID);

        List<JSONObject> rtdata = new ArrayList<>();
        rtdata.add(rt1);
        rtdata.add(rt2);
        rtdata.add(rt3);
        rtdata.add(rt4);
        rtdata.add(rt5);
        rtdata.add(rt6);

        return CommonUtil.success(rtdata);
    }

    /**
     * 统计复杂对象的情况，供可视化平台
     *
     * @return
     */
    @RequestMapping(value = "complexobj-datas", method = RequestMethod.GET)
    public JSONObject getStatisticsDatasOfComplexObject() {
        // 几种复杂对象ID，包括风机10、井盖56、百叶58、照明12、排水泵
        List<Integer> objTypeIds = Arrays.asList(10, 56, 58, 12, 59);
        List<JSONObject> rtdata = new ArrayList<>();
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();

        for (Integer typeId : objTypeIds) {
            JSONObject tmp_object = new JSONObject();
            tmp_object.put("typeId", typeId.intValue());

            List<MeasObj> tmp_objs = measObjs.stream().filter(a -> a.getObjtypeId().intValue() == typeId.intValue() && a.isActived()).collect(Collectors.toList());

            int open_num = 0;
            int close_num = 0;
            int fault_num = 0;
            int other_num = 0;
            for (MeasObj measObj : tmp_objs) {
                JSONObject cv = measObjModuleCenter.getMeasObjComplexObjectCV(measObj.getId().intValue());
                if (cv == null) continue;

                if (cv.containsKey("fault") && cv.getJSONObject("fault").getBooleanValue("value")) {
                    fault_num += 1;
                } else if (cv.containsKey("fault1") && cv.getJSONObject("fault1").getBooleanValue("value")) {
                    fault_num += 1;
                } else if (cv.containsKey("fault2") && cv.getJSONObject("fault2").getBooleanValue("value")) {
                    fault_num += 1;
                } else if (cv.containsKey("fault3") &&  cv.getJSONObject("fault3").getBooleanValue("value")) {
                    fault_num += 1;
                } else if (cv.containsKey("run") && cv.getJSONObject("run").getBooleanValue("value")) {
                    open_num += 1;
                } else if (cv.containsKey("run") && !cv.getJSONObject("run").getBooleanValue("value")) {
                    close_num += 1;
                } else if (cv.containsKey("open") && cv.getJSONObject("open").getBooleanValue("value")) {
                    open_num += 1;
                } else if (cv.containsKey("close") &&  cv.getJSONObject("close").getBooleanValue("value")) {
                    close_num += 1;
                } else {
                    other_num += 1;
                }
            }

            tmp_object.put("open", open_num);
            tmp_object.put("close", close_num);
            tmp_object.put("fault", fault_num);
            tmp_object.put("other", other_num);

            rtdata.add(tmp_object);
        }

        return CommonUtil.success(rtdata);
    }


    private JSONObject getMaxOrMinValueByObjType(Date currentDate, List<MeasObjAI> measObjAIs, List<MeasObj> measObjs, String maxOrmin, ObjectType objType) {

        Integer decimal = xmlService.getXMLAllInfo().getDecimal();
        JSONObject rtdata = new JSONObject();

        String name = maxOrmin.equals("max") ? "最高" + objType.getName() : "最低含氧量";
        double value = 0;
        String unit = objType.getUnit();
        String location = "无位置信息";
        // 获取最大最小值
        ObjTypeParam objTypeParam = xmlService.getXMLAllInfo().getObjTypeParam(objType);

        //获取所有已在用温度检测对象
        List<MeasObj> temperatureList = measObjs.stream().filter(x -> x.getObjtypeId().intValue() == objType.getValue() && x.isActived()).collect(Collectors.toList());
        //获取所有温度检测对象id
        List<Integer> ids = temperatureList.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<MeasObjAI> collect = measObjAIs.stream().filter(x -> ids.contains(x.getId()))
                .filter(a-> (a.getCv().doubleValue() >= objTypeParam.getMeasMin() && a.getCv().doubleValue() <= objTypeParam.getMeasMax())).collect(Collectors.toList());

        if (!collect.isEmpty()) {

            MeasObjAI measValueAI = maxOrmin.equals("max") ?
                    collect.stream().max(Comparator.comparing(MeasObjAI::getCv)).get()
                    : collect.stream().min(Comparator.comparing(MeasObjAI::getCv)).get();

            //获取位置信息
            location = getLocation(location, measValueAI);

            rtdata.put("name", name);
            rtdata.put("value", measValueAI == null ? value : measValueAI.getCv(decimal));
            rtdata.put("unit", unit);
            rtdata.put("time", measValueAI == null ? currentDate : measValueAI.getRefreshTime());
            rtdata.put("location", location);
            rtdata.put("type", objType.getValue());
            rtdata.put("objTypeParam", objTypeParam);
        }

        return rtdata;

    }


    public String getLocation(String location, MeasObjAI measValueAI) {
        if (measValueAI != null) {
            MeasObj measObj = measObjModuleCenter.getMeasObj(measValueAI.getId());
            if (measObj != null) {
                SectionDto section = sectionService.getSectionById(measObj.getSectionId());
                if (section != null) {
                    location = section.getStore().getTunnel().getName() + section.getName();
                    location = location.replaceAll("-", "");
                }
            }
        }
        return location;
    }


    /**
     * 获取今日监测对象触发次数及与昨日比是否增长
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"unit":"次","name":"电子井盖触发","isRise":false,"value":0},{"unit":"次","name":"门禁触发","isRise":false,"value":0},{"unit":"次","name":"红外触发","isRise":false,"value":0}]}
     * @author shaosen
     * @date 2019年1月11日
     */
    @Deprecated
    @RequestMapping(value = "meas-trigger-counts", method = RequestMethod.GET)
    public JSONObject getMeasTriggerCounts() {

        Date today = DateUtil.getDayBegin();
        Date yesterday = DateUtil.getFrontDay(DateUtil.getCurrentDate(), 1);
        List<MeasValueSI> dbList = measValueSIService.getListByTime(yesterday);
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();

        JSONObject rt1 = getTriggerCountByObjType(today, dbList, measObjs, ObjectType.ELECTRONIC_COVERS);
        JSONObject rt2 = getTriggerCountByObjType(today, dbList, measObjs, ObjectType.ENTRANCE_GUARD);
        JSONObject rt3 = getTriggerCountByObjType(today, dbList, measObjs, ObjectType.INFRARED);

        List<JSONObject> rtdata = new ArrayList<>();
        rtdata.add(rt1);
        rtdata.add(rt2);
        rtdata.add(rt3);
        return CommonUtil.success(rtdata);
    }


    private JSONObject getTriggerCountByObjType(Date today, List<MeasValueSI> dbList, List<MeasObj> measObjs, ObjectType objType) {
        //获取监测对象
        List<MeasObj> filterList = measObjs.stream().filter(x -> x.getObjtypeId().intValue() == objType.getValue()).collect(Collectors.toList());

        //获取监测对象id
        List<Integer> ids = filterList.stream().map(x -> x.getId()).collect(Collectors.toList());

        //获取今日数据
        long todayCt = dbList.stream().filter(x -> ids.contains(x.getObjectId())
                && x.getTime().getTime() >= today.getTime()
                && x.getCv().intValue() == SwitchEnum.OPEN.getValue()).count();

        //获取昨日数据
        long yesterdayCt = dbList.stream().filter(x -> ids.contains(x.getObjectId())
                && x.getTime().getTime() < today.getTime()
                && x.getCv().intValue() == SwitchEnum.OPEN.getValue()).count();

        JSONObject json = new JSONObject();
        json.put("name", objType.getName() + "触发");
        json.put("value", todayCt);
        json.put("unit", "次");
        json.put("isRise", todayCt > yesterdayCt);

        return json;
    }


    /**
     * 获取处于“开”状态的监测对象数量以及在廊人数
     *
     * @return {"msg":"请求成功","code":"200","data":[{"name":"已打开电子井盖数","value":0},{"name":"在廊人数","value":2}]}
     * @author shaosen
     * @Date 2019年1月18日
     */
    @RequestMapping(value = "meas-switched-counts", method = RequestMethod.GET)
    public JSONObject getSwitchedCount() {

        //获取已打开电子井盖数
        List<MeasObjSI> measObjSIs = measObjModuleCenter.getMeasObjSIs();
        long count = measObjSIs.stream().filter(x -> x.getObjtypeId().intValue() == ObjectType.ELECTRONIC_COVERS.getValue()
                && x.getCv() == SwitchEnum.OPEN.getValue()).count();

        JSONObject json_1 = new JSONObject();
        json_1.put("name", "已打开电子井盖数");
        json_1.put("value", count);
        json_1.put("type", 1);

        List<JSONObject> rtdata = new ArrayList<>();
        rtdata.add(json_1);

        //获取当前入廊人数
        JSONObject json_2 = new JSONObject();
        json_2.put("name", "在廊人数");
        json_2.put("value", 2);
        json_2.put("type", 2);
        rtdata.add(json_2);

        return CommonUtil.success(rtdata);
    }

    /**
     * 机电或安防监测下各状态的对象数量
     *
     * @param tunnelId
     * @param storeId
     * @param areaId
     * @param monitorType 3：安防；4：机电
     * @return
     * @author ya.liu
     * @Date 2019年5月21日
     */
    @RequestMapping(value = "meas-status-counts", method = RequestMethod.POST)
    public JSONObject getCountBySI(@RequestBody JSONObject object) {
        Integer tunnelId = object.getInteger("tunnelId");
        Integer storeId = object.getInteger("storeId");
        Integer areaId = object.getInteger("areaId");
        Integer monitorType = object.getInteger("monitorType");

        List<JSONObject> list = new ArrayList<>();

        if (monitorType == null) return CommonUtil.success(list);
        List<ObjectType> eList = ObjectType.getEnumByMonitorType(monitorType);
        for (ObjectType objectType : eList) {
            List<MeasObjSI> measObjSIs = measObjModuleCenter.getMeasObjSIs();
            measObjSIs = measObjSIs.stream().filter(x -> x.getObjtypeId().intValue() == objectType.getValue()).collect(Collectors.toList());
            if (tunnelId != null)
                measObjSIs = measObjSIs.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
            if (storeId != null)
                measObjSIs = measObjSIs.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
            if (areaId != null)
                measObjSIs = measObjSIs.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());

            JSONObject json = new JSONObject();
            json.put("name", objectType.getName());
            List<JSONObject> jsonList = new ArrayList<>();
            for (SwitchEnum e : SwitchEnum.values()) {
                if (monitorType.equals(MonitorTypeEnum.SECURITY.getValue()) && e == SwitchEnum.FAULT) continue;
                if (monitorType.equals(MonitorTypeEnum.ELECTROMECHANICAL.getValue()) && e == SwitchEnum.ALARM) continue;
                JSONObject obj = new JSONObject();
                long count = measObjSIs.stream().filter(x -> x.getCv() == e.getValue()).count();
                obj.put("key", e.getName());
                obj.put("val", count);
                jsonList.add(obj);
            }

            json.put("data", jsonList);
            list.add(json);
        }

        return CommonUtil.success(list);
    }

    /**
     * 消防监测数据
     *
     * @param tunnelId
     * @param storeId
     * @param areaId
     * @return
     * @author ya.liu
     * @Date 2019年5月21日
     */
    @RequestMapping(value = "meas-fire-counts", method = RequestMethod.POST)
    public JSONObject getCountByFire(@RequestBody JSONObject object) {
        Integer tunnelId = object.getInteger("tunnelId");
        Integer storeId = object.getInteger("storeId");
        Integer areaId = object.getInteger("areaId");
        int monitorType = MonitorTypeEnum.FIRE_CONTROL.getValue();

        List<JSONObject> list = measObjService.getMeasObjMaxOrMinValue(tunnelId, storeId, areaId, monitorType);
        ObjectType objType = ObjectType.HAND_REPORT;

        List<MeasObjSI> measObjSIs = measObjModuleCenter.getMeasObjSIs();
        measObjSIs = measObjSIs.stream().filter(x -> x.getObjtypeId().intValue() == objType.getValue()).collect(Collectors.toList());
        if (tunnelId != null)
            measObjSIs = measObjSIs.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
        if (storeId != null)
            measObjSIs = measObjSIs.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
        if (areaId != null)
            measObjSIs = measObjSIs.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());

        JSONObject json = new JSONObject();
        json.put("key", objType.getName());
        long open = measObjSIs.stream().filter(x -> x.getCv() == SwitchEnum.OPEN.getValue()).count();
        json.put("open", open);
        long close = measObjSIs.stream().filter(x -> x.getCv() == SwitchEnum.CLOSE.getValue()).count();
        json.put("close", close);
        list.add(json);

        return CommonUtil.success(list);
    }

    /**
     * 绑定预案
     *
     * @param reqJson {"objtypeId":1,"planIds":"plan1,plan2,plan3"}
     * @return
     */
    @RequestMapping(value = "measobjs/conf/plans", method = RequestMethod.POST)
    public JSONObject setPlanIds(@RequestBody JSONObject reqJson) {
        CommonUtil.hasAllRequired(reqJson, "objtypeId,planIds");
        Integer objtypeId = reqJson.getInteger("objtypeId");
        String planIds = reqJson.getString("planIds");
        measObjService.setPlanIds(objtypeId, planIds);

        return CommonUtil.success();
    }


    /**
     * 绑定视频
     *
     * @param reqJson {"id":1,"videoIds":"video1,video2,video3"}
     * @return
     */
    @RequestMapping(value = "measobjs/conf/videos", method = RequestMethod.POST)
    public JSONObject setVideos(@RequestBody JSONObject reqJson) {
        CommonUtil.hasAllRequired(reqJson, "id,videoIds");
        Integer id = reqJson.getInteger("id");
        String videoIds = reqJson.getString("videoIds");
        measObjService.setVideoIds(id, videoIds);

        return CommonUtil.success();
    }


    /**
     * 获取监测对象所在section的所有视频
     *
     * @param storeId
     * @param areaId
     * @return
     */
    @RequestMapping(value = "measobjs/{storeId}/{areaId}/videos", method = RequestMethod.GET)
    public JSONObject getLocalSectionVideoList(@PathVariable("storeId") Integer storeId, @PathVariable("areaId") Integer areaId) {

        List<VideoDto> videoList = new ArrayList<>(10);
        Section section = sectionService.getSectionByStoreAndArea(storeId, areaId);
        if (section != null) {
            videoList = measObjService.getLocalSectionVideoList(section.getId());
        }
        return CommonUtil.success(videoList);
    }

    /**
     * 监测对象id模糊查询
     *
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年4月15日
     */
    @RequestMapping(value = "measobjs/{id}/condition", method = RequestMethod.GET)
    public JSONObject getMeasobjIdList(@PathVariable("id") String id) {

        List<Integer> list = measObjService.getIdList(id);
        return CommonUtil.success(list);
    }

    /**
     * 获取布防cv值
     *
     * @param tunnelId
     * @return
     * @author ya.liu
     * @Date 2019年7月3日
     */
    @RequestMapping(value = "tunnels/{tunnelId}/deploy", method = RequestMethod.GET)
    public JSONObject getDeploy(@PathVariable("tunnelId") Integer tunnelId) {

        TunnelSimpleDto tunnel = tunnelService.getSimpleDtoById(tunnelId);
//        List<MeasObjDI> dis = measObjModuleCenter.getMeasObjDIs();
        int objTypeId = ObjectType.DEPLOY_DEFENCE.getValue();
//        dis = dis.stream().filter(a -> a.getObjtypeId() == objTypeId).collect(Collectors.toList());

        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs().stream()
                .filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList()).stream()
                .filter(a -> a.getObjtypeId().intValue() == objTypeId).collect(Collectors.toList());

        List<MeasObjDI> dis = new ArrayList<>();

        for (MeasObj measObj : measObjs) {
            ComplexObjectConvert complexObjectConvert1 = measObjModuleCenter.getComplexObjectConvertByMeasObj(measObj);
            ConvertType convertType = complexObjectConvert1.getConvertType("open", 0);
            int diId = measObjModuleCenter.getConvertObjectId(measObj, convertType);

            MeasObjDI measObjDI = measObjModuleCenter.getMeasObjDI(diId);
            dis.add(measObjDI);
        }

        JSONObject json = new JSONObject();

        List<AreaDto> areas = areaService.getAreasByTunnelId(tunnelId);
        List<StoreDto> stores = storeService.getStoresByTunnelId(tunnelId);
        json.put("tunnelId", tunnelId);
        json.put("tunnelName", tunnel.getName());
        List<JSONObject> areaList = new ArrayList<>();
        for (AreaDto area : areas) {
            JSONObject areaJson = new JSONObject();
            areaJson.put("areaId", area.getId());
            areaJson.put("areaName", area.getName());
            List<JSONObject> storeList = new ArrayList<>();
            for (StoreDto store : stores) {
                String[] sn = {"01", "02", "03", "04"};
                List<String> list = Arrays.asList(sn);
                if (!list.contains(store.getSn())) continue;
                JSONObject storeJson = new JSONObject();
                storeJson.put("storeId", store.getId());
                storeJson.put("storeName", store.getName());

                List<MeasObjDI> diValue = dis.stream()
                        .filter(a -> a.getAreaId().equals(area.getId()) && a.getStoreId().equals(store.getId()))
                        .collect(Collectors.toList());
                MeasObjDI di = null;
                if (diValue != null && diValue.size() > 0) di = diValue.get(0);
                List<MeasObj> tmpObjs = measObjs.stream()
                        .filter(a -> a.getAreaId().equals(area.getId()) && a.getStoreId().equals(store.getId()))
                        .collect(Collectors.toList());
                storeJson.put("id", di == null ? null : tmpObjs.get(0).getId());
                storeJson.put("val", di == null ? null : di.getCv());
                storeList.add(storeJson);
            }
            areaJson.put("store", storeList);
            areaList.add(areaJson);
        }
        json.put("area", areaList);
        return CommonUtil.success(json);
    }

    /**
     * 获取联动cv值
     *
     * @param tunnelId
     * @return
     * @author ya.liu
     * @Date 2019年7月3日
     */
    @RequestMapping(value = "tunnels/{tunnelId}/linkage", method = RequestMethod.GET)
    public JSONObject getLinkage(@PathVariable("tunnelId") Integer tunnelId) {

        TunnelSimpleDto tunnel = tunnelService.getSimpleDtoById(tunnelId);
        int objTypeId = ObjectType.LINKAGE.getValue();

        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs().stream()
                .filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList()).stream()
                .filter(a -> a.getObjtypeId().intValue() == objTypeId).collect(Collectors.toList());

        List<MeasObjDI> dis = new ArrayList<>();

        for (MeasObj measObj : measObjs) {
            ComplexObjectConvert complexObjectConvert1 = measObjModuleCenter.getComplexObjectConvertByMeasObj(measObj);
            ConvertType convertType = complexObjectConvert1.getConvertType("open", 0);
            int diId = measObjModuleCenter.getConvertObjectId(measObj, convertType);

            MeasObjDI measObjDI = measObjModuleCenter.getMeasObjDI(diId);
            dis.add(measObjDI);
        }

        JSONObject json = new JSONObject();

        List<AreaDto> areas = areaService.getAreasByTunnelId(tunnelId);
        json.put("tunnelId", tunnelId);
        json.put("tunnelName", tunnel.getName());
        List<JSONObject> areaList = new ArrayList<>();
        for (AreaDto area : areas) {
            JSONObject areaJson = new JSONObject();
            areaJson.put("areaId", area.getId());
            areaJson.put("areaName", area.getName());
            List<MeasObjDI> diValue = dis.stream()
                    .filter(a -> a.getAreaId().equals(area.getId()))
                    .collect(Collectors.toList());
            MeasObjDI di = null;
            if (diValue != null && diValue.size() > 0) di = diValue.get(0);
            List<MeasObj> tmpObjs = measObjs.stream().filter(a -> a.getAreaId().equals(area.getId())).collect(Collectors.toList());
            areaJson.put("id", di == null ? null : tmpObjs.get(0).getId());
            areaJson.put("val", di == null ? null : di.getCv());
            areaList.add(areaJson);
        }
        json.put("area", areaList);
        return CommonUtil.success(json);
    }

    /**
     * 获取外部/气象台的温度/湿度
     *
     * @return
     * @author ya.liu
     * @Date 2019年7月3日
     */
    @RequestMapping(value = "measobjs/outside", method = RequestMethod.GET)
    public JSONObject getOutside() {
        List<MeasObj> list = measObjModuleCenter.getMeasObjs();
        list = list.stream().filter(a -> a.getDatatypeId().equals(DataType.AI.getValue())).collect(Collectors.toList());
        list = list.stream().filter(a -> a.getTunnelId() == 0).collect(Collectors.toList());
        list = list.stream().filter(a -> a.getObjtypeId().equals(ObjectType.TEMPERATURE.getValue()) || a.getObjtypeId().equals(ObjectType.HUMIDITY.getValue())).collect(Collectors.toList());

        return CommonUtil.success(getMeasObjInfo(list));
    }

    /**
     * 获得所有的故障设备
     *
     * @return 按类型统计
     */
    @RequestMapping(value = "fault_measobjs", method = RequestMethod.GET)
    public JSONObject getFaultMeasObjs() {
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();

        return CommonUtil.success(classifyMeasObjsByObjType(measObjs));
    }

    /**
     * 获得所有的故障设备
     *
     * @param tunnelId
     * @return 按类型统计
     */
    @RequestMapping(value = "tunnels/{tunnelId}/fault_measobjs", method = RequestMethod.GET)
    public JSONObject getFaultMeasObjsByTunnel(@PathVariable("tunnelId") int tunnelId) {
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs().stream()
                .filter(a -> a.getTunnelId().intValue() == tunnelId).collect(Collectors.toList());

        return CommonUtil.success(classifyMeasObjsByObjType(measObjs));
    }

    /**
     * 找出所有的故障对象
     *
     * @param measObjs
     * @return
     */
    private List<MeasObj> findAllFaultMeasObjs(List<MeasObj> measObjs) {
        List<MeasObj> result = new ArrayList<>();
        for (MeasObj mo : measObjs) {
            if (measObjService.isMeasObjFault(mo)) result.add(mo);
        }
        return result;
    }

    /**
     * 根据类型分类
     *
     * @param measObjs
     * @return
     */
    private List<JSONObject> classifyMeasObjsByObjType(List<MeasObj> measObjs) {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        List<AreaDto> areas = areaService.getList();
        List<Store> stores = storeService.getList();

        List<JSONObject> result = new ArrayList<>();

        // 获取配置文件中所有的objectTypeId
        List<ObjectType> objectTypes = new ArrayList<>();
        Collections.addAll(objectTypes, ObjectType.values());
        List<Integer> objectTypeIds = xmlService.getXMLAllInfo().getObjectTypeIds();
        objectTypes = objectTypes.stream().filter(a -> objectTypeIds.contains(a.getValue())).collect(Collectors.toList());
        // 小数显示位数
        Integer decimal = xmlService.getXMLAllInfo().getDecimal();

        for (int i = 0; i < objectTypes.size(); i++) {

            JSONObject tmp_res = new JSONObject();
            ObjectType type = objectTypes.get(i);
            tmp_res.put("objTypeId", type.getValue());
            tmp_res.put("objType", type.getName());

            List<MeasObj> tmp_objs = measObjs.stream().filter(a -> a.getObjtypeId().intValue() == type.getValue()).collect(Collectors.toList());
            tmp_res.put("total", tmp_objs.size());

            // 获取这个类型所有的有故障的检测对象
            List<MeasObj> tmp_objs2 = findAllFaultMeasObjs(tmp_objs);
            List<JSONObject> faults = new ArrayList<>();

            for (int j = 0; j < tmp_objs2.size(); j++) {
                JSONObject tmp_res2 = getMeasObjCurValue(tmp_objs2.get(j), tunnels, areas, stores, decimal);
                // 返回监测对象关联的设备状态
                boolean isBroken = false;
                Integer moid = tmp_res2.getInteger("id");
                if (moid != null) {
                    EquipmentDto dto = equipmentService.getEquipmentByObj(moid);
                    if (!dto.getStatus().equals(EquipmentStatusEnum.NORMAL.getValue()))
                        isBroken = true;
                }
                tmp_res2.put("isBroken", isBroken);
                faults.add(tmp_res2);
            }
            tmp_res.put("faults", faults);
            result.add(tmp_res);
        }

        return result;
    }

    /**
     * 获取检测对象当前值，并返回详细信息
     *
     * @param measObj
     * @param tunnels
     * @param areas
     * @param stores
     * @param decimal
     * @return
     */
    private JSONObject getMeasObjCurValue(MeasObj measObj, List<TunnelSimpleDto> tunnels, List<AreaDto> areas, List<Store> stores, int decimal) {
        Object cv;
        Date time;
        switch (DataType.getEnum(measObj.getDatatypeId())) {
            case AI:
                MeasObjAI ai = measObjModuleCenter.getMeasObjAI(measObj.getId());
                cv = ai == null ? 0 : ai.getCv(decimal);
                time = ai == null ? new Date() : ai.getRefreshTime();
                break;

            case DI:
                MeasObjDI di = measObjModuleCenter.getMeasObjDI(measObj.getId());
                cv = (di == null || !di.getCv()) ? 0 : 1;
                time = di == null ? new Date() : di.getRefreshTime();
                break;

            case SI:
                MeasObjSI si = measObjModuleCenter.getMeasObjSI(measObj.getId());
                cv = si == null ? 0 : si.getCv();
                time = si == null ? new Date() : si.getRefreshTime();
                break;

            case SO:
                MeasObjSO so = measObjModuleCenter.getMeasObjSO(measObj.getId());
                cv = so == null ? 0 : so.getCv();
                time = so == null ? new Date() : so.getRefreshTime();
                break;

            case ComplexObject:
                cv = measObjModuleCenter.getMeasObjComplexObjectCV(measObj.getId());
                time = (Date) ((JSONObject) cv).get("time");
                break;

            default:
                cv = 0;
                time = new Date();
                break;
        }

        JSONObject json = new JSONObject();
        json.put("id", measObj.getId());
        json.put("name", measObj.getName());
        for (TunnelSimpleDto tunnel : tunnels) {
            if (tunnel.getId().intValue() == measObj.getTunnelId().intValue()) {
                json.put("tunnel", tunnel.getName());
                json.put("tunnelId", tunnel.getId());
            }
        }
        for (AreaDto area : areas) {
            if (area.getId().intValue() == measObj.getAreaId().intValue()) {
                json.put("area", area.getName());
                json.put("areaId", area.getId());
                json.put("areaLeath", area.getLength());
            }
        }
        for (Store store : stores) {
            if (store.getId().intValue() == measObj.getStoreId().intValue()) {
                json.put("store", store.getName());
                json.put("storeId", store.getId());
            }
        }
        json.put("datatypeId", measObj.getDatatypeId());
        json.put("curValue", cv);
        json.put("time", time);
        json.put("description", measObj.getDescription() == null ? "" : measObj.getDescription());

        ObjectType objectType = ObjectType.getEnum(measObj.getObjtypeId());
        if (objectType != null) {
            json.put("control", objectType.isControl());
            json.put("reset", objectType.isReset());
            json.put("unit", objectType.getUnit());
        }

        ObjTypeParam objTypeParam = xmlService.getXMLAllInfo().getObjTypeParam(objectType);
        json.put("maxValue", objTypeParam != null ? objTypeParam.getMeasMax() : "-");
        json.put("minValue", objTypeParam != null ? objTypeParam.getMeasMin() : "-");
        json.put("maxNormal", objTypeParam != null ? objTypeParam.getNormalMax() : "-");
        json.put("minNormal", objTypeParam != null ? objTypeParam.getNormalMin() : "-");

        return json;
    }

    @RequiresPermissions("measobj:update")
    @RequestMapping(value = "measobjs/update_actived", method = RequestMethod.PUT)
    public JSONObject updateMeasObjsActived(@RequestBody JSONObject reqJson) {
        Integer tunnelId = reqJson.getInteger("tunnelId");
        Integer areaId = reqJson.getInteger("areaId");
        Integer storeId = reqJson.getInteger("storeId");
        boolean actived = reqJson.getBooleanValue("actived");

        measObjService.updateObjectsActived(tunnelId, areaId, storeId, actived);

        return CommonUtil.success();
    }
}
