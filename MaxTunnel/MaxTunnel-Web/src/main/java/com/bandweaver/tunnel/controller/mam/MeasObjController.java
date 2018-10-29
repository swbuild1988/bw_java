package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
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
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

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

    /**
     * 添加测试数据
     */
    @RequestMapping(value = "measobjs/addbatch", method = RequestMethod.GET)
    public JSONObject addTestData() {

        List<MeasObj> objList = new ArrayList<>();
        List<MeasObjAI> aiList = new ArrayList<>();
        List<MeasValueAI> aiValList = new ArrayList<>();
        List<MeasObjDI> diList = new ArrayList<>();
        List<MeasObjSO> soList = new ArrayList<>();

        int i = 2000;

        List<TunnelSimpleDto> tunnelList = tunnelService.getList();
        for (TunnelSimpleDto tunnelSimpleDto : tunnelList) {
            List<StoreDto> storeList = storeService.getStoresByTunnelId(tunnelSimpleDto.getId());
            for (StoreDto storeDto : storeList) {
                List<SectionDto> sectionList = sectionService.getSectionsByStoreId(storeDto.getId());
                for (SectionDto sectionDto : sectionList) {

                    MeasObj obj = new MeasObj();
                    obj.setId(i++);
                    obj.setTunnelId(tunnelSimpleDto.getId());
                    obj.setStoreId(storeDto.getId());
                    obj.setSectionId(sectionDto.getId());
//                    obj.setDatatypeId((int) (Math.random() * 2) + 1);
                    obj.setDatatypeId(1);
                    if (obj.getDatatypeId() == DataType.AI.getValue()) {
//                    	obj.setObjtypeId((int) (Math.random() * 6) + 1);
                        obj.setObjtypeId((int) (Math.random() * 4) + 31);

                        MeasObjAI measObjAI = new MeasObjAI();
                        measObjAI.setId(obj.getId());
                        measObjAI.setRefreshTime(new Date());
                        measObjAI.setCV((double) (Math.random() * 200));
                        aiList.add(measObjAI);
                        
                        //valueai
                        for (int j = 1; j <= 10 ; j++) {
                        	 MeasValueAI valueAI = new MeasValueAI();
                             valueAI.setObjectId(obj.getId());
                             valueAI.setTime(DateUtil.getFrontDay(new Date(),j));
                             valueAI.setCV(i*(10-j)*0.1);
                             aiValList.add(valueAI);
						}
                       
                        
                    } else if (obj.getDatatypeId() == DataType.DI.getValue()) {
                        obj.setObjtypeId((int) (Math.random() * 2) + 10);

                        MeasObjDI measObjDI = new MeasObjDI();
                        measObjDI.setId(obj.getId());
                        measObjDI.setRefreshTime(new Date());
                        measObjDI.setCV((int) (Math.random() * 2) == 0 ? false : true);
                        diList.add(measObjDI);

                    } else if (obj.getDatatypeId() == DataType.SO.getValue()) {
                        obj.setObjtypeId(20);

                        MeasObjSO measObjSO = new MeasObjSO();
                        measObjSO.setId(obj.getId());
                        measObjSO.setRefreshTime(new Date());
                        measObjSO.setCV(" ");
                        soList.add(measObjSO);
                    }
                    obj.setName("监测对象" + i);
                    obj.setActived(true);
                    obj.setDescription("");
                    obj.setLongitude((double) Math.random() + "");
                    obj.setLatitude((double) Math.random() + "");
                    obj.setHeight((double) Math.random() + "");
                    obj.setDeviation((double) Math.random());
                    objList.add(obj);

                }

            }
        }

        LogUtil.info("objList : " + objList.size());
        LogUtil.info("aiList : " + aiList.size());
        LogUtil.info("diList : " + diList.size());
        LogUtil.info("aiValList : " + aiValList.size());
        measObjService.addTestData(objList, aiList, diList,aiValList);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**************************************************************************************************/
    /**************************************************************************************************/
    /**************************************************************************************************/

    /**添加obj
     * @param   obj
     * @return 
     * @author shaosen
     * @date 2018年5月28日
     */
    @RequestMapping(value = "measobjs", method = RequestMethod.POST)
    public JSONObject addObj(@RequestBody MeasObj obj) {
        measObjModuleCenter.insertMeasObj(obj);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**批量添加
     * @param   list
     * @return JSONObject
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "measobjs/batch", method = RequestMethod.POST)
    public JSONObject addObjBatch(@RequestBody List<MeasObj> list) {
        for (MeasObj obj : list) {
            measObjModuleCenter.insertMeasObj(obj);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**更新obj 
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

    /** 批量修改
     * @param   list
     * @return JSONObject
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "measobjs/list", method = RequestMethod.PUT)
    public JSONObject editObjBatch(@RequestBody List<MeasObj> list) {
        List<MeasObj> result = new ArrayList<>();
        for (MeasObj obj : list) {
            measObjModuleCenter.updateMeasObj(obj);
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

    }


 
    /**根据id查询obj 
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

    /**根据id集合获取list
     * @param   ids
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


    /**获取所有obj
     * @return JSONObject
     * @author shaosen
     * @date 2018年6月7日
     */
    @RequestMapping(value = "measobjs", method = RequestMethod.GET)
    public JSONObject getAllMeasObjList() {
        List<MeasObj> list = measObjModuleCenter.getMeasObjs();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**根据经纬度和高度查询50米空间内的所有数据
     * @param   longitude
     * @param   latitude
     * @param   height
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
            double a = Math.pow(distance,2);
            double b = Math.pow(differenceHeight,2);
            
            if (a + b <= 50.0 * 50.0) {
            	LogUtil.info(" Distance : " +  Math.sqrt(a+b));
                resultList.add(measObj);
            }
        }
        LogUtil.info("共返回【"+resultList.size()+"】个坐标");
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, resultList);

    }


    /**监测对象分页查询
     * @param sectionId 管舱段id
     * @param storeId 	管舱id
     * @param areaId	区域id
     * @param tunnelId	管廊id
     * @param name		监测对象名称（支持模糊查询）
     * @param datatypeId 	枚举
     * @param objtypeIds	list，格式：[1,2,3,4...]
     * @param pageNum 必须
     * @param pageSize 必须
     * @return {"msg":"请求成功","code":"200","data":{"total":103,"list":[{"id":1001,"tunnelId":1,"storeId":1,"areaId":1,"sectionId":1,"name":"温度监测仪1","datatypeId":1,"datatypeName":"模拟量输入","objtypeId":1,"objtypeName":"温度","actived":true,"description":null,"longitude":"0.003534597099157488","latitude":"0.18009738011735688","height":"0.5474121265387349","deviation":0.460808326715716,"cv":51.3145960835601},{"id":1002,"tunnelId":1,"storeId":3,"areaId":1,"sectionId":45,"name":"温度监测仪2","datatypeId":1,"datatypeName":"模拟量输入","objtypeId":1,"objtypeName":"温度","actived":true,"description":null,"longitude":"0.4386016987524316","latitude":"0.48531866384309663","height":"0.6030770379809333","deviation":0.28828624319183,"cv":52.3145960835601}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":52,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"firstPage":1,"lastPage":8}}
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
    
    

    /**根据measObjId和datatypeId查询cv值
     * @param   ids objId字符串，格式如1,2,3,4...
     * @param   datatypeId 
     * @return {"msg":"请求成功","code":"200","data":[{"id":41003,"tunnelId":1,"storeId":1,"sectionId":1,"name":"声光报警3","datatypeId":3,"objtypeId":41,"actived":true,"description":null,"longitude":"0.003534597099157488","latitude":"0.18009738011735688","height":"0.5474121265387349","deviation":0.460808326715716,"refreshTime":1531992210000,"cv":1.0}]}
     * @author shaosen
     * @date 2018年7月18日
     */
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
            	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,measObjModuleCenter.getMeasObjSIListByIds(idList));
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
     * @param vo 查询参数areaId，storeId
     * @return	
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "section/measobjs",method = RequestMethod.POST)
    public JSONObject getMeasObjByAreaAndStore(@RequestBody SectionVo vo) {
    	Section section = sectionService.getSectionByStoreAndArea(vo.getStoreId(), vo.getAreaId());
    	if(section == null) {
    		throw new RuntimeException("该仓和区段获取不到对象信息！");
    	}
    	List<MeasObjDto> ls = measObjService.getMeasObjBySectionId(section.getId());
    	List<Map<String,Object>> list = new ArrayList<>();
    	for(MeasObjDto obj : ls) {
    		Map<String,Object> map = new HashMap<>();
    		map.put("key", obj.getId());
    		map.put("val", obj.getName());
    		list.add(map);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }

}



