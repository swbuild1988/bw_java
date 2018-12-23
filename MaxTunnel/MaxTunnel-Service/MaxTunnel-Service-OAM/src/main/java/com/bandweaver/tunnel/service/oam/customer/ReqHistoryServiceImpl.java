package com.bandweaver.tunnel.service.oam.customer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.oam.ReqHistoryDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqHistoryService;
import com.bandweaver.tunnel.common.biz.pojo.MyTask;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqHistory;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.dao.common.MyTaskMapper;
import com.bandweaver.tunnel.dao.oam.ReqHistoryMapper;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ReqHistoryServiceImpl implements ReqHistoryService {

    @Autowired
    private ReqHistoryMapper reqHistoryMapper;
    @Autowired
    private ActivitiService activitiService;

    @Override
    public void addBatch(List<ReqHistory> list) {
        reqHistoryMapper.addBatch(list);
    }

    @Override
    public ReqHistoryDto getDtoById(Integer id) {
        return reqHistoryMapper.getDtoById(id);
    }

    @Override
    public List<ReqHistoryDto> getDtoListByCondition(ReqHistoryVo vo) {
        return reqHistoryMapper.getDtoListByCondition(vo);
    }

    @Transactional
    @Override
    public void add(ReqHistory history) {

        LogUtil.info(" get history from front : " + history);
        ContextUtil.getSession().setAttribute(ProcessTypeEnum.REQ_HISTORY.getName(), history);

        activitiService.startProcessInstance(ProcessTypeEnum.REQ_HISTORY, history.getApproverId(), history.getStaffId());
        history = (ReqHistory) ContextUtil.getSession().getAttribute(ProcessTypeEnum.REQ_HISTORY.getName());
        reqHistoryMapper.insert(history);

        activitiService.completeTaskByProcessIntance(history.getProcessInstanceId());
    }

    @Override
    public void deleteById(Integer id) {
        reqHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<ReqHistoryDto> dataGrid(ReqHistoryVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<ReqHistoryDto> list = getDtoListByCondition(vo);
        return new PageInfo<>(list);
    }

    @Override
    public void updateById(ReqHistory pojo) {
        reqHistoryMapper.updateByPrimaryKeySelective(pojo);
    }

    @Transactional
    @Override
    public void auditRequest(String processInstanceId, int value, String remark) throws UnsupportedEncodingException {

        Map<String, Object> variables = new HashMap<>();
        variables.put("result", value == 1 ? "agree" : "disagree");
        activitiService.completeTask(activitiService.getActiveTaskByProcessIntance(processInstanceId).getId(), variables, remark);

        LogUtil.info("审批完成");
        LogUtil.info("流程：" + processInstanceId);
        LogUtil.info("结果：" + (value == 1 ? "agree" : "disagree"));
        LogUtil.info("批注：" + remark);
    }

    @Transactional
    @Override
    public void confirm(ReqHistory vo) {
    	LogUtil.info("结束任务前加入出廊时间");
    	ReqHistory his = reqHistoryMapper.selectByPrimaryKey(vo.getId());
    	his.setEnterTime(vo.getEnterTime());
    	his.setExitTime(vo.getExitTime());
    	reqHistoryMapper.updateByPrimaryKeySelective(his);
        Task task = activitiService.getActiveTaskByProcessIntance(his.getProcessInstanceId());
        activitiService.completeTask(task.getId());
        LogUtil.info(" 确认出廊成功！");
    }

    @Override
    public ReqHistory getReqHistoryByProcessInstanceId(String processInstanceId) {
        return reqHistoryMapper.getReqHistoryByProcessInstanceId(processInstanceId);
    }
    
    @Override
    public ReqHistoryDto getReqHistoryDtoByProcessInstanceId(String processInstanceId) {
        return reqHistoryMapper.getReqHistoryDtoByProcessInstanceId(processInstanceId);
    }
}
