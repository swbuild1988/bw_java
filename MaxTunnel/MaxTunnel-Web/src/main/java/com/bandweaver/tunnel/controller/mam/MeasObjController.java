package com.bandweaver.tunnel.controller.mam;

import java.util.*;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.MonitorTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
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
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
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
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
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


    /**
     * 添加measObj
     *
     * @param id
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
        LogUtil.info(list);
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

    // 获取监测对象值
    private List<JSONObject> getMeasObjInfo(List<MeasObj> measObjs) {
        //获取告警临界值
        double temperature_max = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.TEMPERATURE_MAX));
        double temperature_min = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.TEMPERATURE_MIN));
        double humidity_max = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.HUMIDITY_MAX));
        double humidity_min = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.HUMIDITY_MIN));
        double oxygen_max = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.OXYGEN_MAX));
        double oxygen_min = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.OXYGEN_MIN));
        double h2s_max = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.H2S_MAX));
        double h2s_min = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.H2S_MIN));
        double ch4_max = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.CH4_MAX));
        double ch4_min = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.CH4_MIN));
        double co_max = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.CO_MAX));
        double co_min = DataTypeUtil.toDouble(PropertiesUtil.getValue(Constants.CO_MIN));

        List<AreaDto> areas = areaService.getList();
        List<Store> stores = storeService.getList();

        List<JSONObject> returnData = new ArrayList<>();
        for (MeasObj measObjDto : measObjs) {

            Object cv;
            switch (DataType.getEnum(measObjDto.getDatatypeId())) {
                case AI:
                	MeasObjAI ai = measObjModuleCenter.getMeasObjAI(measObjDto.getId());
                    cv = ai == null ? 0 : ai.getCv();
                    break;

                case DI:
                	MeasObjDI di = measObjModuleCenter.getMeasObjDI(measObjDto.getId());
                    cv = di == null ? 0 : di.getCv();
                    break;

                case SI:
                	MeasObjSI si = measObjModuleCenter.getMeasObjSI(measObjDto.getId());
                    cv = si == null ? 0 : si.getCv();
                    break;

                case SO:
                	MeasObjSO so = measObjModuleCenter.getMeasObjSO(measObjDto.getId());
                    cv = so == null ? 0 : so.getCv();
                    break;

                case ComplexObject:
                    cv = measObjModuleCenter.getMeasObjComplexObjectCV(measObjDto.getId());
                    break;

                default:
                    cv = 0;
                    break;
            }

            JSONObject json = new JSONObject();
            json.put("id", measObjDto.getId());
            json.put("name", measObjDto.getName());
            for (AreaDto area : areas) {
                if (area.getId().intValue() == measObjDto.getAreaId().intValue()) {
                    json.put("area", area.getName());
                    json.put("areaLeath", area.getLength());
                }
            }
            for (Store store : stores) {
                if (store.getId().intValue() == measObjDto.getStoreId().intValue()) {
                    json.put("store", store.getName());
                }
            }
            json.put("datatypeId", measObjDto.getDatatypeId());
            json.put("curValue", cv);
            json.put("time", measObjModuleCenter.getMeasObjAI(measObjDto.getId()) == null ? System.currentTimeMillis() : measObjModuleCenter.getMeasObjAI(measObjDto.getId()).getRefreshTime());

            ObjectType objectType = ObjectType.getEnum(measObjDto.getObjtypeId());
            if (objectType != null) json.put("control", objectType.isControl());
            if (objectType == ObjectType.TEMPERATURE) {
                json.put("maxValue", temperature_max);
                json.put("minValue", temperature_min);
            } else if (objectType == ObjectType.HUMIDITY) {
                json.put("maxValue", humidity_max);
                json.put("minValue", humidity_min);
            } else if (objectType == ObjectType.OXYGEN) {
                json.put("maxValue", oxygen_max);
                json.put("minValue", oxygen_min);
            } else if (objectType == ObjectType.H2S) {
                json.put("maxValue", h2s_max);
                json.put("minValue", h2s_min);
            } else if (objectType == ObjectType.CH4) {
                json.put("maxValue", ch4_max);
                json.put("minValue", ch4_min);
            } else if (objectType == ObjectType.CO) {
                json.put("maxValue", co_max);
                json.put("minValue", co_min);
            } else {
                json.put("maxValue", null);
                json.put("minValue", null);
            }
            json.put("unit", objectType.getUnit());
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

        String idsStr = DataTypeUtil.toString(map.get("ids"));

        String[] idsArr = idsStr.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idsArr) {
            idList.add(DataTypeUtil.toInteger(id));
        }
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
    @Deprecated
    @RequestMapping(value = "measobjs", method = RequestMethod.GET)
    public JSONObject getAllMeasObjList() {
        List<MeasObj> list = measObjModuleCenter.getMeasObjs();
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
        JSONObject rt2 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "max", ObjectType.CH4);
        JSONObject rt3 = getMaxOrMinValueByObjType(currentDate, measObjAIs, measObjs, "min", ObjectType.OXYGEN);

        List<JSONObject> rtdata = new ArrayList<>();
        rtdata.add(rt1);
        rtdata.add(rt2);
        rtdata.add(rt3);

        return CommonUtil.success(rtdata);
    }


    private JSONObject getMaxOrMinValueByObjType(Date currentDate, List<MeasObjAI> measObjAIs, List<MeasObj> measObjs, String maxOrmin, ObjectType objType) {

        JSONObject rtdata = new JSONObject();
        double value = 0;
        String location = "";

        //获取所有温度检测对象
        List<MeasObj> temperatureList = measObjs.stream().filter(x -> x.getObjtypeId().intValue() == objType.getValue()).collect(Collectors.toList());

        //获取所有温度检测对象id
        List<Integer> ids = temperatureList.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<MeasObjAI> collect = measObjAIs.stream().filter(x -> ids.contains(x.getId())).collect(Collectors.toList());

        if (collect.isEmpty()) {
            switch (objType) {
                case TEMPERATURE:
                    JSONObject json_1 = new JSONObject();
                    json_1.put("name", "最高温度");
                    json_1.put("value", value);
                    json_1.put("unit", objType.getUnit());
                    json_1.put("location", "无位置信息");
                    json_1.put("time", currentDate);
                    json_1.put("type", objType.getValue());
                    json_1.put("max", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".max"));
                    json_1.put("min", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".min"));
                    return json_1;
                case CH4:
                    JSONObject json_2 = new JSONObject();
                    json_2.put("name", "最高甲烷");
                    json_2.put("value", value);
                    json_2.put("unit", objType.getUnit());
                    json_2.put("location", "无位置信息");
                    json_2.put("time", currentDate);
                    json_2.put("type", objType.getValue());
                    json_2.put("max", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".max"));
                    json_2.put("min", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".min"));
                    return json_2;
                case OXYGEN:
                    JSONObject json_3 = new JSONObject();
                    json_3.put("name", "最低含氧量");
                    json_3.put("value", value);
                    json_3.put("unit", objType.getUnit());
                    json_3.put("location", "无位置信息");
                    json_3.put("time", currentDate);
                    json_3.put("type", objType.getValue());
                    json_3.put("max", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".max"));
                    json_3.put("min", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".min"));
                    return json_3;

                default:
                    break;
            }
        }


        if ("max".equals(maxOrmin)) {
            //获取最大值
            MeasObjAI measValueAI = collect.stream().max(Comparator.comparing(MeasObjAI::getCv)).get();
            //获取位置信息
            location = getLocation(location, measValueAI);

            rtdata.put("name", "最高" + objType.getName());
            rtdata.put("value", measValueAI == null ? value : measValueAI.getCv());
            rtdata.put("unit", objType.getUnit());
            rtdata.put("time", measValueAI == null ? currentDate : measValueAI.getRefreshTime());

        } else if ("min".equals(maxOrmin)) {
            //获取最大值
            MeasObjAI measValueAI = collect.stream().min(Comparator.comparing(MeasObjAI::getCv)).get();
            //获取位置信息
            location = getLocation(location, measValueAI);

            rtdata.put("name", "最低含氧量");
            rtdata.put("value", measValueAI == null ? value : measValueAI.getCv());
            rtdata.put("unit", objType.getUnit());
            rtdata.put("time", measValueAI == null ? currentDate : measValueAI.getRefreshTime());
        }
        rtdata.put("location", location);
        rtdata.put("type", objType.getValue());
        rtdata.put("max", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".max"));
        rtdata.put("min", PropertiesUtil.getDoubleValue("ai." + objType.name().toLowerCase() + ".min"));

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
     * @param tunnelId
     * @return
     * @author ya.liu
     * @Date 2019年7月3日
     */
    @RequestMapping(value = "tunnels/{tunnelId}/deploy", method = RequestMethod.GET)
    public JSONObject getDeploy(@PathVariable("tunnelId") Integer tunnelId) {
    	
    	TunnelSimpleDto tunnel = tunnelService.getSimpleDtoById(tunnelId);
    	List<MeasObjDI> dis = measObjModuleCenter.getMeasObjDIs();
    	int objTypeId = ObjectType.DEPLOY_DEFENCE.getValue();
    	dis = dis.stream().filter(a -> a.getObjtypeId() == objTypeId).collect(Collectors.toList());
    	JSONObject json = new JSONObject();
    	
    	List<AreaDto> areas = areaService.getAreasByTunnelId(tunnelId);
    	List<StoreDto> stores = storeService.getStoresByTunnelId(tunnelId);
    	json.put("tunnelId", tunnelId);
    	json.put("tunnelName", tunnel.getName());
    	List<JSONObject> areaList = new ArrayList<>();
    	for(AreaDto area :areas) {
    		JSONObject areaJson = new JSONObject();
    		areaJson.put("areaId", area.getId());
    		areaJson.put("areaName", area.getName());
    		List<JSONObject> storeList = new ArrayList<>();
    		for(StoreDto store : stores) {
    			String [] sn = {"01", "02", "03", "04"};
    			List<String> list = Arrays.asList(sn);
    			if(!list.contains(store.getSn())) continue;
    			JSONObject storeJson = new JSONObject();
    			storeJson.put("storeId", store.getId());
    			storeJson.put("storeName", store.getName());
    			
    			List<MeasObjDI> diValue = dis.stream()
    					.filter(a -> a.getAreaId().equals(area.getId()) && a.getStoreId().equals(store.getId()))
    					.collect(Collectors.toList());
    			MeasObjDI di = null;
    			if(diValue != null && diValue.size() > 0) di = diValue.get(0);
    			storeJson.put("id", di == null ? null : di.getId());
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
     * @param tunnelId
     * @return
     * @author ya.liu
     * @Date 2019年7月3日
     */
    @RequestMapping(value = "tunnels/{tunnelId}/linkage", method = RequestMethod.GET)
    public JSONObject getLinkage(@PathVariable("tunnelId") Integer tunnelId) {
    	
    	TunnelSimpleDto tunnel = tunnelService.getSimpleDtoById(tunnelId);
    	List<MeasObjDI> dis = measObjModuleCenter.getMeasObjDIs();
    	int objTypeId = ObjectType.LINKAGE.getValue();
    	dis = dis.stream().filter(a -> a.getObjtypeId() == objTypeId).collect(Collectors.toList());
    	JSONObject json = new JSONObject();
    	
    	List<AreaDto> areas = areaService.getAreasByTunnelId(tunnelId);
    	json.put("tunnelId", tunnelId);
    	json.put("tunnelName", tunnel.getName());
    	List<JSONObject> areaList = new ArrayList<>();
    	for(AreaDto area :areas) {
    		JSONObject areaJson = new JSONObject();
    		areaJson.put("areaId", area.getId());
    		areaJson.put("areaName", area.getName());
			List<MeasObjDI> diValue = dis.stream()
					.filter(a -> a.getAreaId().equals(area.getId()))
					.collect(Collectors.toList());
			MeasObjDI di = null;
			if(diValue != null && diValue.size() > 0) di = diValue.get(0);
			areaJson.put("id", di == null ? null : di.getId());
			areaJson.put("val", di == null ? null : di.getCv());
    		areaList.add(areaJson);
    	}
    	json.put("area", areaList);
    	return CommonUtil.success(json);
    }
    
    /**
     * 获取外部/气象台的温度/湿度
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
}
