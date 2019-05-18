package com.bandweaver.tunnel.service.em;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.em.TemperatureAndHumidityAlarmSopService;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemperatureAndHumidityAlarmSopServiceImpl implements TemperatureAndHumidityAlarmSopService {


    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ActivitiService activitiService;

    @Override
    public void start() {

        List<Deployment> deployList = processEngine.getRepositoryService().createDeploymentQuery()
                .processDefinitionKey(ProcessTypeEnum.TEMPERATURE_HUMIDITY_ALARM_SOP.getProcessKey())
                .list();
        if (deployList == null || deployList.isEmpty()) {
            throw new BandWeaverException("消防预案流程尚未发布");
        }

        // 启动流程
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(ProcessTypeEnum.TEMPERATURE_HUMIDITY_ALARM_SOP.getProcessKey());
        LogUtil.info("消防预案启动成功，流程实例id:" + processInstance.getId());

        // 查看过去4小时数据
        List<String> dataList = new ArrayList<String>();
        LogUtil.info("查看过去4小时数据");
        // todo

        completeTask(processInstance);

        // 数据是否渐变
        checkLast4HoursData(dataList, processInstance);
    }

    @Override
    public void getPng(String processInstance, HttpServletResponse response) {
        activitiService.getImageByProcessInstance(processInstance, response);
    }

    /**
     * 数据是否渐变
     *
     * @param dataList
     * @param processInstance
     */
    private void checkLast4HoursData(List<String> dataList, ProcessInstance processInstance) {
        //todo
        boolean checkResult = true;

        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
            // 判断同仓的其他传感器数值是否正常
            checkOtherObjectsOfSameStore(processInstance);

        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 温湿度传感器异常，进入设备检查sop
            enterDeviceCheckingSOP(processInstance);
        }

    }

    /**
     * 任务完成
     *
     * @param processInstance
     */
    private void completeTask(ProcessInstance processInstance) {
        completeTask(processInstance, null);
    }


    /**
     * 任务完成
     *
     * @param processInstance
     * @param variables
     */
    private void completeTask(ProcessInstance processInstance, Map<String, Object> variables) {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        LogUtil.info("taskId:" + task.getId());
        LogUtil.info("taskName:" + task.getName());
        taskService.complete(task.getId(), variables);
    }

    /**
     * 进入设备检查SOP
     *
     * @param processInstance
     */
    private void enterDeviceCheckingSOP(ProcessInstance processInstance) {
        // todo
        completeTask(processInstance);
    }

    /**
     * 查看同仓的其他传感器数值是否正常
     *
     * @param processInstance
     */
    private void checkOtherObjectsOfSameStore(ProcessInstance processInstance) {
        boolean checkResult = false;
        // todo

        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
            // 温湿度传感器异常，进入设备检查sop
            enterDeviceCheckingSOP(processInstance);

        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 判断是否为火灾
            checkIfFire(processInstance);
        }

    }

    /**
     * 判断是否为火灾
     *
     * @param processInstance
     */
    private void checkIfFire(ProcessInstance processInstance) {
        boolean checkResult = false;
        // todo

        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
            // 进入火灾应急处理sop
            enterFireEmergencyTreatmentSOP(processInstance);

        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 管廊外温度是否高于管廊内温度
            checkTunnelTemperature(processInstance);

        }
    }

    /**
     * 管廊外温度是否高于管廊内温度
     *
     * @param processInstance
     */
    private void checkTunnelTemperature(ProcessInstance processInstance) {
        //todo
        boolean checkResult = false;
        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
            // 持续观察至正常 1分钟
            keepWatchingUntilNormal(processInstance);

        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 风机是否已启动
            checkFanOpened(processInstance);

        }

    }

    /**
     * 检查风机是否启动
     *
     * @param processInstance
     */
    private void checkFanOpened(ProcessInstance processInstance) {
        // todo
        boolean checkResult = false;
        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
            // 持续观察数值是否正常 1分钟
            keepWatchingTemperature(processInstance);

        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 手动启动风机
            openFan(processInstance);

        }
    }

    /**
     * 手动启动风机
     *
     * @param processInstance
     */
    private void openFan(ProcessInstance processInstance) {
        // todo
        completeTask(processInstance);

        // 检查风机是否启动
        checkFanOpenedAgain(processInstance);
    }

    /**
     * 再次检查风机是否启动
     *
     * @param processInstance
     */
    private void checkFanOpenedAgain(ProcessInstance processInstance) {
        // todo
        boolean checkResult = true;
        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
            // 持续观察数值是否正常 1分钟
            keepWatchingTemperature(processInstance);

        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 转入风机故障SOP
            enterFanIssueSOP(processInstance);
        }
    }

    /**
     * 转入风机故障sop
     *
     * @param processInstance
     */
    private void enterFanIssueSOP(ProcessInstance processInstance) {
        // todo
        completeTask(processInstance);
    }

    /**
     * 持续观察温度数值是否正常
     *
     * @param processInstance
     */
    private void keepWatchingTemperature(ProcessInstance processInstance) {
        // todo
        boolean checkResult = true;
        Map<String, Object> variables = new HashMap<>(3);
        if (checkResult) {
            variables.put("message", "yes");
            completeTask(processInstance, variables);
        } else {
            variables.put("message", "no");
            completeTask(processInstance, variables);
            // 应急SOP
            enterEmergencyTreatmentSOP(processInstance);
        }
    }

    /**
     * 进入应急SOP
     *
     * @param processInstance
     */
    private void enterEmergencyTreatmentSOP(ProcessInstance processInstance) {
        // todo
        completeTask(processInstance);
    }

    /**
     * 持续观察至正常
     *
     * @param processInstance
     */
    private void keepWatchingUntilNormal(ProcessInstance processInstance) {
        // todo
        completeTask(processInstance);
        checkFanOpened(processInstance);
    }

    /**
     * 进入火灾应急处理sop
     *
     * @param processInstance
     */
    private void enterFireEmergencyTreatmentSOP(ProcessInstance processInstance) {
        // todo
        completeTask(processInstance);
    }
}
