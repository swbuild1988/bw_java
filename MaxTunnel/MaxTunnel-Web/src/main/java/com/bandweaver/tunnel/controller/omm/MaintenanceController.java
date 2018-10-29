package com.bandweaver.tunnel.controller.omm;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bandweaver.tunnel.common.biz.itf.omm.MaintenanceOrderService;
import com.bandweaver.tunnel.common.biz.vo.omm.MaintenanceOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectLevel;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectStatus;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectType;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.omm.DefectDto;
import com.bandweaver.tunnel.common.biz.dto.omm.MaintenanceOrderDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.omm.DefectService;
import com.bandweaver.tunnel.common.biz.itf.omm.OrderService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.omm.Defect;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.pojo.omm.MaintenanceOrder;
import com.bandweaver.tunnel.common.biz.pojo.omm.Order;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@ResponseBody
public class MaintenanceController {
    @Autowired
    private DefectService defectService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MaintenanceOrderService maintenanceOrderService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private StaffService staffService;

    @RequestMapping(value = "defects", method = RequestMethod.POST)
    public JSONObject add(@RequestBody DefectDto defect) {
    	Section section = sectionService.getSectionByStoreAndArea(defect.getStore().getId(), defect.getArea().getId());
    	defect.setSectionId(section.getId());
        defectService.add(defect);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    @RequestMapping(value = "defects", method = RequestMethod.PUT)
    public JSONObject update(@RequestBody DefectDto defect) {
    	Section section = sectionService.getSectionByStoreAndArea(defect.getStore().getId(), defect.getArea().getId());
    	defect.setSectionId(section.getId());
        defectService.update(defect);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    @RequestMapping(value = "defects/testadd", method = RequestMethod.GET)
    public JSONObject defectTestAdd() {
        List<TunnelSimpleDto> l_tunnel = tunnelService.getList();

        for (TunnelSimpleDto tunnel : l_tunnel) {
            for (DefectType type : DefectType.values()) {
                for (DefectLevel level : DefectLevel.values()) {
                    if (level.getValue() == 0) continue;

                    int objId = type.equals(DefectType.Equipment) ? 1 : 0;
                    int count = (int) Math.round(Math.random() * 3) + 1;
                    for (int i = 0; i < count; i++) {
                        Defect defect = new Defect();
                        defect.setName("测试" + i + 1);
                        defect.setStatus(DefectStatus.None.getValue());
                        defect.setTunnelId(tunnel.getId());
                        defect.setType(type.getValue());
                        defect.setObjectId(objId);
                        defect.setLevel(level.getValue());
                        defect.setDescription(tunnel.getName() + "的" + type.getName() + "类型缺陷，严重性为" + level.getName() + ",这是第" + i + "个");
                        defect.setCreateTime(new Date());
                        defectService.add(defect);
                    }
                }
            }
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取管廊内未处理的各类缺陷的数目
     *
     * @return
     */
    @RequestMapping(value = "tunnels/defectcount", method = RequestMethod.GET)
    public JSONObject getCountOfDefect() {
        List<JSONObject> result = new ArrayList<>();

        List<TunnelSimpleDto> l_tunnel = tunnelService.getList();

        // 获取每个管廊的数据
        for (TunnelSimpleDto tunnel : l_tunnel) {
            JSONObject o = new JSONObject();
            o.put("key", tunnel.getName());
            // 获取管廊下所有缺陷类型的数目
            List<JSONObject> l_o = new ArrayList<>();
            for (DefectType type : DefectType.values()) {
                JSONObject o1 = new JSONObject();
                o1.put("key", type.getName());
                int count = defectService.getCountOfDefectByTunnelAndType(tunnel.getId(), type.getValue());
                o1.put("val", count);
                l_o.add(o1);
            }
            o.put("val", l_o);
            result.add(o);
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }

    /**
     * 获取管廊的所有缺陷，包括设备的
     *
     * @param tunnelId
     * @return
     */
    @RequestMapping(value = "tunnels/{tunnelId}/defects", method = RequestMethod.GET)
    public JSONObject getDefectsOfTunnel(@PathVariable("tunnelId") Integer tunnelId) {
        List<DefectDto> defectDtos = defectService.getDefectsOfTunnel(tunnelId);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, defectDtos);
    }

 
    /**
     * @Description: 分页查询
     * @param @param vo 封装了参数
     * @param @return   
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年6月12日
     */
    @RequestMapping(value = "defects/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody DefectVo vo) {
    	
    	PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<DefectDto> list = defectService.getDefectsByCondition(vo);
        
        for(DefectDto dd : list) {
        	SectionDto section = sectionService.getSectionById(dd.getSectionId());
        	AreaDto area = areaService.getAreasById(section.getAreaId());
        	StoreDto store = storeService.getStoreById(section.getStoreId());
        	dd.setArea(area);
        	dd.setStore(store);
        }
        
        PageInfo<DefectDto> pageInfo = new PageInfo<>(list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    
    
    
    /**
     * @Description: 添加测试数据
     * @param @return   
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年6月12日
     */
    @RequestMapping(value = "orders/testadd", method = RequestMethod.GET)
    public JSONObject orderTestAdd() {
        List<TunnelSimpleDto> l_tunnel = tunnelService.getList();

        for (TunnelSimpleDto tunnel : l_tunnel) {
            List<DefectDto> defectDtos = defectService.getDefectsOfTunnel(tunnel.getId());
            for (DefectDto defect : defectDtos) {
                if (defect.getId() % 2 == 1) continue;

                Order order = new Order();
                order.setId("Order" + tunnel.getId() + "-" + defect.getId());
                order.setTunnelId(tunnel.getId());
                order.setType(1);
                order.setDefectId(defect.getId());
                order.setWorkerId(0);
                order.setWorkTime(null);
                order.setStatus(1);

                orderService.add(order);

                Defect defect1 = (Defect)defect;
                defect1.setOrderId(order.getId());
                defectService.update(defect1);

            }
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * @param order
     * @return
     */
    @RequestMapping(value = "orders", method = RequestMethod.POST)
    public JSONObject addOrder(@RequestBody MaintenanceOrder order) {
    	maintenanceOrderService.add(order);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 结束任务，并修改维修工单信息
     * @param order
     * @return
     * @author liuya
     * @Date 2018年9月12日
     */
    @RequestMapping(value = "orders", method = RequestMethod.PUT)
    public JSONObject updateOrder(@RequestBody MaintenanceOrder order) {
    	
        LogUtil.info("修改维修工单信息");
    	maintenanceOrderService.update(order);
    	LogUtil.info("结束任务");
    	maintenanceOrderService.completeMaintenanceOrder(order.getId());
    	
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * @param tunnelId
     * @return
     */
    @RequestMapping(value = "tunnels/{tunnelId}/orders", method = RequestMethod.GET)
    public JSONObject getOrdersOfTunnel(@PathVariable("tunnelId") Integer tunnelId) {
        List<MaintenanceOrderDto> orderDtos = maintenanceOrderService.getMaintenanceOrderDtosByTunnel(tunnelId);
        LogUtil.info(orderDtos);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, orderDtos);
    }

    /**
     * @Description: 分页查询
     * @param @param vo
     * @param @return   
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年6月12日
     */
    @RequestMapping(value = "orders/datagird", method = RequestMethod.POST)
    public JSONObject dataGird(@RequestBody MaintenanceOrderVo vo) {

    	PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<MaintenanceOrderDto> list = maintenanceOrderService.getMaintenanceOrderDtosByCondition(vo);
        PageInfo<MaintenanceOrderDto> pageInfo = new PageInfo<>(list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
    /**
     * 获取维修工单详情
     * @param id
     * @return
     * @author liuya
     * @Date 2018年9月12日
     */
    @RequestMapping(value = "orders/{id}", method = RequestMethod.GET)
    public JSONObject getOrderById(@PathVariable("id") Integer id) {
    	MaintenanceOrderDto order = maintenanceOrderService.getOrder(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, order);
    }

    /**
     * 设定维修人员
     *
     * @param id
     * @param manId
     * @return
     */
    @RequestMapping(value = "maintenance-order/{id}/maintenance-person/{manId}/remark/{remark}", method = RequestMethod.GET)
    public JSONObject setMaintenancePerson(@PathVariable("id") int id, @PathVariable("manId") int manId){
        maintenanceOrderService.setMaintenancePerson(id, manId);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 备注，设定维修人员
     * @param id
     * @param manId
     * @param remark
     * @return
     * @author liuya
     * @Date 2018年9月11日
     */
    @RequestMapping(value = "maintenance-orders", method = RequestMethod.PUT)
    public JSONObject setMaintenancePersonAndRemark(@RequestBody JSONObject obj) {
    	Integer id = obj.getInteger("id");
    	Integer manId = obj.getInteger("manId");
    	String remark = obj.getString("remark");
    	StaffDto dto = staffService.getDtoById(manId);
    	maintenanceOrderService.setMaintenancePersonAndRemark(id, dto.getAccountId(), remark);
    	LogUtil.info("维修工单表添加维修人");
    	MaintenanceOrder maintenanceOrder = maintenanceOrderService.getOrder(id);
    	maintenanceOrder.setWorker(manId);
    	maintenanceOrderService.update(maintenanceOrder);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 完结维修工单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "maintenance-order/{id}/complete", method = RequestMethod.GET)
    public JSONObject setMaintenancePerson(@PathVariable("id") int id){
        maintenanceOrderService.completeMaintenanceOrder(id);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 获取各管廊缺陷数
     * @return {"msg":"请求成功","code":"200","data":[{"key":"凤岭北路","val":10},{"key":"长虹路","val":12}]}
     * @author liuya
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "tunnel/defect-count",method = RequestMethod.GET)
    public JSONObject getDefectCountByTunnel () {
    	List<InspectionPlan> defectCount = defectService.getDefectCountByTunnelId();
    	List<JSONObject> list = new ArrayList<>();
    	for(InspectionPlan defect : defectCount) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("key", defect.getName());
        	jsonObject.put("val", null == defect.getGroupId()? 0 : defect.getGroupId());
        	list.add(jsonObject);
    	}
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 分页，条件查询缺陷列表
     * @param defectVo 参数封装
     * @return	{"msg":"请求成功","code":"200","data":{"total":2,"pageNum":1,"pageSize":10},"list":[]}
     * @author liuya
     * @Date 2018年8月29日
     */
    @RequestMapping(value = "defects/list",method = RequestMethod.POST)
    public JSONObject getAllDefects(@RequestBody DefectVo defectVo) {
    	PageInfo<DefectDto> pageInfo = defectService.dataGrid(defectVo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
     
    /**
     * 获取缺陷详情
     * @param id
     * @return
     * @author liuya
     * @Date 2018年9月5日
     */
    @RequestMapping(value = "defects/{id}",method=RequestMethod.GET)
    public JSONObject getDefectDto(@PathVariable("id") Integer id) {
    	DefectDto defect = defectService.getDefectDto(id);
    	SectionDto section = sectionService.getSectionById(defect.getSectionId());
    	AreaDto area = areaService.getAreasById(section.getAreaId());
    	StoreDto store = storeService.getStoreById(section.getStoreId());
    	defect.setArea(area);
    	defect.setStore(store);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, defect);
    }
    
    /**
     * 获取所有缺陷信息
     * @return
     * @author liuya
     * @Date 2018年9月6日
     */
    @RequestMapping(value = "defects/list",method = RequestMethod.GET)
    public JSONObject getAllDefects() {
    	DefectVo vo = new DefectVo();
    	List<DefectDto> list = defectService.getDefectsByCondition(vo);
    	for(DefectDto defect : list) {
    		if(defect.getType() == 1) {
    			SectionDto section = sectionService.getSectionById(defect.getSectionId());
    			String startPoint = section.getStartPoint();
    			String endPoint = section.getEndPoint();
    			if(startPoint == null || endPoint == null)
    				continue;
    			String [] start = startPoint.split(",");
    			String [] end = endPoint.split(",");
    			if(start.length > 1 && end.length > 1) {
    				Double lon = (Double.valueOf(start[0]) + Double.valueOf(end[0])) / 2;
        			Double lat = (Double.parseDouble(start[1]) + Double.parseDouble(end[1])) / 2;
        			defect.setLongitude(lon.toString());
        			defect.setLatitude(lat.toString());
    			}
    			defect.setObjectId(null);
    		}
    		defect.setEquipmentType(0);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 通过维修工单的id获取缺陷信息
     * @param id
     * @return
     * @author liuya
     * @Date 2018年9月10日
     */
    @RequestMapping(value = "orders/{id}/defect",method=RequestMethod.GET)
    public JSONObject getDefectByProcessInstanceId(@PathVariable("id") Integer id) {
    	MaintenanceOrder mo = maintenanceOrderService.getOrder(id);
    	DefectDto defect = defectService.getDefectDto(mo.getDefectId());
    	SectionDto section = sectionService.getSectionById(defect.getSectionId());
    	AreaDto area = areaService.getAreasById(section.getAreaId());
    	StoreDto store = storeService.getStoreById(section.getStoreId());
    	defect.setArea(area);
    	defect.setStore(store);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, defect);
    }
    /**
     * 将图片输出流直接返回给前端
     * @param id  维修工单id
     * @param response
     * @throws FileNotFoundException
     * @throws IOException
     * @author liuya
     * @Date 2018年9月13日
     */
    @RequestMapping(value = "maintenance-order/{id}/activiti-png", method = RequestMethod.GET)
    public void getPng(@PathVariable Integer id, HttpServletResponse response) throws FileNotFoundException, IOException {
    	MaintenanceOrder mo = maintenanceOrderService.getOrder(id);
        if (mo == null)
            throw new RuntimeException("获取不到id=" + id + "的维修工单记录");
        activitiService.getImageByProcessInstance(mo.getProcessInstanceId(), response);
    }
    
}
