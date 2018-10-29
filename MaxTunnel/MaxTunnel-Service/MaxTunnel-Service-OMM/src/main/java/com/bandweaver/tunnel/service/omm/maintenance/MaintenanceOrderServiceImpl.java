package com.bandweaver.tunnel.service.omm.maintenance;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.omm.MaintenanceOrderDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.omm.DefectService;
import com.bandweaver.tunnel.common.biz.itf.omm.MaintenanceOrderService;
import com.bandweaver.tunnel.common.biz.pojo.omm.MaintenanceOrder;
import com.bandweaver.tunnel.common.biz.vo.omm.MaintenanceOrderVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.MaintenanceOrderMapper;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaintenanceOrderServiceImpl implements MaintenanceOrderService {

    @Autowired
    private MaintenanceOrderMapper maintenanceOrderMapper;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private DefectService defectService;
    @Autowired
    private TaskService taskService;

    @Override
    public int add(int tunnelId, int defectId) {

        LogUtil.info("维修工单，管廊ID：" + tunnelId + " 缺陷Id：" + defectId);
        MaintenanceOrder maintenanceOrder = new MaintenanceOrder();
        Date date = new Date();
        maintenanceOrder.setOrderId(date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "-" + defectId);
        maintenanceOrder.setTunnelId(tunnelId);
        maintenanceOrder.setDefectId(defectId);
        maintenanceOrder.setCreateTime(date);
        maintenanceOrder.setProcessType(ProcessTypeEnum.MAINTENANCE_ORDER.getValue());
        maintenanceOrder.setProcessTypeName(ProcessTypeEnum.MAINTENANCE_ORDER.getName());

        return add(maintenanceOrder);
    }

    @Override
    public int add(MaintenanceOrder maintenanceOrder) {

        LogUtil.info("待添加工单：" + maintenanceOrder);
        // 启动工单
        // 默认将分配任务交给管理员
        maintenanceOrder.setProcessInstanceId(activitiService.startProcessInstance(ProcessTypeEnum.MAINTENANCE_ORDER, 1));
        // 将工单添加进数据库
        int id = maintenanceOrderMapper.add(maintenanceOrder);
        // 第一步自动结束
        activitiService.completeTaskByProcessIntance(maintenanceOrder.getProcessInstanceId());

        return maintenanceOrder.getDefectId();
    }

    @Override
    public int update(MaintenanceOrder maintenanceOrder) {
        return maintenanceOrderMapper.update(maintenanceOrder);
    }

    @Override
    public MaintenanceOrderDto getOrder(Integer id) {
        return maintenanceOrderMapper.getOrder(id);
    }

    @Override
    public MaintenanceOrder getOrderByInstance(String instanceId) {
        return maintenanceOrderMapper.getOrderByInstance(instanceId);
    }

    @Override
    public List<MaintenanceOrderDto> getMaintenanceOrderDtosByTunnel(Integer tunnelId) {
        return maintenanceOrderMapper.getMaintenanceOrderDtosByTunnel(tunnelId);
    }

    @Override
    public List<MaintenanceOrderDto> getMaintenanceOrderDtosByCondition(MaintenanceOrderVo vo) {
        return maintenanceOrderMapper.getMaintenanceOrderDtosByCondition(vo);
    }

    /**
     * 设置维修人员
     *
     * @param id
     * @param personId
     */
    @Override
    public void setMaintenancePerson(int id, int personId) {
        MaintenanceOrder maintenanceOrder = getOrder(id);
        Map<String, Object> map = new HashMap<>();
        map.put("var1", personId);
        activitiService.completeTaskByProcessIntance(maintenanceOrder.getProcessInstanceId(), map);
    }
    
    /**
     * 设置维修人员
     *
     * @param id
     * @param personId
     * @param remark
     */
    @Override
    public void setMaintenancePersonAndRemark(Integer id, Integer personId, String remark) {
        MaintenanceOrder maintenanceOrder = getOrder(id);
        Map<String, Object> map = new HashMap<>();
        map.put("var1", personId);
        Task task = taskService.createTaskQuery().processInstanceId(maintenanceOrder.getProcessInstanceId()).singleResult();
        // 这个流程已经没有任务了
        if (task == null) {
        	LogUtil.info("无任务");
        	return;
        }
        activitiService.completeTask(task.getId(), map, remark);
    }

    /**
     * 完结维修工单
     *
     * @param id
     */
    @Override
    public void completeMaintenanceOrder(int id) {
        MaintenanceOrder maintenanceOrder = getOrder(id);
        activitiService.completeTaskByProcessIntance(maintenanceOrder.getProcessInstanceId());

        //更改defect的状态
        defectService.maintComplete(id);
    }
}
