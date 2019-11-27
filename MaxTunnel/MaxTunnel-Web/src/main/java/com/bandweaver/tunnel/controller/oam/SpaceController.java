package com.bandweaver.tunnel.controller.oam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.TunnelStatus;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.itf.oam.CableTypeService;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.oam.CableDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.oam.CableContractService;
import com.bandweaver.tunnel.common.biz.itf.oam.CableSectionService;
import com.bandweaver.tunnel.common.biz.itf.oam.CableService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;


/**
 * 管廊空间管理
 *
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class SpaceController {

    @Autowired
    private CableService cableService;
    @Autowired
    private CableContractService cableContractService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private CableSectionService cableSectionService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private StoreTypeService storeTypeService;
    @Autowired
    private CableTypeService cableTypeService;


    /**
     * 通过sectionId获取管线列表(包含管线所在合同信息)
     *
     * @param @param id sectionId
     * @return {"msg":"请求成功","code":"200","data":[{"id":3,"cableName":"长途光缆","cableLength":100,"cableStatus":1,"cableStatusName":"运行中","cableLocation":"位置xxxxxxxxxx","contract":{"id":2,"name":"管线部署合同","customer":{"id":2,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000},"contact":"联系人0","tel":"13722228880","crtTime":1529979467000},"payType":2,"payTypeName":"季付"},"crtTime":1532238184000},{"id":7,"cableName":"骨架式光缆","cableLength":300,"cableStatus":2,"cableStatusName":"故障","cableLocation":"位置xxxxxxxxxx","contract":{"id":2,"name":"管线部署合同","customer":{"id":2,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000},"contact":"联系人0","tel":"13722228880","crtTime":1529979467000},"payType":2,"payTypeName":"季付"},"crtTime":1532238184000},{"id":8,"cableName":"铠装光缆","cableLength":400,"cableStatus":2,"cableStatusName":"故障","cableLocation":"位置xxxxxxxxxx","contract":{"id":1,"name":"管线部署合同","customer":{"id":3,"company":{"id":2,"name":"中国移动","crtTime":1529596800000},"contact":"联系人1","tel":"13722228881","crtTime":1529893067000},"payType":1,"payTypeName":"月付"},"crtTime":1532238184000},{"id":10,"cableName":"无金属光缆","cableLength":100,"cableStatus":2,"cableStatusName":"故障","cableLocation":"位置xxxxxxxxxx","contract":{"id":2,"name":"管线部署合同","customer":{"id":2,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000},"contact":"联系人0","tel":"13722228880","crtTime":1529979467000},"payType":2,"payTypeName":"季付"},"crtTime":1532238184000}]}
     * @throws
     * @author shaosen
     * @date 2018年7月26日
     */
    @RequestMapping(value = "tunnels/areas/sections/{id}/cables", method = RequestMethod.GET)
    public JSONObject getCableListBySectionId(@PathVariable Integer id) {
        List<CableDto> dtoList = cableService.getCableDtoListBySectionId(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dtoList);

    }

    /**
     * 通过管舱id获取管线信息
     * @param storeId
     * @return
     * @author ya.liu
     * @Date 2019年5月19日
     */
    @RequestMapping(value = "tunnels/stores/{storeId}/cables", method = RequestMethod.GET)
    public JSONObject getCableIdsBySectionIds(@PathVariable Integer storeId) {
    	List<CableDto> dtoList = new ArrayList<>();
    	
    	SectionVo vo = new SectionVo();
    	vo.setStoreId(storeId);
    	List<SectionDto> section = sectionService.getSectionsByCondition(vo);
    	if(section != null && section.size() > 0) {
    		List<Integer> sectionIds = new ArrayList<>();
    		for(SectionDto dto : section) {
    			sectionIds.add(dto.getId());
    		}
    		if(sectionIds.size() > 0) {
    			List<String> cableIds = cableService.getCableIdsBySectionIds(sectionIds);
    			if(cableIds != null && cableIds.size() > 0) {
    				dtoList = cableService.getListByIds(cableIds);
    			}
    		}
    	}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dtoList);

    }

    /**
     * 获取若干个section可用管线数统计
     *
     * @param ids sectionId字符串，使用逗号分隔 如：1,2,3,4,5....
     * @return {"msg":"请求成功","code":"200","data":[{"val":[{"val":5,"key":"设计管线数"},{"val":4,"key":"已用管线数"},{"val":1,"key":"可用管线数"}],"id":1},{"val":[{"val":5,"key":"设计管线数"},{"val":3,"key":"已用管线数"},{"val":2,"key":"可用管线数"}],"id":2},{"val":[{"val":5,"key":"设计管线数"},{"val":4,"key":"已用管线数"},{"val":1,"key":"可用管线数"}],"id":3}]}
     * @throws
     * @author shaosen
     * @date 2018年7月26日
     */
    @RequestMapping(value = "tunnels/areas/sections/batch/{ids}/cables-count", method = RequestMethod.GET)
    public JSONObject getSectionCableCount(@PathVariable String ids) {

        String[] arr = ids.split(",");
        if (arr.length == 0) {
            LogUtil.info("获取的id字符串为空！");
            return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
        }

        List<JSONObject> resultList = new ArrayList<>();
        for (String str : arr) {

            JSONObject result = new JSONObject();
            Integer id = DataTypeUtil.toInteger(str);
            result.put("id", id);

            SectionDto section = sectionService.getSectionById(id);
            if (section == null) {
                LogUtil.info("没有获取到id=[" + id + "]的section数据");
                continue;
            }

            List<JSONObject> list = new ArrayList<>();
            int totalCount = section.getTotalCableNumber() == null ? 0 : section.getTotalCableNumber().intValue();
            JSONObject json = new JSONObject();
            json.put("key", "设计管线数");
            json.put("val", totalCount);
            list.add(json);

            //求已用管线数
            int usedCount = cableSectionService.getCountBySectionId(id);
            JSONObject usedJson = new JSONObject();
            usedJson.put("key", "已用管线数");
            usedJson.put("val", usedCount);
            list.add(usedJson);

            //可用管线数
            JSONObject availableCount = new JSONObject();
            availableCount.put("key", "可用管线数");
            availableCount.put("val", totalCount - usedCount);
            list.add(availableCount);
            result.put("val", list);
            resultList.add(result);

        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, resultList);
    }


    /**
     * 统计管廊各舱管线使用情况
     *
     * @param id 管廊id
     * @return {"msg":"请求成功","code":"200","data":[{"可用管线数统计":104,"已用管线数统计":6,"设计管线数统计":110,"key":"燃气仓"},{"可用管线数统计":110,"已用管线数统计":0,"设计管线数统计":110,"key":"污水仓"},{"可用管线数统计":440,"已用管线数统计":0,"设计管线数统计":440,"key":"电力仓"},{"可用管线数统计":110,"已用管线数统计":0,"设计管线数统计":110,"key":"综合仓"}]}
     * @author shaosen
     * @Date 2018年9月4日
     */
    @RequestMapping(value = "tunnels/{id}/stores/cables-count", method = RequestMethod.GET)
    public JSONObject getStoreCableUsedCount(@PathVariable("id") Integer id) {

        List<JSONObject> result = new ArrayList<>();

        List<StoreDto> storeList = storeService.getStoresByTunnelId(id);
        for (StoreDto storeDto : storeList) {
            JSONObject json = new JSONObject();
            json.put("key", storeDto.getName());

            List<SectionDto> sectionList = sectionService.getSectionsByStoreId(storeDto.getId());
            // 每个section计算一根管线
//            int totalCableNumberSum = 0;
//            int usedCountSum = 0;
//            int availableCountSum = 0;
//            for (SectionDto sectionDto : sectionList) {
//                //设计管线数
//                int totalCableNumber = sectionDto.getTotalCableNumber() == null ? 0 : sectionDto.getTotalCableNumber();
//                //已用管线数
//                int usedCount = cableSectionService.getCountBySectionId(sectionDto.getId());
//                //可用管线数
//                int availableCount = totalCableNumber - usedCount;
//
//                totalCableNumberSum += totalCableNumber;
//                usedCountSum += usedCount;
//                availableCountSum += availableCount;
//
//            }
//            json.put("设计管线数统计", totalCableNumberSum);
//            json.put("已用管线数统计", usedCountSum);
//            json.put("可用管线数统计", availableCountSum);
            
            // 每个舱计算一根管线
            int totalCableNumber = 0;
            int usedCount = 0;
            int availableCount = 0;
            if(sectionList != null && sectionList.size() > 0) {
            	SectionDto sectionDto = sectionList.get(0);
                //设计管线数
                totalCableNumber = sectionDto.getTotalCableNumber() == null ? 0 : sectionDto.getTotalCableNumber();
                //已用管线数
                usedCount = cableSectionService.getCountBySectionId(sectionDto.getId());
                //可用管线数
                availableCount = totalCableNumber - usedCount;
            }
            json.put("设计管线数统计", totalCableNumber);
            json.put("已用管线数统计", usedCount);
            json.put("可用管线数统计", availableCount);
            result.add(json);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }


    /**
     * 管线基本信息
     *
     */
    @RequestMapping(value = "cables/message", method = RequestMethod.GET)
    public JSONObject getLinesMessage() {

        List<CableType> cableTypes = cableTypeService.getAllTypes();
        List<SectionDto> sections = sectionService.getAllSections();
        List<TunnelDto> tunnels = tunnelService.getDtoList();
        List<Store> sList = storeService.getList();
        List<StoreType> stList = storeTypeService.getList();
        List<JSONObject> returnData = new ArrayList<>();


        // 先初始化每个管仓类型下可放的管线种类
        HashMap<Integer, List<JSONObject>> storeType_cableMessage = new HashMap<Integer, List<JSONObject>>();
        for (CableType cableType : cableTypes) {
            JSONObject json = new JSONObject();
            json.put("id", cableType.getId());
            json.put("used", 0.0);          // 该类型管线已建长度
            json.put("total", 0.0);         // 该类型管线的总长度
            for (int storeTypeId : cableType.getInStoreTypeIds()) {
                if (!storeType_cableMessage.containsKey(storeTypeId)) {
                    storeType_cableMessage.put(storeTypeId, new ArrayList<>());
                }
                storeType_cableMessage.get(storeTypeId).add(json);
            }
        }

        // 遍历所有的section，将其对应的长度以及是否建设，家到对应的管线类型下
        for (SectionDto sectionDto : sections) {
            List<Store> tmp_stores = sList.stream().filter(a-> a.getId().intValue() == sectionDto.getStoreId().intValue()).collect(Collectors.toList());
            if (tmp_stores.size() < 1) continue;
            List<TunnelDto> tmp_tunnels = tunnels.stream().filter(a-> a.getId().intValue() == sectionDto.getTunnelId().intValue()).collect(Collectors.toList());
            if (tmp_tunnels.size() < 1) continue;

            Store tmp_store = tmp_stores.get(0);
            TunnelDto tmp_tunnel = tmp_tunnels.get(0);
            // 如果该section所在的管仓类型中可部署某管线
            if (storeType_cableMessage.containsKey(tmp_store.getStoreTypeId().intValue())) {
                List<JSONObject> tmp_jsons = storeType_cableMessage.get(tmp_store.getStoreTypeId().intValue());
                for (JSONObject object : tmp_jsons) {
                    object.put("total", object.getDoubleValue("total")+ sectionDto.getLength().doubleValue());
                    if (tmp_tunnel.getStatus().intValue() == TunnelStatus.RUNNING.getValue())
                        object.put("used", object.getDoubleValue("used")+ sectionDto.getLength().doubleValue());
                }
            }
        }

        // 将所有结果根据管线类型统计下
        for (CableType cableType : cableTypes) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", cableType.getTypeName());
            double used = 0.0;
            double total = 0.0;
            for (int storeTypeId : cableType.getInStoreTypeIds()) {
                List<JSONObject> tmp_jsons = storeType_cableMessage.get(storeTypeId);
                for (JSONObject tmp_json : tmp_jsons) {
                    if (tmp_json.getIntValue("id") == cableType.getId()) {
                        used += tmp_json.getDoubleValue("used");
                        total += tmp_json.getDoubleValue("total");
                    }
                }
            }
            jsonObject.put("used", used);
            jsonObject.put("value", total);
            jsonObject.put("unit", "km");
            jsonObject.put("percent", total == 0 ? 0: MathUtil.div(used, total, 2));

            returnData.add(jsonObject);
        }

        return CommonUtil.success(returnData);
    }


    @RequestMapping(value = "cabletypes", method = RequestMethod.GET)
    public JSONObject getCableTypes() {
        List<CableType> cableTypes = cableTypeService.getAllTypes();

        return CommonUtil.success(cableTypes);
    }

    @RequestMapping(value = "cabletypes", method = RequestMethod.POST)
    public JSONObject addCableType(@RequestBody CableType cableType){
        cableTypeService.insertRecord(cableType);

        return CommonUtil.success();
    }

    @RequestMapping(value = "cabletypes", method = RequestMethod.PUT)
    public JSONObject updateCableType(@RequestBody CableType cableType){
        cableTypeService.updateRecord(cableType);

        return CommonUtil.success();
    }


    @RequestMapping(value = "cabletypes/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteCableType(@PathVariable("id") int id){
        cableTypeService.deleteRecord(id);

        return CommonUtil.success();
    }

}
