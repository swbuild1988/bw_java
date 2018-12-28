package com.bandweaver.tunnel.controller.mam;

import java.util.*;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIParam;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
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
import com.github.pagehelper.Constant;
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
    private MeasObjAIService measObjAIService;
    @Autowired
    private MeasObjSOService measObjSOService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private AreaService areaService;


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
    @RequestMapping(value = "measobjs/datagrid", method = RequestMethod.POST)
    public JSONObject measObjDataGrid(@RequestBody MeasObjVo vo) {
        PageInfo<MeasObjDto> pageInfo = measObjService.dataGrid(vo);

        //get CV
        List<MeasObjDto> objDtoList = pageInfo.getList();
        for (MeasObjDto measObjDto : objDtoList) {
            double cv = measObjService.getMeasObjCVByIdAndDataType(measObjDto.getId(), measObjDto.getDatatypeId());
            measObjDto.setCv(cv);
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
    @RequestMapping(value = "measobjs/list", method = RequestMethod.POST)
    public JSONObject getObjectListByCondition(@RequestBody JSONObject reqJson) {
        CommonUtil.hasAllRequired(reqJson, "tunnelId");
//    	MeasObjVo measObjVo = CommonUtil.parse2Obj(reqJson, MeasObjVo.class);
//    	List<MeasObjDto> measObjList = measObjService.getMeasObjByCondition(measObjVo);
        List<MeasObj> measObjs = measObjModuleCenter.getMeasObjs();
        Integer tunnelId = reqJson.getInteger("tunnelId");
        Integer storeId = reqJson.getInteger("storeId");
        Integer areaId = reqJson.getInteger("areaId");
        Integer objtypeId = reqJson.getInteger("objtypeId");

        if (tunnelId != null)
            measObjs = measObjs.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
        if (storeId != null)
            measObjs = measObjs.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
        if (areaId != null)
            measObjs = measObjs.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());
        if (objtypeId != null)
            measObjs = measObjs.stream().filter(a -> a.getObjtypeId().intValue() == objtypeId.intValue()).collect(Collectors.toList());


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

            Double cv = 0.0;
            switch (DataType.getEnum(measObjDto.getDatatypeId())) {
                case AI:
                    cv = measObjModuleCenter.getMeasObjAI(measObjDto.getId()).getCv();
                    break;

                case DI:
                    cv = measObjModuleCenter.getMeasObjDI(measObjDto.getId()).getCv() ? 1.0 : 0.0;
                    break;

                case SI:
                    Integer tmpV = measObjModuleCenter.getMeasObjSI(measObjDto.getId()).getCv();
                    cv = tmpV == null ? 0 : tmpV.doubleValue();
                    break;

                default:
                    break;
            }

            JSONObject json = new JSONObject();
            json.put("id", measObjDto.getId());
            json.put("name", measObjDto.getName());
            for (AreaDto area : areas) {
                if (area.getId().intValue() == measObjDto.getAreaId().intValue()) {
                    json.put("area", area.getName());
                }
            }
            for (Store store : stores) {
                if (store.getId().intValue() == measObjDto.getStoreId().intValue()) {
                    json.put("store", store.getName());
                }
            }
            json.put("datatypeId", measObjDto.getDatatypeId());
            json.put("curValue", cv);

            ObjectType objectType = ObjectType.getEnum(measObjDto.getObjtypeId());
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
            returnData.add(json);
        }
        return CommonUtil.success(returnData);

    }

    /**求管廊监测数据极值 
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

        List<MeasObjAI> measObjAIList = measObjModuleCenter.getMeasObjAIs();
        if (tunnelId != null)
            measObjAIList = measObjAIList.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
        if (storeId != null)
            measObjAIList = measObjAIList.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
        if (areaId != null)
            measObjAIList = measObjAIList.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());

        // 分别查询温度，湿度等的极限值
        if (measObjAIList != null && measObjAIList.size() > 0) {
            List<MeasObjAIParam> paramList = new ArrayList<>();
            MeasObjAIParam temperature = new MeasObjAIParam(0.00);
            MeasObjAIParam humidity = new MeasObjAIParam(0.00);
            MeasObjAIParam oxygen = new MeasObjAIParam(10000.00);
            MeasObjAIParam h2s = new MeasObjAIParam(0.00);
            MeasObjAIParam ch4 = new MeasObjAIParam(0.00);
            MeasObjAIParam co = new MeasObjAIParam(0.00);

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

            paramList.add(temperature);
            paramList.add(humidity);
            paramList.add(oxygen);
            paramList.add(h2s);
            paramList.add(ch4);
            paramList.add(co);

            List<JSONObject> list = getJsonObjList(paramList);
            return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

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
     * 根据measObjId和datatypeId查询cv值
     *
     * @param ids        objId字符串，格式如1,2,3,4...
     * @param datatypeId
     * @return {"msg":"请求成功","code":"200","data":[{"id":41003,"tunnelId":1,"storeId":1,"sectionId":1,"name":"声光报警3","datatypeId":3,"objtypeId":41,"actived":true,"description":null,"longitude":"0.003534597099157488","latitude":"0.18009738011735688","height":"0.5474121265387349","deviation":0.460808326715716,"refreshTime":1531992210000,"cv":1.0}]}
     * @author shaosen
     * @date 2018年7月18日
     */
    @Deprecated
    @RequestMapping(value = "measobjs/{ids}/{datatypeId}/cache-cv", method = RequestMethod.GET)
    public JSONObject getCVByIdFromCache(@PathVariable("ids") String ids, @PathVariable("datatypeId") Integer datatypeId) {

        List<Integer> idList = new ArrayList<>();
        String[] arr = ids.split(",");
        for (String str : arr) {
            idList.add(DataTypeUtil.toInteger(str));
        }

        DataType dataType = DataType.getEnum(datatypeId);
        switch (dataType) {
            case AI:
                return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, measObjModuleCenter.getMeasObjAIListByIds(idList));
            case DI:
                return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, measObjModuleCenter.getMeasObjDIListByIds(idList));
            case SI:
                return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, measObjModuleCenter.getMeasObjSIListByIds(idList));
            case DISTRIBUTE:
                break;
            case VIDEO:
                break;
            case SO:
                break;
            case DAS:
                break;
            default:
                break;
        }
        return null;
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
     * 监测对象查询
     *
     * @param sectionIds 管舱段id集合
     * @param storeIds   管舱id集合
     * @param tunnelIds  管廊id集合
     * @param id         监测对象主键
     * @param datatypeId 枚举
     * @return list
     * @author ya.liu
     * @Date 2018年10月31日
     */
    @Deprecated
    @RequestMapping(value = "measobjs/condition", method = RequestMethod.POST)
    public JSONObject getMeasObjByCondition(@RequestBody(required = false) MeasObjVo vo) {
        if (null == vo) {
            return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, null);
        }
        List<MeasObjDto> list = measObjService.getMeasObjByCondition(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**
     * convert to Json
     */
    private List<JSONObject> getJsonObjList(List<MeasObjAIParam> paramList) {
        List<JSONObject> list = new ArrayList<>();

        // 判断监测对象是否存在，如果不存在跳过
        for (MeasObjAIParam param : paramList) {
            if (param.getObjTypeName() == null)
                continue;
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("key", param.getObjTypeName());
            jsonobj.put("val", param.getCv());
            SectionDto dto = sectionService.getSectionById(param.getSectionId());
            jsonobj.put("location", dto == null ? "" : dto.getName());
            list.add(jsonobj);
        }
        return list;
    }
}




