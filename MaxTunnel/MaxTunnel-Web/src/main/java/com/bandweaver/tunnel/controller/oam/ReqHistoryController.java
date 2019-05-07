package com.bandweaver.tunnel.controller.oam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ReqHistoryDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ReqRecordDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqHistoryService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqRecordService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqHistory;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqRecord;
import com.bandweaver.tunnel.common.biz.vo.AuditVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqRecordVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.github.pagehelper.PageInfo;


/**
 * 入廊申请管理
 *
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class ReqHistoryController {

    @Autowired
    private ReqHistoryService reqHistoryService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ReqRecordService reqRecordService;
    
    /**
     * 通过id查询入廊申请记录
     *
     * @param   id 入廊申请id
     * @param 
     * @return {"msg":"请求成功","code":"200","data":{"id":16,"createTime":1533714203000,"processType":1003,"processTypeName":"入廊申请","processStatus":"流程结束","processInstanceId":"2501","isFinished":true,"approverId":1,"staff":{"id":2,"name":"李四","deptId":2,"positionId":2,"accountId":2,"crtTime":1529942400000},"tunnel":{"id":2,"name":"长虹路"},"action":1,"actionName":"参观","preTime":null,"enterTime":null,"exitTime":null,"crtTime":1533714203000,"visitorNumber":null,"visitorInfo":null,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000}}}
     * @throws
     * @author shaosen
     * @date 2018年7月26日
     */
    @RequestMapping(value = "req-historys/{id}", method = RequestMethod.GET)
    public JSONObject getDtoById(@PathVariable Integer id) {
        ReqHistoryDto dto = reqHistoryService.getDtoById(id);
        
        //获取批注
        List<Comment> list = new ArrayList<>();
        List<HistoricTaskInstance> hisList = historyService.createHistoricTaskInstanceQuery().processInstanceId(dto.getProcessInstanceId()).list();
        if(hisList!=null && hisList.size()>0){
        	for(HistoricTaskInstance hti : hisList){
        	//任务ID
        	String htaskId = hti.getId();
        	LogUtil.info("hisTaskId: " + htaskId);
        	//获取批注信息
        	List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
        	list.addAll(taskList);
        	}
        }
        if(list != null && list.size() > 0)
        	dto.setComment(list.get(0).getFullMessage());
        
        // 获取审批结果
        List<HistoricVariableInstance> ls = historyService.createHistoricVariableInstanceQuery().processInstanceId(dto.getProcessInstanceId()).list();
        if(list != null && list.size()>0){
			for(HistoricVariableInstance hiv : ls){
				if("result".equals(hiv.getVariableName())) {
					LogUtil.info("result:" + hiv.getValue());
					dto.setResult((String)hiv.getValue());
				}
			}
		}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }

    /**
     * 添加申请记录
     *
     * @param  staffId 	员工id
     * @param  approverId	审批人id
     * @param  tunnelId	管廊id
     * @param  action 		行为	 枚举   1：参观；2：维护：3：安装；
     * @param  preTime		计划入廊时间
     * @param  visitorNumber 	访客总人数
     * @param  visitorCompany	访客所属公司
     * @param  visitorInfo		访客详细信息
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年6月27日
     */
    @RequestMapping(value = "req-historys", method = RequestMethod.POST)
    public JSONObject add(@RequestBody ReqHistory history) {
        history.setCreateTime(new Date());
        LogUtil.info("添加入廊申请:" + history);
        reqHistoryService.add(history);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 根据id删除
     *
     * @param  id 入廊申请id
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年6月27日
     */
    @RequestMapping(value = "req-historys/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteById(@PathVariable Integer id) {
        reqHistoryService.deleteById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 入廊申请分页查询
     *
     * @param  staffId 员工id
     * @param  tunnelId 管廊id
     * @param  isFinished  流程是否结束标记 1：结束；0：未结束
     * @param  action:行为	 枚举   1：参观；2：维护：3：安装；
     * @param  startTime: 查询preTime（计划入廊时间）的起始时间
     * @param  endTime: 查询preTime（计划入廊时间）的截止时间
     * @param  vistorCompany 公司id
     * @param  pageNum 必须
     * @param  pageSize 必须
     * @return {"msg":"请求成功","code":"200","data":{"total":2,"list":[{"id":16,"createTime":1533714203000,"processType":1003,"processTypeName":"入廊申请","processStatus":"流程结束","processInstanceId":"2501","isFinished":true,"approverId":1,"staff":{"id":2,"name":"李四","deptId":2,"positionId":2,"accountId":2,"crtTime":1529942400000},"tunnel":{"id":2,"name":"长虹路"},"action":1,"actionName":"参观","preTime":null,"enterTime":null,"exitTime":null,"crtTime":1533714203000,"visitorNumber":null,"visitorInfo":null,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000}},{"id":7,"createTime":1533696554000,"processType":1003,"processTypeName":"入廊申请","processStatus":"流程结束","processInstanceId":"22501","isFinished":true,"approverId":1,"staff":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"tunnel":{"id":1,"name":"凤岭北路"},"action":1,"actionName":"参观","preTime":null,"enterTime":null,"exitTime":null,"crtTime":1533696554000,"visitorNumber":null,"visitorInfo":null,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000}}],"pageNum":1,"pageSize":5,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
     * @throws
     * @author shaosen
     * @date 2018年6月27日
     */
    @RequestMapping(value = "req-historys/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody ReqHistoryVo vo) {
        PageInfo<ReqHistoryDto> pageInfo = reqHistoryService.dataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    /**
     * 通过绑定设备的人id获取详情
     * @param staffId 外来人员id
     * @return
     * @author ya.liu
     * @Date 2019年3月6日
     */
    @RequestMapping(value = "req-historys/{staffId}/staff", method = RequestMethod.GET)
    public JSONObject getReqHistoryByStaffId(@PathVariable("staffId") Integer staffId) {
    	// 获取人员信息以及申请入廊的信息
    	StaffDto staff = staffService.getDtoById(staffId);
    	ReqHistoryDto reqHistory = getReq(staffId);
    	List<StaffDto> list = new ArrayList<>();
    	list.add(staff);
    	reqHistory.setList(list);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, reqHistory);
    }
    
    /**
     * 获取申请入廊人员信息
     * @return
     * @author liuya
     * @Date 2018年9月13日
     */
    @RequestMapping(value = "req-historys/visitors", method = RequestMethod.GET)
    public JSONObject getDtoList() {
    	ReqHistoryVo vo = new ReqHistoryVo();
    	vo.setIsFinished(false);
        List<ReqHistoryDto> list = reqHistoryService.getDtoListByCondition(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 将图片输出流直接返回给前端
     *
     * @param  id 入廊申请id
     * @return void
     * @throws IOException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws
     * @author shaosen
     * @date 2018年7月5日
     */
    @RequestMapping(value = "req-historys/{id}/activiti-png", method = RequestMethod.GET)
    public JSONObject getPng(@PathVariable Integer id, HttpServletResponse response) throws FileNotFoundException, IOException {
        ReqHistoryDto dto = reqHistoryService.getDtoById(id);
        if (dto == null)
            throw new RuntimeException("获取不到id=" + id + "的入廊申请记录");
        activitiService.getImageByProcessInstance(dto.getProcessInstanceId(), response);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 审批入廊申请
     *
     * @param  processInstanceId 流程实例id
     * @param  value 1:同意;2:不同意
     * @param  remark 备注（可选）
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws UnsupportedEncodingException
     * @throws
     * @author shaosen
     * @date 2018年7月6日
     */
    @RequestMapping(value = "users/activiti/req-historys/audit", method = RequestMethod.POST)
    public JSONObject auditRequest(@RequestBody AuditVo vo) throws UnsupportedEncodingException {
        reqHistoryService.auditRequest(vo.getProcessInstanceId(), vo.getValue(), vo.getRemark());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 确认出廊
     *
     * @param  id 入廊申请id
     * @param  processInstanceId 流程实例id
     * @param  exitTime 出廊时间（时间戳格式：1531723374075）
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年7月26日
     */
    @RequestMapping(value = "users/out/req-historys", method = RequestMethod.PUT)
    public JSONObject confirm(@RequestBody ReqHistory vo) {
        reqHistoryService.confirm(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    
    //------------------------------外来人员入廊模块----------------------------------
    
    /**
     * maxView定时发送在廊人员的定位信息
     * @param staffId 人id
     * @param equipmentId 设备id
     * @param time 时间
     * @param longitude 经度
     * @param latitude 纬度
     * @param height 高度
     * @return
     * @author ya.liu
     * @Date 2019年3月4日
     */
    @RequestMapping(value = "req-record", method = RequestMethod.POST)
    public JSONObject insertReqRecord(@RequestBody ReqRecord record) {
    	
    	reqRecordService.insertSelective(record);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 修改在廊人员位置信息
     * @param id
     * @param staffId 人id
     * @param equipmentId 设备id
     * @param time 时间
     * @param longitude 经度
     * @param latitude 纬度
     * @param height 高度
     * @return
     * @author ya.liu
     * @Date 2019年3月4日
     */
    @RequestMapping(value = "req-record", method = RequestMethod.PUT)
    public JSONObject updateReqRecord(@RequestBody ReqRecord record) {
    	
    	reqRecordService.updateByIdSelective(record);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 获取在廊人员某一点的位置信息
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年3月4日
     */
    @RequestMapping(value = "req-record/{id}", method = RequestMethod.GET)
    public JSONObject getReqRecordById(@PathVariable("id") Integer id) {
    	
    	ReqRecordDto dto = reqRecordService.selectById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }
    
    /**
     * 条件查询轨迹
     * @param staffId 入廊人id
     * @param equipmentId 设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author ya.liu
     * @Date 2019年3月4日
     */
    @RequestMapping(value = "req-record/condition", method = RequestMethod.POST)
    public JSONObject getReqRecordDtoByCondition(@RequestBody ReqRecordVo vo) {
    	
    	List<ReqRecord> list = reqRecordService.getListByCondition(vo);
    	// 时间间隔超过十分钟视为下一次入廊
    	List<List<ReqRecord>> result = new ArrayList<>();
    	if(list != null && list.size() > 0) {
    		List<ReqRecord> mid = new ArrayList<>();
    		for(ReqRecord req : list) {
    			if(mid.size() < 1) {
    				mid.add(req);
    				continue;
    			}
    			Date firstTime = mid.get(mid.size() - 1).getTime();
    			Date secondTime = req.getTime();
    			long time = DateUtil.dateDiff(firstTime, secondTime);
    			if(time - 10 * 60 * 1000 > 0) {
    				result.add(new ArrayList<>(mid));
    				mid.clear();
    				mid.add(req);
    			}else {
    				mid.add(req);
    			}
    		}
    		result.add(mid);
    	}
    	// 处理返回数据
    	List<JSONObject> objs = new ArrayList<>();
    	for(int i=0;i<result.size();i++) {
    		JSONObject obj = new JSONObject();
    		obj.put("list", result.get(i));
    		obj.put("id", i+1);
    		objs.add(obj);
    	}
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, objs);
    }
    
    /**
     * 返回该用户最新一次的入廊信息
     * @param id staffId
     * @return
     * @author ya.liu
     * @Date 2019年3月6日
     */
    private ReqHistoryDto getReq(Integer id) {
    	ReqHistoryVo vo = new ReqHistoryVo();
    	vo.setVisitorInfo(id.toString());
    	List<ReqHistoryDto> list = reqHistoryService.getDtoListByCondition(vo);
    	return list.size() > 0 ? list.get(0) : null;
    }
    
    /**
     * 分页获取入廊人员信息
     * @param staffId 入廊人id
     * @param equipmentId 设备id
     * @param staffName 入廊人姓名，支持模糊查询
     * @param telphone 手机号码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageSize
     * @param pageNum
     * @return
     * @author ya.liu
     * @Date 2019年3月5日
     */
    @RequestMapping(value = "req-record/datagrid", method = RequestMethod.POST)
    public JSONObject getStaffByCondition(@RequestBody ReqRecordVo vo) {
    	
    	List<ReqRecordDto> list = reqRecordService.getDtoListByCondition(vo);
    	if(vo.getStaffName() != null)
    		list = list.stream().filter(a -> a.getStaff().getName().indexOf(vo.getStaffName()) > -1).collect(Collectors.toList());
    	if(vo.getTelphone() != null)
    		list = list.stream().filter(a -> a.getStaff().getTelphone().indexOf(vo.getTelphone()) > -1).collect(Collectors.toList());
    	
    	Set<Staff> set = new HashSet<>();
    	for(ReqRecordDto dto : list) {
    		set.add(dto.getStaff());
    	}
    	List<Staff> dtos = new ArrayList<>(set);
    	ListPageUtil<Staff> page = new ListPageUtil<>(dtos, vo.getPageNum(), vo.getPageSize());
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, page);
    }
}
