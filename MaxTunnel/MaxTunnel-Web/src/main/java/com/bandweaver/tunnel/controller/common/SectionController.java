package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bandweaver.tunnel.common.biz.pojo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

import javafx.geometry.Point2D;

/**
 * 管舱段管理
 *
 * @author shaosen
 * @date 2018年6月21日
 */
@Controller
@ResponseBody
public class SectionController extends BaseController<Section>{

    @Autowired
    private SectionService sectionService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private MeasObjService measObjService;
    @Autowired
    private StoreTypeService storeTypeService;


    /**添加
     * @param name             管舱段名称
     * @param tunnelId         管廊id
     * @param storeId          管舱id
     * @param areaId           区域id
     * @param totalCableNumber 设计可安装管线总数
     * @param camera           相机视角（字符串）
     * @param startPoint		开始坐标 格式：112.494028,37.707195,6.130
     * @param endPoint			结束坐标 格式：112.4994028,37.706120,6.130
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @author shaosen
     * @date 2018年7月25日
     */
    @Deprecated
    @RequestMapping(value = "sections", method = RequestMethod.POST)
    public JSONObject add(@RequestBody Section section) {
        sectionService.add(section);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

    }

    @RequestMapping(value = "sections/create", method = RequestMethod.GET)
    public JSONObject create() {
        List<Store> stores = storeService.getList();
        List<AreaDto> areas = areaService.getList();

        for (Store store : stores){
            for (AreaDto area: areas) {
                SectionVo vo = new SectionVo();
                if (sectionService.getSectionByStoreAndArea(store.getId(),area.getId())!=null) continue;

                // 新加section
                Section section = new Section();
                section.setStoreId(store.getId());
                section.setAreaId(area.getId());
                section.setTotalCableNumber(10);
                section.setStartPoint("");
                section.setEndPoint("");
                section.setName(area.getName() + "-" + store.getName());
                section.setCamera("");
                section.setCrtTime(new Date());
                section.setTunnelId(area.getTunnelId());

                sectionService.add(section);
            }
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    
    /**检查名称是否重复 
     * @param name
     * @return   true:可添加 false：不可添加
     * @author shaosen
     * @Date 2018年10月9日
     */
    @Deprecated
    @RequestMapping(value="sections/ajax/{name}",method=RequestMethod.GET)
    public JSONObject checkName(@PathVariable String name) {
    	Section section = sectionService.getByName(name);
    	return success(section == null ? true : false);
    }
    
    
    /**删除section 
     * @param id sectionId
     * @return  {"msg":"请求成功","code":"200","data":{}} 
     * @author shaosen
     * @Date 2018年9月20日
     */
    @RequestMapping(value = "sections/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable Integer id) {
    	sectionService.delete(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

    }
    
    /**
     * 批量删除
     */
	@Override
	@RequestMapping(value="sections/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = new ArrayList<>();
		String[] arr = ids.split(",");
		for (String str : arr) {
			list.add(DataTypeUtil.toInteger(str));
		}
		sectionService.deleteBatch(list);
		  return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	

    /**更新 
     * @param section 
     * @return  {"msg":"请求成功","code":"200","data":{}}  
     * @author shaosen
     * @Date 2018年9月20日
     */
    @RequestMapping(value="sections",method=RequestMethod.PUT)
    public JSONObject update(@RequestBody Section section) {
    	sectionService.update(section);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

    }
    

    /**条件查询
     * @param name      支持模糊查询
     * @param storeId   管舱id
     * @param areaId    区域id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return list
     * @author shaosen
     * @date 2018年6月21日
     */
//    @RequestMapping(value = "sections/condition", method = RequestMethod.POST)
    @RequestMapping(value = "tunnels/stores/sections/condition", method = RequestMethod.POST)
    public JSONObject getSectionsByCondition(@RequestBody SectionVo vo) {
        List<SectionDto> list = sectionService.getSectionsByCondition(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**通过id获取dto详情
     * @param id 管舱段id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"1区-燃气仓","totalCableNumber":5,"camera":null,"startPoint":"112.494028,37.707195,6.130","endPoint":"112.4994028,37.706120,6.130","crtTime":1535611490000,"store":{"id":1,"name":"燃气仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":1,"name":"1区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}}
     * @author shaosen
     * @date 2018年6月21日
     */
    @RequestMapping(value = "sections/{id}", method = RequestMethod.GET)
    public JSONObject getById(@PathVariable Integer id) {
        SectionDto dto = sectionService.getSectionById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }

    /**通过ids获取dto列表
     * @param ids 管舱段id字符串，格式：1,2,3,4,.....
     * @return dto集合
     * @author shaosen
     * @date 2018年7月24日
     */
    @RequestMapping(value = "sections/batch/{ids}", method = RequestMethod.GET)
    public JSONObject getDtoListByIds(@PathVariable String ids) {
    	
        List<SectionDto> list = new ArrayList<>();
        String[] arr = ids.split(",");
        for (String id : arr) {
            Integer sectionId = DataTypeUtil.toInteger(id);
            SectionDto dto = sectionService.getSectionById(sectionId);
            list.add(dto);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**通过storeId查询管舱段dto列表
     * @param id 管舱id
     * @return dto集合
     * @author shaosen
     * @date 2018年6月21日
     */
//    @RequestMapping(value = "stores/{id}/sections", method = RequestMethod.GET)
    @RequestMapping(value = "tunnels/stores/{id}/sections", method = RequestMethod.GET)
    public JSONObject getSectionsByStoreId(@PathVariable Integer id) {

        List<SectionDto> list = sectionService.getSectionsByStoreId(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**通过areaId查询管舱段dto列表，并根据管舱类型进行分类
     * @param id 区域id
     * @return {"msg":"请求成功","code":"200","data":[{"val":[],"key":"燃气仓"},{"val":[{"id":67,"name":"1区-综合仓","totalCableNumber":5,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1535611491000,"store":{"id":2,"name":"综合仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":2,"name":"综合仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":1,"name":"1区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}],"key":"综合仓"},{"val":[{"id":45,"name":"1区-电力仓","totalCableNumber":20,"camera":null,"startPoint":"112.494079,37.707195,6.130","endPoint":"112.494079,37.706120,6.130","crtTime":1535611491000,"store":{"id":3,"name":"电力仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":3,"name":"电力仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":1,"name":"1区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}],"key":"电力仓"},{"val":[{"id":23,"name":"1区-污水仓","totalCableNumber":5,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1535611491000,"store":{"id":4,"name":"污水仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":4,"name":"污水仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":1,"name":"1区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}],"key":"污水仓"}]}
     * @author shaosen
     * @date 2018年6月21日
     */
//    @RequestMapping(value = "areas/{id}/sections", method = RequestMethod.GET)
    @RequestMapping(value = "tunnels/areas/{id}/sections", method = RequestMethod.GET)
    public JSONObject getSectionsByAreaId(@PathVariable Integer id) {

        List<SectionDto> list = sectionService.getSectionsByAreaId(id);

        List<JSONObject> jsonList = new ArrayList<>();
        List<StoreType> li = storeTypeService.getList();
        for (StoreType e : li) {
            JSONObject json = new JSONObject();
            json.put("key", e.getName());

            List<SectionDto> valList = new ArrayList<>();
            for (SectionDto sectionDto : list) {
                if (sectionDto.getStore().getStoreTypeId() == e.getId()) {
                    valList.add(sectionDto);
                }
            }
            json.put("val", valList);
            jsonList.add(json);
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, jsonList);
    }


    /**根据sectionId查询所在的管廊信息
     * @param id 管舱段id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"凤岭北路"}}
     * @author shaosen
     * @date 2018年7月9日
     */
    @RequestMapping(value = "tunnels/sections/{id}", method = RequestMethod.GET)
    public JSONObject getTunnelBySectionId(@PathVariable Integer id) {
        TunnelSimpleDto dto = sectionService.getTunnelBySectionId(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }


    /**管舱段分页查询
     * @param name     管舱段名称（支持模糊查询）
     * @param tunnelId 管廊id
     * @param storeId  管舱id
     * @param areaId   区域id
     * @param pageNum  必须
     * @param pageSize 必须
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return {"msg":"请求成功","code":"200","data":{"total":22,"list":[{"id":5,"name":"5区-燃气仓","totalCableNumber":5,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1535611491000,"store":{"id":1,"name":"燃气仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":5,"name":"5区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}},{"id":6,"name":"6区-燃气仓","totalCableNumber":5,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1535611491000,"store":{"id":1,"name":"燃气仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":6,"name":"6区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":11,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"firstPage":1,"lastPage":8}}
     * @author shaosen
     * @throws Exception 
     * @Date 2018年7月27日
     */
    @RequestMapping(value = "sections/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody SectionVo vo) throws Exception {
        /*if (vo.getStoreId() == null && vo.getAreaId() == null) {
            throw new Exception("管舱ID和区域ID必填其中一个！");
        }*/
        PageInfo<SectionDto> pageInfo = sectionService.dataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    /**
     * 根据storeId和areaId查询section列表
     *
     * @param storeId 管舱id （storeId和areaId必须填其中一个，可以都填）
     * @param areaId  区域id
     * @return {"msg":"请求成功","code":"200","data":[{"id":55,"name":"综合仓仓段13","storeId":null,"areaId":null,"totalCableNumber":null,"camera":null,"crtTime":null},{"id":58,"name":"综合仓仓段16","storeId":null,"areaId":null,"totalCableNumber":null,"camera":null,"crtTime":null},{"id":57,"name":"综合仓仓段15","storeId":null,"areaId":null,"totalCableNumber":null,"camera":null,"crtTime":null},{"id":54,"name":"综合仓仓段12","storeId":null,"areaId":null,"totalCableNumber":null,"camera":null,"crtTime":null},{"id":56,"name":"综合仓仓段14","storeId":null,"areaId":null,"totalCableNumber":null,"camera":null,"crtTime":null}]}
     * @throws
     * @author shaosen
     * @Date 2018年7月27日
     */
    @RequestMapping(value = "tunnels/stores/areas/sections/condition", method = RequestMethod.POST)
    public JSONObject getSectionListByStoreAndArea(@RequestBody SectionVo vo) {
        if (vo.getStoreId() == null && vo.getAreaId() == null) {
            throw new RuntimeException("管舱ID和区域ID必填其中一个！");
        }
        List<Section> list = sectionService.getSectionListByStoreAndArea(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**
     * 通过经纬度确定所在的舱段
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param height    高度
     * @return {"msg":"请求成功","code":"200","data":{"sectionInfo":{"endPoint":{"latitude":"37.706120","longitude":"112.4940792899","height":"6.130"},"startPoint":{"latitude":"37.707195","longitude":"112.4940791288","height":"6.130"},"id":2,"point":{"X":2.033301630365462E7,"Y":1.080982264123373E7,"Z":6.63}},"moInfo":[{"cv":52.3145960835601,"dataTypeName":"模拟量输入","dataType":1,"id":1002},{"cv":43.3145960835601,"dataTypeName":"模拟量输入","dataType":1,"id":4003},{"cv":44.3145960835601,"dataTypeName":"模拟量输入","dataType":1,"id":4004},{"cv":1.0,"dataTypeName":"状态量","dataType":3,"id":41005},{"cv":0.0,"dataTypeName":"状态量","dataType":3,"id":41006},{"cv":1.0,"dataTypeName":"状态量","dataType":3,"id":41007},{"cv":1.0,"dataTypeName":"状态量","dataType":3,"id":41008},{"cv":0.0,"dataTypeName":"视频","dataType":7,"id":7003},{"cv":0.0,"dataTypeName":"视频","dataType":7,"id":7004},{"cv":194.372869541593,"dataTypeName":"模拟量输入","dataType":1,"id":1057}]}}
     * @author shaosen
     * @Date 2018年8月25日
     */
    @RequestMapping(value = "sections/gps", method = RequestMethod.POST)
    public JSONObject getSectionByPoint(@RequestBody Map<String, Object> map) {

        //获取传过来的经纬度高度
        double longitude = DataTypeUtil.toDouble(map.get("longitude"));
        double latitude = DataTypeUtil.toDouble(map.get("latitude"));
        double height = DataTypeUtil.toDouble(map.get("height"));

        SectionDto resultDto = sectionService.getSectionByGPS(longitude, latitude, height);

        if (resultDto == null) {
            return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, null);
        }

        String[] arr = resultDto.getStartPoint().split(",");
        String[] arr2 = resultDto.getEndPoint().split(",");

        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        json1.put("longitude", arr[0]);
        json1.put("latitude", arr[1]);
        json1.put("height", arr[2]);
        JSONObject json2 = new JSONObject();
        json2.put("longitude", arr2[0]);
        json2.put("latitude", arr2[1]);
        json2.put("height", arr2[2]);
//        JSONObject json3 = new JSONObject();
//        json3.put("X", p4.getX());
//        json3.put("Y", p4.getY());
//        json3.put("Z", height);

        json.put("startPoint", json1);
        json.put("endPoint", json2);
//        json.put("point", json3);
        json.put("id", resultDto.getId());


        JSONObject result = new JSONObject();
        result.put("sectionInfo", json);


        //根据sectionId查询所有的监测对象
        List<MeasObjDto> moList = measObjService.getMeasObjBySectionId(resultDto.getId());
        List<JSONObject> cvList = new ArrayList<>();
        for (MeasObjDto measObj : moList) {
            double cv = measObjService.getMeasObjCVByIdAndDataType(measObj.getId(), measObj.getDatatypeId());
            JSONObject j = new JSONObject();
            j.put("id", measObj.getId());
            j.put("dataType", measObj.getDatatypeId());
            j.put("dataTypeName", measObj.getDatatypeName());
            j.put("cv", cv);
            cvList.add(j);
        }
        result.put("moInfo", cvList);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }


    private List<Double> getListFromString(String str) {
        List<String> strList = Arrays.asList(str.split(","));
        List<Double> dList = new ArrayList<>();
        for (String s : strList) {
            Double d = DataTypeUtil.toDouble(s);
            dList.add(d);
        }
        return dList;
    }

	
	/*double dis;
    double a, b, c;

    a = GPSUtil.GetDistance(startLon, startLat, endLon, endLat);//section length
    LogUtil.info(" section length : " + a);
    b = GPSUtil.GetDistance(startLon, startLat, longitude, latitude);//length from start to point
    c = GPSUtil.GetDistance(endLon, endLat, longitude, latitude);//length from end to point
	
    if (c <= 0.000001 || b <= 0.000001) { dis = 0; }
    if (a <= 0.000001) { dis = b; }
    if (c * c >= a * a + b * b) { dis = b; }
    if (b * b >= a * a + c * c) {  dis = c; }

    double p = (a + b + c) / 2;// 半周长
    double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
    dis = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
    LogUtil.info(" get distance [" + dis + "] with sectionId [" + sectionDto.getId() + "]" );*/


}
