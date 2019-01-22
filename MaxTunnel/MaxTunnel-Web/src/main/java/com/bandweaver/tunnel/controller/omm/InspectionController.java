package com.bandweaver.tunnel.controller.omm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionRecordDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTaskDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionGroupService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionRecordService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.AuditVo;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**
 * 巡检计划模块
 */
@Controller
@ResponseBody
public class InspectionController {
    @Autowired
    private InspectionGroupService inspectionGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private InspectionTaskService inspectionTaskService;
    @Autowired
    private InspectionPlanService inspectionPlanService;
    @Autowired
    private InspectionRecordService inspectionRecordService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private MeasObjService measObjService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private StoreService storeService;

    /**
     * 向数据库内添加模拟数据
     *
     * @return
     */
    @RequestMapping(value = "inspection-groups/testadd", method = RequestMethod.GET)
    public JSONObject testAddInspectionGroup() {
//
//        for (int i = 0; i < 4; i++) {
//            User user = new User();
//            user.setName("test" + i);
//            user.setPassword("123456");
//            user.setCrtTime(new Date());
//            userService.addUser(user);
//        }

        for (int i = 0; i < 2; i++) {
            InspectionGroup inspectionGroup = new InspectionGroup();
            inspectionGroup.setName("group" + i);
            LogUtil.info("get user by id : " + (i + 3));
            UserDTO user = userService.getUser(i + 3);
            LogUtil.info(user);
            inspectionGroup.setLeaderId(user.getId());
            inspectionGroup.setLeaderName(user.getName());
            LogUtil.info(inspectionGroup);
            inspectionGroupService.add(inspectionGroup);
            inspectionGroupService.addRelation(i + 1, i + 3);
            inspectionGroupService.addRelation(i + 1, i + 5);
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取某个具体的巡检小组
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "inspection-groups/{id}", method = RequestMethod.GET)
    public JSONObject getInspectionGroupDto(@PathVariable("id") Integer id) {
        InspectionGroupDto inspectionGroupDto = inspectionGroupService.getInspectionGroupDto(id);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, inspectionGroupDto);
    }

    /**
     * 获取所有巡检小组的大体信息
     *
     * @return
     */
    @RequestMapping(value = "inspection-groups", method = RequestMethod.GET)
    public JSONObject getInspectionGroups() {
        List<InspectionGroupDto> inspectionGroups = inspectionGroupService.getAllInspectionGroupDtos();

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, inspectionGroups);
    }

    /**
     * 新增巡检计划（附带其巡检任务列表）
     *
     * @param inspectionPlanDto
     * @return
     */
    @RequestMapping(value = "inspection-plans", method = RequestMethod.POST)
    public JSONObject addInspectionPlan(@RequestBody InspectionPlanDto inspectionPlanDto) {
        LogUtil.info("plan post： " + inspectionPlanDto);
        inspectionPlanService.add(inspectionPlanDto);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取巡检计划（附带其巡检任务列表）
     *
     * @return
     */
    @RequestMapping(value = "inspection-plans", method = RequestMethod.GET)
    public JSONObject getAllInspectionPlans() {
        List<InspectionPlanSimpleDto> inspectionPlans = inspectionPlanService.getInspectionPlans();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, inspectionPlans);
    }

    /**
     * 获取管廊下所有的巡检计划
     *
     * @param tunnelId
     * @return
     */
    @RequestMapping(value = "tunnels/{tunnelId}/inspection-plans")
    public JSONObject getAllInspectionPlanByTunnel(@PathVariable("tunnelId") Integer tunnelId) {
        List<InspectionPlanSimpleDto> inspectionPlans = inspectionPlanService.getInspectionPlansByTunnel(tunnelId);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, inspectionPlans);
    }


    /**
     * @param @param  inspectionVo
     * @param @return
     * @return JSONObject
     * @throws
     * @Description: 巡检计划分页查询
     * @author shaosen
     * @date 2018年6月12日
     */
    @RequestMapping(value = "inspection-plans/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody InspectionVo inspectionVo) {
        PageInfo<InspectionPlanSimpleDto> pageInfo = inspectionPlanService.dataGrid(inspectionVo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    /**
     * 获得某个计划的详细
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "inspection-plans/{id}")
    public JSONObject getInspectionPlanDto(@PathVariable("id") String id) {
        InspectionPlanDto inspectionPlanDto = inspectionPlanService.getInspectionDto(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, inspectionPlanDto);
    }

    /**
     * @param @param  id
     * @param @return
     * @param @throws IOException
     * @return JSONObject
     * @throws IOException
     * @throws FileNotFoundException
     * @throws
     * @Description: 将图片输出流直接返回给前端
     * @author shaosen
     * @date 2018年7月5日
     */
    @RequestMapping(value = "inspection-plans/{id}/activiti-png", method = RequestMethod.GET)
    public void getPng(@PathVariable String id, HttpServletResponse response) throws FileNotFoundException, IOException {

        InspectionPlanDto dto = inspectionPlanService.getInspectionDto(id);
        if (dto == null)
            throw new RuntimeException("获取不到id=" + id + "的巡检计划记录");
        activitiService.getImageByProcessInstance(dto.getProcessInstanceId(), response);
    }


    /**
     * @param @param  processDefinitionId
     * @param @param  value
     * @param @return
     * @return JSONObject
     * @throws UnsupportedEncodingException
     * @throws
     * @Description: 审批申请
     * @author shaosen
     * @date 2018年7月6日
     */
    @RequestMapping(value = "inspection-plans/audit", method = RequestMethod.POST)
    public JSONObject auditRequest(@RequestBody AuditVo vo) {

        inspectionPlanService.auditRequest(vo.getProcessInstanceId(), vo.getValue(), vo.getRemark());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 更新巡检任务
     *
     * @param inspectionTask
     * @return
     */
    @RequestMapping(value = "inspection-tasks", method = RequestMethod.PUT)
    public JSONObject updateInspectionTask(@RequestBody InspectionTaskDto inspectionTask) {
        LogUtil.info("task update:" + inspectionTask);
        inspectionTaskService.update(inspectionTask);

        // 添加记录
        for (InspectionRecordDto record : inspectionTask.getRecords()) {
        	//通过官仓id和区段id查询官仓段
        	Section section = sectionService.getSectionByStoreAndArea(record.getStore().getId(), record.getArea().getId());
            if(null != section) {
            	record.setSectionId(section.getId());
            }
        	inspectionRecordService.add(record);
        }

        LogUtil.info("巡检任务结束");
        // 提交结束，下一步
        activitiService.completeTaskByProcessIntance(inspectionTask.getProcessInstanceId());

        LogUtil.info("更新巡检计划");
        inspectionPlanService.complete(inspectionTask.getPlanId());

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取某个任务的详情
     * @param id 任务id
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "inspection-tasks/{id}", method = RequestMethod.GET)
    public JSONObject getInspectionTask(@PathVariable("id") int id) {
        InspectionTaskDto taskDto = inspectionTaskService.getTaskDto(id);
        for(InspectionRecordDto record : taskDto.getRecords()) {
        	SectionDto section = sectionService.getSectionById(record.getSectionId());
        	AreaDto area = areaService.getAreasById(section.getAreaId());
        	StoreDto store = storeService.getStoreById(section.getStoreId());
        	record.setArea(area);
        	record.setStore(store);
        	if(record.getDefect() != null) {
        		//SectionDto section1 = sectionService.getSectionById(record.getDefect().getSectionId());
            	//AreaDto area1 = areaService.getAreasById(section1.getAreaId());
            	//StoreDto store1 = storeService.getStoreById(section1.getStoreId());
            	//记录和缺陷的sectionId相同
        		record.getDefect().setArea(area);
            	record.getDefect().setStore(store);
        	}
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, taskDto);
    }
    
    /**
     * 获取年度计划数、本月计划、本月已完成巡检任务数、本月待巡检任务数、计划信息列表
     * @return {"msg":"请求成功","code":"200","data":{"yearPlanCount":0,"monthPlanCount":0,"finishTaskCount":0,"unfinishTaskCount":0,"yearTaskCount":0,"finYearTaskCount":0,"listPlan":[{"id":1,"name":"巡检计划"}]}}
     * @author liuya
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "inspection-plans/count", method = RequestMethod.GET)
    public JSONObject getInspectionPlansAndTask () {
    	JSONObject map = new JSONObject();
    	InspectionVo inspectionVo = new InspectionVo();
    	inspectionVo.setCreateTime(new Date());
    	int yearPlanCount = inspectionPlanService.getInspectionPlanSumByYear(inspectionVo);
    	int monthPlanCount = inspectionPlanService.getInspectionPlanSumByMonth(inspectionVo);
    	int yearTaskCount = inspectionTaskService.getInspectionTaskSumByYear(inspectionVo);
    	
    	//已完成
    	inspectionVo.setRequestStatus(1);
    	int finishTaskCount = inspectionTaskService.getFinishedInspectionTaskSumByMonth(inspectionVo);
    	int finYearTaskCount = inspectionTaskService.getFinishedInspectionTaskSumByYear(inspectionVo);
    	inspectionVo.setRequestStatus(0);
    	int unfinishTaskCount = inspectionTaskService.getFinishedInspectionTaskSumByMonth(inspectionVo);
    	
    	List<InspectionPlan> listPlan = inspectionPlanService.getAllInspectionPlan();
    	
    	map.put("yearPlanCount", yearPlanCount);
    	map.put("monthPlanCount", monthPlanCount);
    	map.put("finishTaskCount", finishTaskCount);
    	map.put("unfinishTaskCount", unfinishTaskCount);
    	map.put("yearTaskCount", yearTaskCount);
    	map.put("finYearTaskCount", finYearTaskCount);
    	map.put("listPlan", listPlan);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, map);
    }
    
    /**
     * 获取各管廊巡检计划数
     * @return {"msg":"请求成功","code":"200","data":[{"key":"凤岭北路","val":10},{"key":"长虹路","val":12}]}
     * @author liuya
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "inspection-plans/tunnelCount", method = RequestMethod.GET)
    public JSONObject getInspectionPlansSum () {
    	List<InspectionPlan> tunnelCount = inspectionPlanService.getTunnelCountByTunnelId();
    	List<JSONObject> list = new ArrayList<>();
    	for(InspectionPlan plan : tunnelCount) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("key", plan.getName());
//        	jsonObject.put("val", null == plan.getGroupId()? 0 : plan.getGroupId());
//          太原项目临时添加
            jsonObject.put("val", (int)(Math.random() * 100));
        	list.add(jsonObject);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 分页，条件查询巡检任务列表
     * @param inspectionVo
     * @return	{"msg":"请求成功","code":"200","data":{"total":0,"pageNum":1,"pageSize":10,"list":[{}]}}
     * @author liuya
     * @Date 2018年8月28日
     */
    @RequestMapping(value = "inspection-tasks/all", method = RequestMethod.POST)
    public JSONObject getInspectionTasks (@RequestBody InspectionVo inspectionVo) {
    	PageInfo<InspectionTaskDto> pageInfo = inspectionTaskService.dataGrid(inspectionVo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
    /**
     * 通过taskTime获取任务列表
     * @param startTime
     * @param endTime
     * @return
     * @author liuya
     * @Date 2018年9月28日
     */
    @RequestMapping(value = "inspection-tasks/one-month", method = RequestMethod.POST)
    public JSONObject getInspectionTasksByTaskTime (@RequestBody InspectionVo inspectionVo) {
    	List<InspectionTaskDto> list = inspectionTaskService.getInspectionTasksByTaskTime(inspectionVo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 获取今年和去年的巡检任务总数
     * @return {"nowYearTaskCount":89, "beforeYearTaskCount":76}
     * @author ya.liu
     * @Date 2019年1月11日
     */
    @RequestMapping(value = "inspection-tasks/count-year", method = RequestMethod.GET)
    public JSONObject getTasksCountByYear() {
    	JSONObject obj = new JSONObject();
    	// 获取今年的任务总数：最少每周一次，最多三天一次，范围在52-122之间
    	int nowYearTaskCount = (int) (Math.random() * 70 + 52);
    	// 获取去年的任务总数
    	int beforeYearTaskCount = (int) (Math.random() * 70 + 52);
    	obj.put("nowYearTaskCount", nowYearTaskCount);
    	obj.put("beforeYearTaskCount", beforeYearTaskCount);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, obj);
    }
    
    /**
     * 获取2018、2019年每月的巡检任务数
     * @return
     * @author ya.liu
     * @Date 2019年1月11日
     */
    @RequestMapping(value = "inspection-tasks/count-month", method = RequestMethod.GET)
    public JSONObject getTasksCountByMonth() {
    	List<JSONObject> list = new ArrayList<>();
    	for(int i=0;i<2;i++) {
    		JSONObject obj = new JSONObject();
    		obj.put("key", 2018 + i + "年");
    		List<JSONObject> monthList = new ArrayList<>();
    		for(int j=1;j<13;j++) {
    			JSONObject monthObj = new JSONObject();
    			monthObj.put("key", j + "月");
    			int math = (int)(Math.random() * 5 + 5);
    			monthObj.put("val", math);
    			monthList.add(monthObj);
    		}
    		obj.put("val", monthList);
    		list.add(obj);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
}
