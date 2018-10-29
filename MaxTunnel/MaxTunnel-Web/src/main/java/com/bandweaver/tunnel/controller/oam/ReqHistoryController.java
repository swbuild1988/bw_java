package com.bandweaver.tunnel.controller.oam;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ReqHistoryDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqHistoryService;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqHistory;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.AuditVo;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
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
        
      //获取审批结果
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
     * 获取申请入廊人员信息
     * @return
     * @author liuya
     * @Date 2018年9月13日
     */
    @RequestMapping(value = "req-historys/visitors", method = RequestMethod.GET)
    public JSONObject getDtoList() {
    	ReqHistoryVo vo = new ReqHistoryVo();
        List<ReqHistoryDto> list = reqHistoryService.getDtoListByCondition(vo);
        Set<Map<String,String>> set = new HashSet<>();
        list = list.stream().filter(a -> a.getIsFinished() == false).collect(Collectors.toList());
        for(ReqHistoryDto dto : list) {
        	String company = dto.getCompany().getName();
        	String [] str = dto.getVisitorInfo().split(",");
        	for(int i=0;i<str.length;i++) {
        		Map<String,String> map = new HashMap<>();
        		String [] s = str[i].split("-");
        		//判断名字，身份证和手机号是否都存在
        		if(null == s || s.length < 3) continue;
        		if("null".equals(s[0]) && "null".equals(s[1]) && "null".equals(s[2]))
        			continue;
        		map.put("name", s[0]);
        		map.put("idCard", s[1]);
        		map.put("phoneNum", s[2]);
        		map.put("companyName", company);
        		set.add(map);
        	}
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, set);
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
}
