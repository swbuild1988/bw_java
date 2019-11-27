package com.bandweaver.tunnel.controller.omm;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.biz.pojo.omm.*;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.util.*;
import org.apache.poi.hssf.usermodel.*;
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
import com.bandweaver.tunnel.common.biz.constant.omm.InspectionPath;
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
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTemplateDto;
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
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTemplateService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.vo.AuditVo;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionTemplateVo;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
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
    @Autowired
    private InspectionTemplateService inspectionTemplateService;


    /**
     * 添加责任班组
     *
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
     *
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
     *
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
     *
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
        for (InspectionGroupDto dto : listDto) {
            if (dto == null || id.equals(dto.getId())) continue;
            List<Staff> users = dto.getMembers();
            for (Staff user : users) {
                list = list.stream().filter(a -> !a.getId().equals(user.getId())).collect(Collectors.toList());
            }
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 获取不在群组的成员
     *
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
        for (InspectionGroupDto dto : listDto) {
            if (dto == null) continue;
            List<Staff> users = dto.getMembers();
            for (Staff user : users) {
                list = list.stream().filter(a -> !a.getId().equals(user.getId())).collect(Collectors.toList());
            }
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 批量添加成员
     *
     * @param id  群组id
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
        if (dto != null) {
            for (Staff s : dto.getMembers()) {
                if (dto.getLeaderId().equals(s.getId())) continue;
                inspectionGroupService.deleteRelation(id, s.getId());
            }
        }
        // 重新添加
        for (Integer member : list) {
            inspectionGroupService.addRelation(id, member);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 批量删除成员
     *
     * @param id  群组id
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
        for (Integer member : list) {
            if (userId == member) continue;
            inspectionGroupService.deleteRelation(id, member);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 分页获取群组信息
     *
     * @param name     支持模糊查询
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
     * @param tasks            [{"taskTime":1551766778000}]
     * @param approverId
     * @param tunnelId
     * @param requestStaffId
     * @param remark
     * @param steps            [{"name":""}]
     * @return
     */
    @RequestMapping(value = "inspection-plans", method = RequestMethod.POST)
    public JSONObject addInspectionPlan(@RequestBody InspectionPlanDto inspectionPlanDto) {
        inspectionPlanService.add(inspectionPlanDto);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 删除巡检计划，删除连带任务
     *
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
     * @param inspectionVo
     * @return
     * @author ya.liu
     * @Date 2019年5月23日
     */
    @RequestMapping(value = "inspection-plans/condition", method = RequestMethod.POST)
    public JSONObject getAllInspectionPlans(@RequestBody(required = false) InspectionVo inspectionVo) {
        List<InspectionPlanSimpleDto> inspectionPlans = inspectionPlanService.getInspectionPlansByVo(inspectionVo);
        for (InspectionPlanSimpleDto dto : inspectionPlans) {
            List<JSONObject> json = new ArrayList<>();

            Integer path = dto.getInspectionPath();
            List<Integer> ids = CommonUtil.convertStringToList(dto.getOtherIds());
            if (ids != null && ids.size() > 0) {
                if (InspectionPath.CABIN.getValue().equals(path)) {
                    Integer storeId = ids.get(0);
                    List<SectionDto> sections = sectionService.getSectionsByStoreId(storeId);
                    if (sections != null && sections.size() > 0) {
                        JSONObject obj = new JSONObject();
                        obj.put("name", sections.get(0).getStore().getName());
                        obj.put("startPoint", sections.get(0).getStartPoint());
                        obj.put("endPoint", sections.get(sections.size() - 1).getEndPoint());
                        json.add(obj);
                    }
                }
                if (InspectionPath.RANGE.getValue().equals(path)) {
                    for (Integer id : ids) {
                        AreaDto area = areaService.getAreasById(id);
                        JSONObject obj = new JSONObject();
                        obj.put("name", area.getName());
                        obj.put("startPoint", area.getStartPoint());
                        obj.put("endPoint", area.getEndPoint());
                        json.add(obj);
                    }
                }
            }
            dto.setOthers(json);
        }

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
    @RequestMapping(value = "inspection-plans/{id}", method = RequestMethod.GET)
    public JSONObject getInspectionPlanDto(@PathVariable("id") String id) {
        InspectionPlanDto inspectionPlanDto = inspectionPlanService.getInspectionDto(id);
        List<InspectionTaskDto> list = inspectionPlanDto.getTasks();
        if (list != null && list.size() > 0) {
            InspectionTaskDto dto = inspectionTaskService.getTaskDto(list.get(0).getId());
            inspectionPlanDto.setSteps(dto.getSteps());
        }
        ;
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
            if (null != section) {
                record.setSectionId(section.getId());
            }
            inspectionRecordService.add(record);
        }

        // 修改计划步骤
        boolean flag = true;
        for (InspectionStep step : inspectionTask.getSteps()) {
            inspectionStepService.update(step);
            if (!Boolean.TRUE.equals(step.getIsFinished())) flag = false;
        }

        if (flag) {
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
     *
     * @param id 任务id
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "inspection-tasks/{id}", method = RequestMethod.GET)
    public JSONObject getInspectionTask(@PathVariable("id") int id) {
        InspectionTaskDto taskDto = inspectionTaskService.getTaskDto(id);
        for (InspectionRecordDto record : taskDto.getRecords()) {
            SectionDto section = sectionService.getSectionById(record.getSectionId());
            if (section == null) continue;
            AreaDto area = areaService.getAreasById(section.getAreaId());
            StoreDto store = storeService.getStoreById(section.getStoreId());
            record.setArea(area);
            record.setStore(store);
            if (record.getDefect() != null) {
                //记录和缺陷的sectionId相同
                record.getDefect().setArea(area);
                record.getDefect().setStore(store);
            }
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, taskDto);
    }

    /**
     * 获取年度计划数、本月计划数、本月已完成巡检任务数、本月待巡检任务数、计划信息列表
     *
     * @return {"msg":"请求成功","code":"200","data":{"yearPlanCount":0,"monthPlanCount":0,"finishTaskCount":0,"unfinishTaskCount":0,"yearTaskCount":0,"finYearTaskCount":0,"listPlan":[{"id":1,"name":"巡检计划"}]}}
     * @author liuya
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "inspection-plans/count", method = RequestMethod.GET)
    public JSONObject getInspectionPlansAndTask() {
        JSONObject map = new JSONObject();
        InspectionVo vo1 = new InspectionVo();
        Date startYear = DateUtil.getBeginDayOfYear();
        Date endYear = DateUtil.getEndDayOfYear();
        vo1.setStartTime(startYear);
        vo1.setEndTime(endYear);
        int yearTaskCount = inspectionTaskService.getCountByVo(vo1);
        vo1.setFinished(true);
        int finYearTaskCount = inspectionTaskService.getCountByVo(vo1);

        InspectionVo vo2 = new InspectionVo();
        Date startMonth = DateUtil.getBeginDayOfMonth();
        Date endMonth = DateUtil.getEndDayOfMonth();
        vo2.setStartTime(startMonth);
        vo2.setEndTime(endMonth);
        int monthTaskCount = inspectionTaskService.getCountByVo(vo2);
        vo2.setFinished(true);
        int finishTaskCount = inspectionTaskService.getCountByVo(vo2);
        vo2.setFinished(false);
        int unfinishTaskCount = inspectionTaskService.getCountByVo(vo2);

        List<InspectionPlan> listPlan = inspectionPlanService.getAllInspectionPlan();

        map.put("monthTaskCount", monthTaskCount);
        map.put("finishTaskCount", finishTaskCount);
        map.put("unfinishTaskCount", unfinishTaskCount);
        map.put("yearTaskCount", yearTaskCount);
        map.put("finYearTaskCount", finYearTaskCount);
        map.put("listPlan", listPlan);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, map);
    }

    /**
     * 获取各管廊巡检计划数
     *
     * @return {"msg":"请求成功","code":"200","data":[{"key":"凤岭北路","val":10},{"key":"长虹路","val":12}]}
     * @author liuya
     * @Date 2018年8月27日
     */
    @RequestMapping(value = "inspection-plans/tunnelCount", method = RequestMethod.GET)
    public JSONObject getInspectionPlansSum() {
        List<TunnelSimpleDto> tunnel = tunnelService.getList();
        List<JSONObject> list = new ArrayList<>();
        for (TunnelSimpleDto dto : tunnel) {
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
     *
     * @param tunnelId
     * @param name       支持模糊查询
     * @param isFinished
     * @param pageSize
     * @param pageNum
     * @param startTime
     * @param endTime
     * @return {"msg":"请求成功","code":"200","data":{"total":0,"pageNum":1,"pageSize":10,"list":[{}]}}
     * @author liuya
     * @Date 2018年8月28日
     */
    @RequestMapping(value = "inspection-tasks/all", method = RequestMethod.POST)
    public JSONObject getInspectionTasks(@RequestBody InspectionVo inspectionVo) {
        PageInfo<InspectionTaskDto> pageInfo = inspectionTaskService.dataGrid(inspectionVo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    /**
     * 通过taskTime获取任务列表
     *
     * @param startTime
     * @param endTime
     * @return
     * @author liuya
     * @Date 2018年9月28日
     */
    @RequestMapping(value = "inspection-tasks/one-month", method = RequestMethod.POST)
    public JSONObject getInspectionTasksByTaskTime(@RequestBody InspectionVo inspectionVo) {
        List<InspectionTaskDto> list = inspectionTaskService.getInspectionTasksByTaskTime(inspectionVo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 获取今年和去年的巡检任务总数
     *
     * @return {"nowYearTaskCount":89, "beforeYearTaskCount":76}
     * @author ya.liu
     * @Date 2019年1月11日
     */
    @RequestMapping(value = "inspection-tasks/count-year", method = RequestMethod.GET)
    public JSONObject getTasksCountByYear() {
        JSONObject obj = new JSONObject();
        // 获取今年的任务总数：最少每周一次，最多三天一次，范围在52-122之间
//    	int nowYearTaskCount = (int) (Math.random() * 70 + 52);
        InspectionVo vo = new InspectionVo();
        vo.setStartTime(DateUtil.getBeginDayOfYear());
        vo.setEndTime(DateUtil.getEndDayOfYear());
        int nowYearTaskCount = inspectionTaskService.getCountByVo(vo);

        // 获取去年的任务总数
//    	int beforeYearTaskCount = (int) (Math.random() * 70 + 52);
        Date now = new Date();
        now.setYear(now.getYear() - 1);
        vo.setStartTime(DateUtil.getBeginDayOfYear(now));
        vo.setEndTime(DateUtil.getEndDayOfYear(now));
        int beforeYearTaskCount = inspectionTaskService.getCountByVo(vo);
        obj.put("nowYearTaskCount", nowYearTaskCount);
        obj.put("beforeYearTaskCount", beforeYearTaskCount);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, obj);
    }

    /**
     * 获取去年和今年每月的巡检任务数
     *
     * @return
     * @author ya.liu
     * @Date 2019年1月11日
     */
    @RequestMapping(value = "inspection-tasks/count-month", method = RequestMethod.GET)
    public JSONObject getTasksCountByMonth() {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 1; i > -1; i--) {
            Date now = new Date();
            JSONObject obj = new JSONObject();
            now.setYear(now.getYear() - i);
            obj.put("key", now.getYear() + 1900 + "年");
            List<JSONObject> monthList = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                JSONObject monthObj = new JSONObject();
                monthObj.put("key", j + 1 + "月");
//    			int math = (int)(Math.random() * 5 + 5);// 假数据
                InspectionVo vo = new InspectionVo();
                now.setMonth(j);
                vo.setStartTime(DateUtil.getBeginDayOfMonth(now));
                vo.setEndTime(DateUtil.getEndDayOfMonth(now));
                int math = inspectionTaskService.getCountByVo(vo);// 真数据
                monthObj.put("val", math);
                monthList.add(monthObj);
            }
            obj.put("val", monthList);
            list.add(obj);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    // ================================巡检模板===========================

    /**
     * 添加巡检模板
     *
     * @param name
     * @param info
     * @return
     * @author ya.liu
     * @Date 2019年5月19日
     */
    @RequestMapping(value = "inspection-template", method = RequestMethod.POST)
    public JSONObject addInspectionTemplate(@RequestBody JSONObject map) {
        InspectionTemplate temp = new InspectionTemplate();
        temp.setName(map.getString("name"));
        temp.setInfo(JSON.toJSONString(map.get("info")));
        inspectionTemplateService.add(temp);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 通过id获取巡检模板详情
     *
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年5月19日
     */
    @RequestMapping(value = "inspection-template/{id}", method = RequestMethod.GET)
    public JSONObject getInspectionTemplate(@PathVariable("id") Integer id) {
        InspectionTemplateDto dto = inspectionTemplateService.getDtoById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }

    /**
     * 删除巡检模板
     *
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年5月19日
     */
    @RequestMapping(value = "inspection-template/{id}", method = RequestMethod.DELETE)
    public JSONObject addInspectionTemplate(@PathVariable("id") Integer id) {
        inspectionTemplateService.delete(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取巡检模板列表
     *
     * @param id
     * @param staffId
     * @param name      支持模糊查询
     * @param startTime
     * @param endTime
     * @return
     * @author ya.liu
     * @Date 2019年5月19日
     */
    @RequestMapping(value = "inspection-template/condition", method = RequestMethod.POST)
    public JSONObject addInspectionTemplate(@RequestBody(required = false) InspectionTemplateVo vo) {
        List<InspectionTemplateDto> list = inspectionTemplateService.getListByCondition(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 获取巡检任务的报告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "inspection-tasks/{id}/report")
    public JSONObject createTaskReport(@PathVariable("id") int id) {
        InspectionTaskDto task = inspectionTaskService.getTaskDto(id);

        List<Item> data = new ArrayList<>();

        // 塞入任务名
        data.add(new Item("巡检任务", 0, 0));
        data.add(new Item(task.getPlanName(), 0, 2));
        // 加入任务人员名单
        data.add(new Item("巡检人", 1, 0));
        data.add(new Item(task.getInspectManName(), 1, 2));
        // 加入任务状态
        data.add(new Item("任务状态", 1, 4));
        data.add(new Item(task.getProcessStatus(), 1, 6));
        // 开始结束时间
        data.add(new Item("任务开始时间", 2, 0));
        data.add(new Item(DateUtil.getDate2String(task.getStartTime()), 2, 2));
        data.add(new Item("任务结束时间", 2, 4));
        data.add(new Item(DateUtil.getDate2String(task.getEndTime()), 2, 6));
        // 巡检记录标题
        List<String> colNames = Arrays.asList("记录序号", "管廊", "区域", "舱室", "时间", "类型", "问题", "描述");
        for (int i = 0; i < colNames.size(); i++) {
            data.add(new Item(colNames.get(i), 3, i));
        }
        // 将所有记录加入
        List<InspectionRecordDto> inspectionRecords = task.getRecords();
        for (int i = 0; i < inspectionRecords.size(); i++) {
            InspectionRecordDto record = inspectionRecords.get(i);
            LogUtil.info(record);
            List<String> vals = new ArrayList<>();
            vals.add((i + 1) + "");
            vals.add(task.getTunnelName());
            vals.add(record.getArea() == null ? "" : record.getArea().getName());
            vals.add(record.getStore() == null ? "" : record.getStore().getName());
            vals.add(DateUtil.getDate2String(record.getRecordTime()));
            vals.add(record.getContent());
            vals.add(record.getHasDefect() ? record.getDefect().getTypeName() : "无");
            vals.add(record.getHasDefect() ? record.getDefect().getDescription() : "无");

            for (int j = 0; j < vals.size(); j++) {
                data.add(new Item(vals.get(j), 4 + i, j));
            }
        }
        // 加入审核人、时间
        data.add(new Item("审核人", 5 + inspectionRecords.size(), 4));
        data.add(new Item("时间", 6 + inspectionRecords.size(), 4));

        try {
            String xlsPath = getTaskFileName(id) + ".xls";
            createExcel(xlsPath, data, 7 + inspectionRecords.size(), 8);
            String pdfPath = getTaskFileName(id) + ".pdf";
            PDFUtil.excel2Pdf(xlsPath, pdfPath);
            LogUtil.info("巡检任务报告转化pdf完成");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e);
            return CommonUtil.returnStatusJson(StatusCodeEnum.E_400, e);
        }

        return CommonUtil.success();
    }


    /**
     * pdf文件预览
     * @param id
     * @param response
     */
    @RequestMapping(value = "inspection-tasks/{id}/report-view",method = RequestMethod.GET)
    public void preview(@PathVariable("id")Integer id, HttpServletResponse response){
        FileInputStream fis = null;
        try {
            String pdfPath = getTaskFileName(id) + ".pdf";
            fis = new FileInputStream(new File(pdfPath));
            byte[] data = new byte[fis.available()];
            fis.read(data);
            response.getOutputStream().write(data);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(e);
        } finally {
            close(fis);
        }
    }


    /**
     * excel文件下载
     * @param id
     * @param response
     * @return
     */
    @RequiresPermissions("report:download")
    @RequestMapping(value = "inspection-tasks/{id}/report-download",method = RequestMethod.GET)
    public void download(@PathVariable("id")Integer id, HttpServletResponse response){
        FileInputStream fis = null;
        try {
            String excelPath = getTaskFileName(id) + ".xls";
            fis = new FileInputStream(new File(excelPath));
            byte[] data = new byte[fis.available()];
            fis.read(data);
            //获取输出文件名
            String downloadFileName = excelPath.substring(excelPath.lastIndexOf("\\"), excelPath.length());
            response.setHeader("Content-disposition", "attachment; filename=" + new String(downloadFileName.getBytes("GBK"), "ISO-8859-1") + ""); // 设定输出文件头
            response.setContentType("application/vnd.ms-excel"); // 定义输出类型
            response.getOutputStream().write(data);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(e);
        } finally {
            close(fis);
        }
    }

    private void createExcel(String path, List<Item> data, int rowNum, int colNum) throws Exception {
        //获得Excel文件输出流
        FileOutputStream out = new FileOutputStream(new File(path));

        try {
            //创建excel工作簿对象
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建excel页
            HSSFSheet sheet = wb.createSheet("POI导出测试");

            // 设置单元格格式
            HSSFCellStyle cellStyle = wb.createCellStyle();

            //将所有的单元格创建并加边框
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            // 自动换行
            cellStyle.setWrapText(true);
            for (int i = 0; i < rowNum; i++) {
                HSSFRow row = sheet.createRow(i);
                for (int j = 0; j < colNum; j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                }
            }

            // 将数据存到excel中
            ExcelUtil.fillExcelData(sheet, data);

            // 合并单元格
            ExcelUtil.mergedRegion(sheet, 0, 0, 0, 1);
            ExcelUtil.mergedRegion(sheet, 0, 0, 2, 7);
            ExcelUtil.mergedRegion(sheet, 1, 1, 0, 1);
            ExcelUtil.mergedRegion(sheet, 1, 1, 2, 3);
            ExcelUtil.mergedRegion(sheet, 1, 1, 4, 5);
            ExcelUtil.mergedRegion(sheet, 1, 1, 6, 7);
            ExcelUtil.mergedRegion(sheet, 2, 2, 0, 1);
            ExcelUtil.mergedRegion(sheet, 2, 2, 2, 3);
            ExcelUtil.mergedRegion(sheet, 2, 2, 4, 5);
            ExcelUtil.mergedRegion(sheet, 2, 2, 6, 7);
            ExcelUtil.mergedRegion(sheet, rowNum - 3, rowNum - 3, 0, 7);
            ExcelUtil.mergedRegion(sheet, rowNum - 2, rowNum - 1, 0, 3);
            ExcelUtil.mergedRegion(sheet, rowNum - 2, rowNum - 2, 5, 7);
            ExcelUtil.mergedRegion(sheet, rowNum - 1, rowNum - 1, 5, 7);

            // 设置列宽
            int widths[] = {1250, 2000, 1750, 1750, 3000, 1500, 1500, 10000};
            for (int i = 0; i < widths.length; i++) {
                sheet.setColumnWidth(i, widths[i]);
            }

            // 设置任务标题居中、加粗
            cellStyle = wb.createCellStyle();
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont font = wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 14);
            cellStyle.setFont(font);
            HSSFCell cell = sheet.getRow(0).getCell(2);
            cell.setCellStyle(cellStyle);

            wb.write(out);

        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }
        }
    }

    // 生成文件名
    private String getTaskFileName(int taskId) {
        String exportPath = PropertiesUtil.getString(Constants.INSPECTION_TASK_PATH);
        if (!FileUtil.isExit(exportPath)) {
            FileUtil.createPath(exportPath);
        }
        String path = exportPath + File.separator + "巡检任务" + taskId;
        return path;
    }

    private void close(FileInputStream fis) {
        if (fis != null){
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.error(e);
            }
        }
    }

}
