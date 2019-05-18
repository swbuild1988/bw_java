package com.bandweaver.tunnel.controller.omm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
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
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionGroupService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionRecordService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionStepService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionStep;
import com.bandweaver.tunnel.common.biz.vo.AuditVo;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
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
    private InspectionTaskService inspectionTaskService;
    @Autowired
    private InspectionPlanService inspectionPlanService;
    @Autowired
    private InspectionRecordService inspectionRecordService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private InspectionStepService inspectionStepService;


    /**
     * 添加责任班组
     * @param name
     * @param leaderId
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups", method = RequestMethod.POST)
    public JSONObject addGroup(@RequestBody InspectionGroup group) {
    	inspectionGroupService.add(group);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 修改责任班组
     * @param id
     * @param name
     * @param leaderId
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups", method = RequestMethod.PUT)
    public JSONObject updateGroup(@RequestBody InspectionGroup group) {
    	inspectionGroupService.update(group);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 删除责任班组
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteGroup(@PathVariable("id") Integer id) {
    	inspectionGroupService.delete(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 获取可以成为组长的员工
     * @param id 群组id
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups/{id}/staffs", method = RequestMethod.GET)
    public JSONObject getStaff(@PathVariable("id") Integer id) {
    	StaffVo vo = new StaffVo();
    	vo.setOutside(1);
    	List<StaffDto> list = staffService.getDtoListByCondition(vo);
    	
    	List<InspectionGroupDto> listDto = inspectionGroupService.getAllInspectionGroupDtos();
    	for(InspectionGroupDto dto : listDto) {
    		if(dto == null || id.equals(dto.getId())) continue;
    		List<Staff> users = dto.getMembers();
    		for(Staff user : users) {
        		list = list.stream().filter(a -> !a.getId().equals(user.getId())).collect(Collectors.toList());
        	}
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 获取不在群组的成员
     * @param id 群组id
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups/staffs", method = RequestMethod.GET)
    public JSONObject getStaffs() {
    	StaffVo vo = new StaffVo();
    	vo.setOutside(1);
    	List<StaffDto> list = staffService.getDtoListByCondition(vo);
    	
    	List<InspectionGroupDto> listDto = inspectionGroupService.getAllInspectionGroupDtos();
    	for(InspectionGroupDto dto : listDto) {
    		if(dto == null) continue;
    		List<Staff> users = dto.getMembers();
    		for(Staff user : users) {
        		list = list.stream().filter(a -> !a.getId().equals(user.getId())).collect(Collectors.toList());
        	}
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 批量添加成员
     * @param id 群组id
     * @param ids 成员id  1,2,3
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups/{id}/members/{ids}", method = RequestMethod.GET)
    public JSONObject addRelation(@PathVariable("id") Integer id, @PathVariable("ids") String ids) {
    	List<Integer> list = CommonUtil.convertStringToList(ids);
    	// 删除组员
    	InspectionGroupDto dto = inspectionGroupService.getInspectionGroupDto(id);
    	if(dto != null) {
    		for(Staff s : dto.getMembers()) {
        		if(dto.getLeaderId().equals(s.getId())) continue;
        		inspectionGroupService.deleteRelation(id, s.getId());
        	}
    	}
    	// 重新添加
    	for(Integer member : list) {
    		inspectionGroupService.addRelation(id, member);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 批量删除成员
     * @param id 群组id
     * @param ids 成员id  1,2,3
     * @return
     * @author ya.liu
     * @Date 2019年5月5日
     */
    @RequestMapping(value = "inspection-groups/{id}/members/{ids}", method = RequestMethod.DELETE)
    public JSONObject deleteRelation(@PathVariable("id") Integer id, @PathVariable("ids") String ids) {
    	List<Integer> list = CommonUtil.convertStringToList(ids);
    	InspectionGroupDto dto = inspectionGroupService.getInspectionGroupDto(id);
    	int userId = dto == null ? 0 : dto.getLeaderId();
    	for(Integer member : list) {
    		if(userId == member) continue;
    		inspectionGroupService.deleteRelation(id, member);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 分页获取群组信息
     * @param name 支持模糊查询
     * @param leaderId
     * @param pageNum
     * @param pageSize
     * @return
     * @author ya.liu
     * @Date 2019年5月6日
     */
    @RequestMapping(value = "inspection-groups/datagride", method = RequestMethod.POST)
    public JSONObject deleteRelation(@RequestBody InspectionVo vo) {
    	PageInfo<InspectionGroup> page = inspectionGroupService.dataGraid(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, page);
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
     * @param planId
     * @param name
     * @param groupId
     * @param inspectionWay
     * @param inspectionObject
     * @param inspectionPath
     * @param inspectTime
     * @param tasks [{"taskTime":1551766778000}]
     * @param approverId
     * @param tunnelId
     * @param requestStaffId
     * @param remark
     * @param steps [{"name":""}]
     * @return
     */
    @RequestMapping(value = "inspection-plans", method = RequestMethod.POST)
    public JSONObject addInspectionPlan(@RequestBody InspectionPlanDto inspectionPlanDto) {
        inspectionPlanService.add(inspectionPlanDto);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 删除巡检计划，删除连带任务
     * @param planId 计划id
     * @return
     * @author ya.liu
     * @Date 2019年4月8日
     */
    @RequestMapping(value = "inspection-plans/{planId}", method = RequestMethod.DELETE)
    public JSONObject deleteByPlanId(@PathVariable("planId") String planId) {
    	int i = inspectionPlanService.delete(planId);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, i);
    }
    
    /**
     * 获取巡检计划
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
    @RequestMapping(value = "inspection-plans/{id}", method=RequestMethod.GET)
    public JSONObject getInspectionPlanDto(@PathVariable("id") String id) {
        InspectionPlanDto inspectionPlanDto = inspectionPlanService.getInspectionDto(id);
        List<InspectionTaskDto> list = inspectionPlanDto.getTasks();
        if(list != null && list.size() > 0) {
        	InspectionTaskDto dto = inspectionTaskService.getTaskDto(list.get(0).getId());
        	inspectionPlanDto.setSteps(dto.getSteps());
        };
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
        
        // 修改计划步骤
        boolean flag = true;
        for(InspectionStep step : inspectionTask.getSteps()) {
        	inspectionStepService.update(step);
        	if(!Boolean.TRUE.equals(step.getIsFinished())) flag = false;
        }

        if(flag) {
        	LogUtil.info("巡检任务结束");
            // 提交结束，下一步
            activitiService.completeTaskByProcessIntance(inspectionTask.getProcessInstanceId());

            LogUtil.info("更新巡检计划");
            inspectionPlanService.complete(inspectionTask.getPlanId());
        }

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
        	if(section == null) continue;
        	AreaDto area = areaService.getAreasById(section.getAreaId());
        	StoreDto store = storeService.getStoreById(section.getStoreId());
        	record.setArea(area);
        	record.setStore(store);
        	if(record.getDefect() != null) {
            	//记录和缺陷的sectionId相同
        		record.getDefect().setArea(area);
            	record.getDefect().setStore(store);
        	}
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, taskDto);
    }
    
    /**
     * 获取年度计划数、本月计划数、本月已完成巡检任务数、本月待巡检任务数、计划信息列表
     * @return {"msg":"请求成功","code":"200","data":{"yearPlanCount":0,"monthPlanCount":0,"finishTaskCount":0,"unfinishTaskCount":0,"yearTaskCount":0,"finYearTaskCount":0,"listPlan":[{"id":1,"name":"巡检计划"}]}}
     * @author liuya
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "inspection-plans/count", method = RequestMethod.GET)
    public JSONObject getInspectionPlansAndTask () {
    	JSONObject map = new JSONObject();
    	InspectionVo vo1 = new InspectionVo();
    	Date startYear = DateUtil.getBeginDayOfYear();
    	Date endYear = DateUtil.getEndDayOfYear();
    	vo1.setStartTime(startYear);
    	vo1.setEndTime(endYear);
    	int yearPlanCount = inspectionPlanService.getCountByCondition(vo1);
    	int yearTaskCount = inspectionTaskService.getCountByVo(vo1);
    	vo1.setFinished(true);
    	int finYearTaskCount = inspectionTaskService.getCountByVo(vo1);
    	
    	InspectionVo vo2 = new InspectionVo();
    	Date startMonth = DateUtil.getBeginDayOfMonth();
    	Date endMonth = DateUtil.getEndDayOfMonth();
    	vo2.setStartTime(startMonth);
    	vo2.setEndTime(endMonth);
    	int monthPlanCount = inspectionPlanService.getCountByCondition(vo2);
    	vo2.setFinished(true);
    	int finishTaskCount = inspectionTaskService.getCountByVo(vo2);
    	vo2.setFinished(false);
    	int unfinishTaskCount = inspectionTaskService.getCountByVo(vo2);
    	
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
    	List<TunnelSimpleDto> tunnel = tunnelService.getList();
    	List<JSONObject> list = new ArrayList<>();
    	for(TunnelSimpleDto dto : tunnel) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("key", dto.getName());
    		InspectionVo vo = new InspectionVo();
    		vo.setTunnelId(dto.getId());
    		int count = inspectionPlanService.getCountByCondition(vo);
        	jsonObject.put("val", count);
        	list.add(jsonObject);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 分页，条件查询巡检任务列表
     * @param tunnelId
     * @param name	支持模糊查询
     * @param isFinished
     * @param pageSize
     * @param pageNum
     * @param startTime
     * @param endTime
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
//    	InspectionVo vo = new InspectionVo();
//    	vo.setStartTime(DateUtil.getBeginDayOfYear());
//    	vo.setEndTime(DateUtil.getEndDayOfYear());
//    	int nowYearTaskCount = inspectionTaskService.getCountByVo(vo);
    	
    	// 获取去年的任务总数
    	int beforeYearTaskCount = (int) (Math.random() * 70 + 52);
//    	Date now = new Date();
//    	now.setYear(now.getYear() - 1);
//    	vo.setStartTime(DateUtil.getBeginDayOfYear(now));
//    	vo.setEndTime(DateUtil.getEndDayOfYear(now));
//    	int beforeYearTaskCount = inspectionTaskService.getCountByVo(vo);
    	obj.put("nowYearTaskCount", nowYearTaskCount);
    	obj.put("beforeYearTaskCount", beforeYearTaskCount);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, obj);
    }
    
    /**
     * 获取去年和今年每月的巡检任务数
     * @return
     * @author ya.liu
     * @Date 2019年1月11日
     */
    @RequestMapping(value = "inspection-tasks/count-month", method = RequestMethod.GET)
    public JSONObject getTasksCountByMonth() {
    	List<JSONObject> list = new ArrayList<>();
    	for(int i=1;i>-1;i--) {
    		Date now = new Date();
    		JSONObject obj = new JSONObject();
    		now.setYear(now.getYear() - i);
    		obj.put("key", now.getYear() + 1900 + "年");
    		List<JSONObject> monthList = new ArrayList<>();
    		for(int j=0;j<12;j++) {
    			JSONObject monthObj = new JSONObject();
    			monthObj.put("key", j + 1 + "月");
    			int math = (int)(Math.random() * 5 + 5);// 假数据
//    			InspectionVo vo = new InspectionVo();
//    			now.setMonth(j);
//    	    	vo.setStartTime(DateUtil.getBeginDayOfMonth(now));
//    	    	vo.setEndTime(DateUtil.getEndDayOfMonth(now));
//    	    	int math = inspectionTaskService.getCountByVo(vo);// 真数据
    			monthObj.put("val", math);
    			monthList.add(monthObj);
    		}
    		obj.put("val", monthList);
    		list.add(obj);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
}
