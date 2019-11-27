package com.bandweaver.tunnel.controller.common;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.dto.*;
import com.bandweaver.tunnel.common.biz.itf.*;

import com.bandweaver.tunnel.common.platform.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.TunnelStatus;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.common.TunnelRun;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.github.pagehelper.PageInfo;

/**
 * 管廊管理
 *
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class TunnelController extends BaseController<Tunnel> {
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SectionService sectionService;


    
    /**添加管廊
     * @param name             管廊名称
     * @param sn
     * @param length           管廊长度（数值）
     * @param responsibilityId 负责人id（员工表）
     * @param constructId      建筑单位id（公司表）
     * @param operationId      运营单位（公司表）
     * @param camera           相机视角（字符串）
     * @param maxviewConfigId  二级子系统id
     * @param status           管廊状态
     * @param crtTime
     * @param startPoint
     * @param endTime
     * @param direction
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @author shaosen
     * @date 2018年7月25日
     */
    @RequestMapping(value = "tunnels", method = RequestMethod.POST)
    public JSONObject add(@RequestBody Tunnel tunnel) {
        tunnelService.add(tunnel);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    
    /**检查名称是否重复
     * @param name 管廊名称
     * @return ture：可以添加  false:重复不可添加
     * @author shaosen
     * @Date 2018年10月9日
     */
    @RequestMapping(value = "tunnels/ajax/{name}", method = RequestMethod.GET)
    public JSONObject checkName(@PathVariable String name) {
        LogUtil.info("name:" + name);
        Tunnel tunnel = tunnelService.getByName(name);
        LogUtil.info("Get tunnel:" + tunnel);
        return success(tunnel == null ? true : false);
    }


    /**获取管廊列表(简单列表)
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"古城大街"},{"id":2,"name":"实验路"},{"id":3,"name":"经三路"},{"id":4,"name":"经二路"},{"id":5,"name":"纬三路"}]}
     * @author shaosen
     * @date 2018年6月7日
     */
    @RequestMapping(value = "tunnels", method = RequestMethod.GET)
    public JSONObject getList() {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, tunnels);
    }

    
    /**通过id获取管廊信息（包含负责人，建筑单位，运营单位信息）
     * @param id 管廊id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"古城大街","length":5000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611490000}}
     * @author shaosen
     * @date 2018年6月27日
     */
    @RequestMapping(value = "tunnels/{id}", method = RequestMethod.GET)
    public JSONObject getById(@PathVariable Integer id) {
        TunnelDto dto = tunnelService.getDtoById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }


    /**通过主键更新
     * @param tunnel 参数参考添加接口
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequestMapping(value = "tunnels", method = RequestMethod.PUT)
    public JSONObject update(@RequestBody Tunnel tunnel) {
        tunnelService.update(tunnel);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    
    /**通过主键删除
     * @param id
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequestMapping(value = "tunnels/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable Integer id) {
        tunnelService.delete(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    
    /**批量删除
     * @param ids id通过逗号拼接的字符串
     * @return
     * @author shaosen
     * @Date 2018年9月21日
     */
    @RequestMapping(value = "tunnels/batch/{ids}", method = RequestMethod.DELETE)
    public JSONObject deleteBatch(@PathVariable String ids) {

        LogUtil.info("ids : " + ids);
        List<Integer> id_list = CommonUtil.convertStringToList(ids);
        tunnelService.deleteBatch(id_list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**管廊分页查询
     * @param name             管廊名称（支持模糊查询）
     * @param responsibilityId 负责人id
     * @param constructId      建筑单位id
     * @param operationId      营运单位id
     * @param startTime
     * @param endTime
     * @param pageNum          必须
     * @param pageSize         必须
     * @return {"msg":"请求成功","code":"200","data":{"total":5,"list":[{"id":2,"name":"实验路","length":1000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":5,"name":"纬三路","length":5000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":4,"name":"经二路","length":1000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":3,"name":"经三路","length":1000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":1,"name":"古城大街","length":5000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611490000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequestMapping(value = "tunnels/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody TunnelVo vo) {
        PageInfo<TunnelDto> pageInfo = tunnelService.dataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }



    /**获取管廊树形结构
     * @return 
     * @author shaosen
     * @date 2018年7月25日
     */
    @RequestMapping(value = "tunnels/tree", method = RequestMethod.GET)
    public JSONObject getTree() {

        List<TunnelDto> dtoList = tunnelService.getDtoList();
        List<JSONObject> listOne = new ArrayList<>();
        for (TunnelDto dto : dtoList) {
            JSONObject jsonOne = new JSONObject();
            jsonOne.put("name", dto.getName());
            jsonOne.put("id", dto.getId());
            jsonOne.put("camera", dto.getCamera());
            jsonOne.put("sn", dto.getSn());
            // 查询仓信息
//            List<StoreDto> storeList = storeService.getStoresByTunnelId(dto.getId());
            //查询防火区信息
            List<AreaDto> areaList = areaService.getAreasByTunnelId(dto.getId());

            List<JSONObject> listTwo = new ArrayList<>();
            for (AreaDto area : areaList) {
                JSONObject jsonTwo = new JSONObject();
                jsonTwo.put("name", area.getName());
                jsonTwo.put("id", area.getId());
                jsonTwo.put("camera", area.getCamera());
                jsonTwo.put("sn", area.getSn());
                
                //查询仓段信息
                List<SectionDto> sectionList = sectionService.getSectionsByAreaId(area.getId());
                List<JSONObject> listThree = new ArrayList<>();
                for (SectionDto section : sectionList) {
                    JSONObject jsonThree = new JSONObject();
                    jsonThree.put("name", section.getName());
                    jsonThree.put("id", section.getId());
                    jsonThree.put("camera", section.getCamera());
                    jsonThree.put("list", new ArrayList<>());
                    listThree.add(jsonThree);
                }

                jsonTwo.put("list", listThree);
                listTwo.add(jsonTwo);
            }
            jsonOne.put("list", listTwo);
            listOne.add(jsonOne);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listOne);

    }

    /**
     * 获取每条管廊的具体信息
     * @return
     * @author ya.liu
     * @Date 2019年7月10日
     */
    @RequestMapping(value = "tunnels/all", method = RequestMethod.GET)
    public JSONObject getTunnels() {

        List<TunnelDto> dtoList = tunnelService.getDtoList();
        List<JSONObject> listOne = new ArrayList<>();
        for (TunnelDto dto : dtoList) {
            JSONObject jsonOne = new JSONObject();
            jsonOne.put("name", dto.getName());
            jsonOne.put("id", dto.getId());
            jsonOne.put("sn", dto.getSn());
            // 查询仓信息
            List<StoreDto> storeList = storeService.getStoresByTunnelId(dto.getId());
            //查询防火区信息
            List<AreaDto> areaList = areaService.getAreasByTunnelId(dto.getId());

            List<JSONObject> listTwo = new ArrayList<>();
            for (AreaDto area : areaList) {
                JSONObject jsonTwo = new JSONObject();
                jsonTwo.put("name", area.getName());
                jsonTwo.put("id", area.getId());
                jsonTwo.put("sn", area.getSn());
                
                listTwo.add(jsonTwo);
            }
            jsonOne.put("areas", listTwo);
            
            List<JSONObject> list = new ArrayList<>();
            for (StoreDto store : storeList) {
                JSONObject jsonTwo = new JSONObject();
                jsonTwo.put("name", store.getName());
                jsonTwo.put("id", store.getId());
                jsonTwo.put("sn", store.getSn());
                
                list.add(jsonTwo);
            }
            jsonOne.put("stores", list);
            listOne.add(jsonOne);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listOne);

    }
    
    /**获取指定管廊下共有多少管仓
     * @param id 管廊id
     * @return {"msg":"请求成功","code":"200","data":{"val":6,"key":"管仓数"}}
     * @throws @author shaosen
     * @date 2018年6月29日
     */
    @RequestMapping(value = "tunnels/{id}/stores/count", method = RequestMethod.GET)
    public JSONObject getStoresCountByTunnelId(@PathVariable Integer id) {

        List<StoreDto> storeList = storeService.getStoresByTunnelId(id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "管仓数");
        jsonObject.put("val", storeList.size());

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, jsonObject);
    }

    
    /**获取指定管廊下共有多少section(仓段)
     * @param id 管廊id
     * @return {"msg":"请求成功","code":"200","data":{"val":16,"key":"仓段数"}}
     * @throws @author shaosen
     * @date 2018年6月29日
     */
    @RequestMapping(value = "tunnels/{id}/sections/count", method = RequestMethod.GET)
    public JSONObject getSectionsCountByTunnelId(@PathVariable Integer id) {

        List<SectionDto> list = tunnelService.getSectionList(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "仓段数");
        jsonObject.put("val", list.size());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, jsonObject);
    }

    
    /**获取管廊总览 
     * @param id
     * @return   {"msg":"请求成功","code":"200","data":{"areaCount":20,"areaList":["3区","4区","5区","6区","7区","8区","9区","10区","11区","12区","13区","14区","15区","16区","17区","18区","19区","20区","21区","22区"],"storeCount":16,"storeList":["污水舱","电力舱","综合舱","燃气舱","设备间","污水舱进风口","电力舱进风口","综合舱进风口","燃气舱进风口","污水舱排风口","电力舱排风口","综合舱排风口","燃气舱排风口","燃气舱投料井","非燃气舱投料井","其他"],"construct":"中铁十二局","operation":"波汇科技"}}
     * @author shaosen
     * @Date 2018年12月28日
     */
    @RequestMapping(value="tunnels/{id}/detail",method=RequestMethod.GET)
    public JSONObject getTunnelInfo(@PathVariable Integer id) {
    	
    	JSONObject returnData = new JSONObject();
    	TunnelDto tunnel = tunnelService.getDtoById(id);
    	returnData.put("construct", tunnel.getConstruct() == null ? "" : tunnel.getConstruct().getName() );
    	returnData.put("operation", tunnel.getOperation().getName() == null ? "" : tunnel.getOperation().getName());
    	
    	List<String> storesName = new ArrayList<>();
    	List<String> areasName = new ArrayList<>();
    	List<StoreDto> storeList = storeService.getStoresByTunnelId(id);
    	storeList.forEach(s -> {
    		storesName.add(s.getName());
    	});
    	List<AreaDto> areaList = areaService.getAreasByTunnelId(id);
    	areaList.forEach(a -> {
    		areasName.add(a.getName());
    	});
    	
    	returnData.put("storeList", storesName);
    	returnData.put("storeCount", storeList.size());
    	returnData.put("areaList", areasName);
    	returnData.put("areaCount", areaList.size());
    	return CommonUtil.success(returnData);
    }
    
    
   
    /**管廊基本信息
     * @author shaosen
     * @date 2019年1月10日
     * @param   
     * @return {"msg":"请求成功","code":"200","data":[{"unit":"条","name":"运行中","value":2},{"unit":"条","name":"建设中","value":4},{"unit":"条","name":"规划中","value":0},{"unit":"个","name":"防火分区共","value":21},{"unit":"km","name":"总长度","value":13.5}]}  
     */
    @RequestMapping(value="tunnels/message",method=RequestMethod.GET)
    public JSONObject tunnelsMessage() {
    	
    	List<JSONObject> returnData = new ArrayList<>();
    	List<TunnelSimpleDto> list = tunnelService.getList().stream().filter(a->a.getShow()).collect(Collectors.toList());
    	
    	//获取各运行状态管廊个数
    	for (TunnelStatus tunnelStatus : TunnelStatus.values()) {
    		
    		List<TunnelSimpleDto> tunnelList = list.stream()
    				.filter(t -> t.getStatus().intValue() == tunnelStatus.getValue())
    				.collect(Collectors.toList());
    		
    		JSONObject tunnelJson = new JSONObject();
    		tunnelJson.put("name", tunnelStatus.getName());
    		tunnelJson.put("value", tunnelList.size());
    		tunnelJson.put("unit", "条");
    		tunnelJson.put("percent",list.isEmpty() ? "0" : MathUtil.div((double)(tunnelList.size()), (double)(list.size()), 4) * 100 + "%");
    		returnData.add(tunnelJson);
		}
    	
    	//获取防火分区总数
    	int totalArea = areaService.getTotalCount();
    	JSONObject areaJson = new JSONObject();
    	areaJson.put("name", "防火分区共");
    	areaJson.put("value", totalArea);
    	areaJson.put("unit", "个");
    	returnData.add(areaJson);
    	
    	//获取管廊总长度
    	Double sum = 0.0;
    	for (TunnelSimpleDto tunnelSimpleDto : list) {
			sum = MathUtil.add(sum, tunnelSimpleDto.getLength());
		}
		JSONObject lengthJson = new JSONObject();
		lengthJson.put("name", "总长度");
		lengthJson.put("value", sum/1000);
		lengthJson.put("unit", "km");
		returnData.add(lengthJson);
    	
    	return CommonUtil.success(returnData);
    }
    
    
    /**管廊运行信息
     * @author shaosen
     * @date 2019年1月10日
     * @param   
     * @return JSONObject  
     */
    @RequestMapping(value="tunnels/run-message",method=RequestMethod.GET)
    public JSONObject getDaysInfo() {
    	TunnelRun info = tunnelService.getTunnelRunInfo();
    	if(StringTools.isNullOrEmpty(info)) {
    		TunnelRun tr = new TunnelRun();
    		tr.setId(1);
    		tr.setRunDays(1);
    		tr.setSafeDyas(1);
    		info = tr;
    		tunnelService.addTunnelRun(tr);
    	}
    	JSONObject rtData = new JSONObject();
    	rtData.put("startTime", DateUtil.getFrontDay(new Date(), info.getRunDays()).getTime());
    	rtData.put("total", info.getRunDays());
    	rtData.put("safe", info.getSafeDyas());
    	return CommonUtil.success(rtData);
    }
  
}
