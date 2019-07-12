package com.bandweaver.tunnel.service.em;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.util.TypeUtils;
import com.bandweaver.tunnel.common.biz.constant.em.*;
import com.bandweaver.tunnel.common.platform.record.ProcessRecord;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.NodeStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.itf.mam.mapping.MeasObjMapService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.SubSystemService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.dao.em.EmPlanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class EmPlanServiceImpl implements EmPlanService {

    @Autowired
    private EmPlanMapper emPlanMapper;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private MeasObjService measObjService;
    @Autowired
    private MeasObjMapService measObjMapService;
    @Autowired
    private MqService mqService;
    @Autowired
    private SubSystemService subSystemService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private VideoServerService videoServerService;

    private final static String KEY_LINE = "message";

    @Override
    public void doBusiness(ActivitiEvent activitiEvent, TaskEntity taskEntity) {

        LogUtil.info("触发doBusiness");
        String processDefinitionId = activitiEvent.getProcessDefinitionId();
        ProcessDefinition processDefinition = activitiService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            throw new BandWeaverException("流程尚未部署");
        }
        LogUtil.info("Get KEY:" + processDefinition.getKey());

        Map<String, Object> variables = runtimeService.getVariables(activitiEvent.getExecutionId());
        LogUtil.info("Get variables:" + variables);
        List<Section> sectionList = (List<Section>) variables.get("sectionList");

        EmPlan emPlan = getEmPlanByProcessKeyAndTaskKey(processDefinition.getKey(), taskEntity.getTaskDefinitionKey());
        LogUtil.info("Get emPlan from DB:" + emPlan);
        if (StringTools.isNullOrEmpty(emPlan)) {
            throw new BandWeaverException("流程节点不存在");
        }

        LogUtil.info("第一步");
        // 第一步：获取目标
        Collection<MeasObj> list = new ArrayList<>();
        TargetEnum targetEnum = TargetEnum.getEnum(emPlan.getTargetKey());

        if (TargetEnum.ASSIGN_TO == targetEnum) {
            list = measObjService.getMeasObjByTargetVal(emPlan.getTargetValue());
        }
        if (TargetEnum.TYPE == targetEnum) {
            list = measObjService.getMeasObjsByTargetValAndSection(emPlan.getTargetValue(), sectionList);
        }
        LogUtil.info("第一步结束，获得list" + list.toString());

        LogUtil.info("第2步");
        // 第二步：做什么事情
        String nextTaskValue = "-1";
        ActionEnum actionEnum = ActionEnum.getEnum(emPlan.getActionKey());
        LogUtil.info("actionEnum：" + actionEnum.toString());
        /**
         * 不需要结果
         */
        switch (actionEnum) {
            case NONE:
                break;

            /**
             * 需要结果
             */
            case CHECK:
                Integer integer = TypeUtils.castToInt(emPlan.getActionValue());
                CheckTypeEnum checkTypeEnum = CheckTypeEnum.getEnum(integer);
                switch (checkTypeEnum) {
                    case CHECK_FAN:
                        nextTaskValue = checkFanOpen();
                        break;
                    case CHECK_VALUE:
                        nextTaskValue = checkValue();
                        break;
                    case CHECK_LOUVER:
                        nextTaskValue = checkLouverOpen();
                        break;
                    default:
                        break;
                }
                break;

            /**
             * 自动联动控制
             */
            case SWITCH:
                Integer inputValue = TypeUtils.castToInt(emPlan.getActionValue());
                for (MeasObj measObj : list) {
//                    boolean flag = subSystemService.doAction(measObj.getId(), inputValue);
                    boolean flag = subSystemService.controlOutput(measObj.getId(), "open");
//                    boolean flag = true;
                    LogUtil.info("监测对象[" + measObj.getName() + "]执行[ " + SwitchEnum.getEnum(inputValue).getName() + " ]结果[" + flag + "]");
                }
                break;

            /**
             * 手动联动控制
             */
            case MANNUL_SWITCH:
                // 什么都不做，在前端页面控制，但是需要把检测对象id传过去
                break;

            default:
                break;
        }

        LogUtil.info("向" + activitiEvent.getProcessInstanceId() + "流程record中添加参数");

        String processInstanceId = activitiEvent.getProcessInstanceId();

        ProcessRecord processRecord = ProcessRecord.getProcessRecord();
        processRecord.setEmPlanHashMap(processInstanceId, emPlan);
        processRecord.setNextTaskValueHashMap(processInstanceId, nextTaskValue);
        LogUtil.info("---------------存储流程，ID:" + processInstanceId + " " + activitiEvent.getExecutionId());
    }

    private String checkValue() {
        return "0";
    }

    private String checkLouverOpen() {
        // todo
        return "0";
    }

    private String checkFanOpen() {
        // todo
        return "1";
    }


    @Override
    public void nextTask(String processInstanceId, List<Section> sectionList) {

        LogUtil.info("流程中nextTask");
        boolean isFinished = activitiService.isProccessInstanceFinished(processInstanceId);
        // 前一个taskKey
        String preTaskKey = "";

        while (!isFinished) {

            // 从record中提取参数
            try {

                ProcessRecord processRecord = ProcessRecord.getProcessRecord();
                EmPlan emPlan = processRecord.getEmPlan(processInstanceId);
                String nextTaskValue = processRecord.getNextTaskValue(processInstanceId);

                // 如果流程一步还没进行好
                if (emPlan == null || nextTaskValue == null) {
                    Thread.sleep(100);
                    continue;
                }

                isFinished = activitiService.isProccessInstanceFinished(processInstanceId);
                LogUtil.info("流程是否已经结束:" + isFinished);
                if (isFinished) {
                    // 发送结束消息
                    sendMsg(processInstanceId, "finished");
                    break;
                }

                LogUtil.info("emPlan.getTaskKey():" + emPlan.getTaskKey() + " preTaskKey:" + preTaskKey);
                // 如果流程还没跳到下一步
                if (emPlan.getTaskKey().equals(preTaskKey)) {
                    Thread.sleep(100);
                    continue;
                }

                LogUtil.info("-----------------获取流程参数，ID:" + processInstanceId);
                LogUtil.info("emPlan:" + emPlan);
                LogUtil.info("nextTaskValue:" + nextTaskValue);

                // 将当前流程步骤更新
                preTaskKey = emPlan.getTaskKey();

                // 第三步：怎样结束
                FinishEnum finishEnum = FinishEnum.getEnum(emPlan.getFinishKey());

                Map<String, Object> map = new HashMap<>(3);
                map.put(KEY_LINE, nextTaskValue);

                if (Objects.equals("-1", nextTaskValue)) {
                    if (FinishEnum.AUTO == finishEnum) {
                        // 自动结束
                        sendMsg(processInstanceId, "running");
                        LogUtil.info("流程实例进行下一步（-1）:" + processInstanceId);
                        activitiService.completeTaskByProcessIntance(processInstanceId);
                        continue;
                    }

                } else {
                    if (FinishEnum.AUTO == finishEnum) {
                        // 自动结束
                        sendMsg(processInstanceId, "running");
                        LogUtil.info("流程实例进行下一步:" + processInstanceId);
                        activitiService.completeTaskByProcessIntance(processInstanceId, map);
                        continue;
                    }

                }

                if (FinishEnum.MANUAL == finishEnum) {
                    // 手动结束
                    sendMsg(processInstanceId);
                    break;
                }

            } catch (Exception e) {
                break;
            }
        }
    }


    /**
     * 发送消息到MQ队列
     *
     * @param emPlan
     * @param processInstanceId
     */
    @Override
    public void sendMsg(String processInstanceId) {

        JSONObject json = new JSONObject();
        json.put("processInstanceId", processInstanceId);

        // 将消息广播出去
        mqService.sendByType("Plan", json.toJSONString());
    }

    /**
     * 发送消息和状态到mq
     *
     * @param processInstanceId
     * @param status
     */
    @Override
    public void sendMsg(String processInstanceId, String status) {

        // sleep一段时间为了更好的看到效果
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("processInstanceId", processInstanceId);
        json.put("status", status);

        // 将消息广播出去
        mqService.sendByType("Plan", json.toJSONString());
    }

    public EmPlan getEmPlanByProcessKeyAndTaskKey(String processKey, String taskKey) {
        return emPlanMapper.getEmPlanByProcessKeyAndTaskKey(processKey, taskKey);
    }


    @Override
    public String start(List<Section> sectionList, Integer processValue) {

        ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(processValue);
        LogUtil.info("开始启动【" + processTypeEnum.getName() + "】");

        Map<String, Object> variables = new HashMap<>(3);
        variables.put("sectionList", sectionList);

        ProcessDefinition processDefinition = activitiService.getLastestProcessDefinition(PropertiesUtil.getString(processTypeEnum.getBpmnPath()));
        ProcessInstance processInstance = activitiService.startProcessInstanceById(processDefinition.getId(), variables);
        LogUtil.info("Get processInstance:" + processInstance.getId());

        LogUtil.info("生成新线程：" + processInstance.getId());
        EmPlanNextTaskThread thread = new EmPlanNextTaskThread(processInstance.getId(), sectionList);
        LogUtil.info("启动线程");
        thread.start();
        LogUtil.info("线程已启动");

        return processInstance.getId();
    }


    @Override
    public void add(EmPlan record) {
        record.setCrtTime(DateUtil.getCurrentDate());
        emPlanMapper.insertSelective(record);
    }


    @Override
    public void delete(Integer id) {
        emPlanMapper.deleteByPrimaryKey(id);
    }


    @Override
    public void update(EmPlan record) {
        emPlanMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public EmPlanDto getById(Integer id) {
        return emPlanMapper.getById(id);
    }


    @Override
    public void deleteBatch(List<Integer> list) {
        emPlanMapper.deleteBatch(list);
    }


    @Override
    public PageInfo<EmPlanDto> dataGrid(EmPlanVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<EmPlanDto> list = getByCondition(vo);
        return new PageInfo<>(list);
    }


    private List<EmPlanDto> getByCondition(EmPlanVo vo) {
        List<EmPlanDto> list = emPlanMapper.getByCondition(vo);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }


    @Override
    public List<JSONObject> getNodeListByProcessKeyAndSection(String processKey, List<Section> sectionList) {

        List<EmPlanDto> list = emPlanMapper.getNodeListByProcessKey(processKey);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        // 通过taskKey从小到大排列
        list = list.stream().sorted(Comparator.comparing(EmPlanDto::getTaskKey)).collect(Collectors.toList());

        // 从缓存中获取所有监测对象
        List<MeasObj> allMeasObjList = measObjService.getAllMeasObjList();
        List<JSONObject> returnData = new ArrayList<>();

        // 遍历获取每个节点详细信息
        for (EmPlanDto emPlanDto : list) {

            List<JSONObject> objJsonList = getMeasObjJsonList(sectionList, allMeasObjList, emPlanDto);

            JSONObject j = new JSONObject();
            j.put("stepName", emPlanDto.getTaskName());
            j.put("targetName", emPlanDto.getTargetName());
            j.put("targetValue", emPlanDto.getTargetValue());
            j.put("actionName", emPlanDto.getActionName());
            j.put("actionValue", emPlanDto.getActionValue());
            j.put("finishName", emPlanDto.getFinishName());
            j.put("finishValue", emPlanDto.getFinishValue());

            j.put("statusStr", NodeStatusEnum.WAITING.getName());
            j.put("statusVal", NodeStatusEnum.WAITING.getValue());
            j.put("objectList", objJsonList);
            j.put("isSwitch", emPlanDto.getActionKey() == ActionEnum.SWITCH.getValue());
            returnData.add(j);
        }
        return returnData;
    }


    public List<JSONObject> getMeasObjJsonList(List<Section> sectionList, List<MeasObj> allMeasObjList, EmPlanDto emPlanDto) {

        List<Integer> objectIdList = new ArrayList<>();
        List<JSONObject> objJsonList = new ArrayList<>();

        if (null != sectionList) {
            for (Section section : sectionList) {
                if (TargetEnum.getEnum(emPlanDto.getTargetKey()) == TargetEnum.ASSIGN_TO) {
                    // 如果是指定对象，直接返回对象id
                    String targetValue = emPlanDto.getTargetValue();
                    objectIdList = CommonUtil.convertStringToList(targetValue);
                } else {
                    // 如果是指定类型，需要通过sectionId和objectType去查找id集合
                    Integer objectTypeId = ObjectType.getEnum(DataTypeUtil.toInteger(emPlanDto.getTargetValue())).getValue();
                    List<MeasObj> measObjList = allMeasObjList.stream().filter(m -> m.getSectionId().intValue() == section.getId().intValue()
                            && m.getObjtypeId().intValue() == objectTypeId.intValue()).collect(Collectors.toList());
                    for (MeasObj m : measObjList) {
                        objectIdList.add(m.getId());
                    }
                }
            }
        }

        // 获取监测对象id，name和当前开关状态
        for (Integer objId : objectIdList) {

            MeasObj measObj = measObjService.get(objId);
            JSONObject objectJson = new JSONObject();
            objectJson.put("id", objId);
            objectJson.put("name", measObj == null ? "" : measObj.getName());
            objectJson.put("objtypeId", measObj.getObjtypeId());
            double cv = measObjService.getMeasObjCVByIdAndDataType(objId, measObj.getDatatypeId());
            objectJson.put("cv", cv);
            objJsonList.add(objectJson);

        }
        return objJsonList;
    }


    @Override
    public List<EmPlanDto> getListByProcessKey(String processKey) {

        List<EmPlanDto> list = emPlanMapper.getNodeListByProcessKey(processKey);
        return list == null ? Collections.emptyList() : list;

    }

}
