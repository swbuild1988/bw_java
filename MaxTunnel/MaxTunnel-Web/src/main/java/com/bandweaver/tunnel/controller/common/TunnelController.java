package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.dto.*;
import com.bandweaver.tunnel.common.biz.itf.*;

import org.activiti.engine.impl.variable.DateType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.MonitorTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIParam;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
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
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private MeasObjService measObjService;


    
    /**添加管廊
     * @param name             管廊名称
     * @param length           管廊长度（数值）
     * @param responsibilityId 负责人id（员工表）
     * @param constructId      建筑单位id（公司表）
     * @param operationId      运营单位（公司表）
     * @param camera           相机视角（字符串）
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
        String[] arr = ids.split(",");
        List<Integer> id_list = new ArrayList<>();
        for (String str : arr) {
            id_list.add(DataTypeUtil.toInteger(str));
        }
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
            List<StoreDto> storeList = storeService.getStoresByTunnelId(dto.getId());
            //查询防火区信息
            List<AreaDto> areaList = areaService.getAreasByTunnelId(dto.getId());

            List<JSONObject> listTwo = new ArrayList<>();
            for (StoreDto storeDto : storeList) {
                JSONObject jsonTwo = new JSONObject();
                jsonTwo.put("name", storeDto.getName());
                jsonTwo.put("id", storeDto.getId());
                jsonTwo.put("camera", storeDto.getCamera());
                jsonTwo.put("sn", storeDto.getSn());
                
                List<JSONObject> listThree = new ArrayList<>();
                for (AreaDto areaDto : areaList) {
                    JSONObject jsonThree = new JSONObject();
                    jsonThree.put("name", areaDto.getName());
                    jsonThree.put("id", areaDto.getId());
                    jsonThree.put("camera", areaDto.getCamera());
                    jsonThree.put("sn", areaDto.getSn());
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

  
}
