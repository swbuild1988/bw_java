package com.bandweaver.tunnel.service.em;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.NodeStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.constant.em.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FinishEnum;
import com.bandweaver.tunnel.common.biz.constant.em.TargetEnum;
import com.bandweaver.tunnel.common.biz.constant.em.UnitTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
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
import com.bandweaver.tunnel.common.biz.pojo.Area;
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

    @Override
    public void doBusiness(ActivitiEvent activitiEvent, TaskEntity taskEntity) {

        String processDefinitionId = activitiEvent.getProcessDefinitionId();
        ProcessDefinition processDefinition = activitiService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            throw new BandWeaverException("流程尚未部署");
        }
        LogUtil.debug("Get KEY:" + processDefinition.getKey());

        Map<String, Object> variables = runtimeService.getVariables(activitiEvent.getExecutionId());
        LogUtil.debug("Get variables:" + variables);
        List<Section> sectionList = (List<Section>) variables.get("sectionList");

        EmPlan emPlan = getEmPlanByProcessKeyAndTaskKey(processDefinition.getKey(), taskEntity.getTaskDefinitionKey());
        LogUtil.debug("Get emPlan from DB:" + emPlan);
        if (StringTools.isNullOrEmpty(emPlan)) {
            throw new BandWeaverException("流程节点不存在");
        }

        // 第一步：获取目标
        Collection<MeasObj> list = new ArrayList<>();
        TargetEnum targetEnum = TargetEnum.getEnum(emPlan.getTargetKey());

        if (TargetEnum.ASSIGN_TO == targetEnum) {
            list = measObjService.getMeasObjByTargetVal(emPlan.getTargetValue());
        }
        if (TargetEnum.TYPE == targetEnum) {
            list = measObjService.getMeasObjsByTargetValAndSection(emPlan.getTargetValue(), sectionList);
        }

        // 第二步：做什么事情
        ActionEnum actionEnum = ActionEnum.getEnum(emPlan.getActionKey());
        if (ActionEnum.METION == actionEnum) {
            LogUtil.info("提示信息为：" + emPlan.getActionValue());
            //TODO
        }
        if (ActionEnum.NOTICE == actionEnum) {
            Integer unitTypeId = DataTypeUtil.toInteger(emPlan.getActionValue());
            LogUtil.info("通知单位类型为：" + UnitTypeEnum.getEnum(unitTypeId).getName());
            //TODO
        }
        if (ActionEnum.SWITCH == actionEnum) {
            Integer inputValue = DataTypeUtil.toInteger(emPlan.getActionValue());
            for (MeasObj measObj : list) {
//                if (ObjectType.getEnum(measObj.getObjtypeId()) == ObjectType.FAN) {continue;}
                boolean flag = subSystemService.doAction(measObj.getId(), inputValue);
                LogUtil.info("监测对象[" + measObj.getName() + "]执行[ " + SwitchEnum.getEnum(inputValue).getName() + " ]结果[" + flag + "]");
            }
        }

        ContextUtil.getSession().setAttribute("emPlan", emPlan);
        ContextUtil.getSession().setAttribute("measObjList", list);
    }


    @Override
    public void nextTask(String processInstanceId, List<Section> sectionList) {

        boolean isFinished = false;
        while (!isFinished) {
            EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute("emPlan");
            Set<MeasObj> measObjList = (Set<MeasObj>) ContextUtil.getSession().getAttribute("measObjList");
            LogUtil.debug("Get emPlan from session:" + emPlan + "\n"
                    + "Get emPlan from session:" + measObjList);

            if (emPlan == null) {
                isFinished = true;
                throw new BandWeaverException("流程节点未配置");
            } else if (emPlan != null && (emPlan.getFinishKey() == FinishEnum.MANUAL.getValue())) {
                isFinished = true;
            } else {
                isFinished = emPlan.getIsFinished();
            }

            // 第三步：怎样结束
            FinishEnum finishEnum = FinishEnum.getEnum(emPlan.getFinishKey());
            if (FinishEnum.AUTO == finishEnum) {
                LogUtil.info("自动结束");
                sendMsg(emPlan, processInstanceId, sectionList, measObjList);
                activitiService.completeTaskByProcessIntance(processInstanceId);
                continue;
            }
            if (FinishEnum.MANUAL == finishEnum) {
                LogUtil.info("手动结束,需要确认");
                sendMsg(emPlan, processInstanceId, sectionList, measObjList);
                continue;
            }

        }
    }


    /*
     * 发送消息到MQ队列
     */
    public void sendMsg(EmPlan emPlan, String processInstanceId, List<Section> sectionList, Set<MeasObj> measObjList) {

        // sleep一段时间为了更好的看到效果
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        double longitude = 0, latitude = 0;
        // 获取指定仓
        Section section = null;
        List<Section> collect = sectionList.stream().filter(s -> s.getId().intValue() == s.getParentId().intValue()).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            section = collect.get(0);
        }
        if (section != null) {

            double longitude_ = 0, latitude_ = 0;
            String startPoint = section.getStartPoint();
            if (!StringTools.isNullOrEmpty(startPoint)) {
                String[] startPointArr = startPoint.split(",");
                if (startPointArr.length == 3) {
                    longitude_ = DataTypeUtil.toDouble(startPointArr[0]);
                    latitude_ = DataTypeUtil.toDouble(startPointArr[1]);
                }
            }

            double _longitude = 0, _latitude = 0;
            String endPoint = section.getEndPoint();
            if (!StringTools.isNullOrEmpty(endPoint)) {
                String[] endPointArr = endPoint.split(",");
                if (endPointArr.length == 3) {
                    _longitude = DataTypeUtil.toDouble(endPointArr[0]);
                    _latitude = DataTypeUtil.toDouble(endPointArr[1]);
                }
            }

            longitude = MathUtil.div(MathUtil.add(longitude_, _longitude), 2.0, 6);
            latitude = MathUtil.div(MathUtil.add(latitude_, _latitude), 2.0, 6);
        }

        //获取当前流程节点列表
        List<JSONObject> processList = new ArrayList<>();
        List<EmPlanDto> eList = getListByProcessKey(emPlan.getProcessKey());
        eList = eList.stream().filter(e -> emPlan.getTaskKey().compareTo(e.getTaskKey()) >= 0).collect(Collectors.toList());

        List<MeasObj> allMeasObjList = measObjService.getAllMeasObjList();

        for (EmPlanDto e : eList) {

            //获取该节点关联的监测对象及状态
            List<JSONObject> objJsonList = getMeasObjJsonList(sectionList, allMeasObjList, e);

            JSONObject json = new JSONObject();
            json.put("node", e.getTaskName());
            json.put("status", NodeStatusEnum.FINISHED.getValue());
            json.put("objectList", objJsonList);
            json.put("isSwitch", e.getActionKey() == ActionEnum.SWITCH.getValue());
            processList.add(json);
        }

        //获取section视频列表
        List<VideoDto> videos = new ArrayList<>();
        sectionList.forEach(s -> {
            videos.addAll(videoServerService.getVideosBySection(s.getId()));
        });

        ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(emPlan.getProcessKey());
        JSONObject json = new JSONObject();
        json.put("processName", processTypeEnum.getName());
        json.put("processKey", emPlan.getProcessKey());
        json.put("processInstanceId", processInstanceId);
        json.put("range", processTypeEnum.getRange());
        json.put("process", processList);
        json.put("nodeList", getNodeListByProcessKeyAndSection(emPlan.getProcessKey(), sectionList));
        json.put("longitude", longitude);
        json.put("latitude", latitude);
        json.put("videos", videos);

        //发送到队列
        mqService.sendToPlanUMQueue(json.toJSONString());
        mqService.sendToPlanVMQueue(json.toJSONString());
    }


    public EmPlan getEmPlanByProcessKeyAndTaskKey(String processKey, String taskKey) {
        return emPlanMapper.getEmPlanByProcessKeyAndTaskKey(processKey, taskKey);
    }


    @Override
    public void start(List<Section> sectionList, Integer processValue) {

        ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(processValue);
        LogUtil.info("开始启动【" + processTypeEnum.getName() + "】");

        Map<String, Object> variables = new HashMap<>(16);
        variables.put("sectionList", sectionList);

        ProcessDefinition processDefinition = activitiService.getLastestProcessDefinition(PropertiesUtil.getString(processTypeEnum.getBpmnPath()));
        ProcessInstance processInstance = activitiService.startProcessInstanceById(processDefinition.getId(), variables);
        LogUtil.debug("Get processInstance:" + processInstance);
        ContextUtil.getSession().setAttribute("processInstanceId", processInstance.getId());
        nextTask(processInstance.getId(), sectionList);

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
