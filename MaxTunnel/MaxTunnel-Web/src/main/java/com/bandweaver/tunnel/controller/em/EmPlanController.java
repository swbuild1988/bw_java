package com.bandweaver.tunnel.controller.em;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.SubSystemService;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanGroupDto;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanMemberDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanGroupService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanMemberService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.PageBean;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanGroup;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanMember;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanGroupVo;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanMemberVo;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.bandweaver.tunnel.common.biz.vo.em.PlanPageVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.controller.common.BaseController;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 应急预案管理
 *
 * @author shaosen
 * @date 2018年7月30日
 */
@Controller
@ResponseBody
public class EmPlanController extends BaseController<EmPlan> {

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private EmPlanService emPlanService;
    @Autowired
    private SectionService sectionService;
    
    @Autowired
    private EmPlanGroupService emPlanGroupService;
    @Autowired
    private EmPlanMemberService emPlanMemberService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private SubSystemService subSystemService;


    /**获取流程执行过程的png图片
     * @param processInstanceId 流程实例id
     * @param response
     */
    @RequestMapping(value = "emplans/png/{processInstanceId}",method = RequestMethod.GET)
    public void getynaDmicPng(@PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response) {
        activitiService.getImageByProcessInstance(processInstanceId, response);
    }


    /**获取流程静态图片
     * @param processValue 4003
     * @param response
     */
    @RequestMapping(value = "emplans/png/static/{processValue}",method = RequestMethod.GET)
    public void getStaticPng(@PathVariable("processValue") int processValue, HttpServletResponse response) {
        String pngPath = PropertiesUtil.getString(ProcessTypeEnum.getEnum(processValue).getPngPath());
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pngPath); OutputStream os = response.getOutputStream();){
            response.setContentType("image/png");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            LogUtil.error(ExceptionUtils.getStackTrace(e));
        }


    }


    /**
     * 批量部署
     *
     * @param value "4001,4002,4003"
     * @return
     * @author shaosen
     * @Date 2018年11月29日
     */
    @RequestMapping(value = "emplans/deploy/batch/{value}", method = RequestMethod.GET)
    public JSONObject deploy(@PathVariable String value) {

        String[] arr = value.split(",");
        for (String str : arr) {
            ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(DataTypeUtil.toInteger(str));
            activitiService.deploy(PropertiesUtil.getValue(processTypeEnum.getBpmnPath()),
                     PropertiesUtil.getValue(processTypeEnum.getPngPath()), processTypeEnum.getName());
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 手动执行预案
     *
     * @param
     * @param processValue 应急预案类型
     * @return
     * @author shaosen
     * @Date 2018年12月20日
     */
    @RequestMapping(value = "emplans/start", method = RequestMethod.POST)
    public JSONObject startPlan(@RequestBody JSONObject reqJson) {

        CommonUtil.hasAllRequired(reqJson, "areaId,storeId,processValue");
        Integer areaId = reqJson.getInteger("areaId");
        Integer storeId = reqJson.getInteger("storeId");
        Integer processValue = reqJson.getInteger("processValue");

        Section section = sectionService.getSectionByStoreAndArea(storeId, areaId);
        if (section == null) {
            throw new BandWeaverException("section不存在");
        }
        // 查询仓以及仓关联的进气出气仓等
        List<Section> sectionList = sectionService.getSectionListByParentId(section.getId());
        emPlanService.start(sectionList, processValue);

        // 最后再查一次
        EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute(Constants.EMPLAN_OBJ_KEY);
        String processInstanceId = (String) ContextUtil.getSession().getAttribute(Constants.EMPLAN_PROCESSINSTANCE_ID);
        List<MeasObj> measObjList = (List<MeasObj>) ContextUtil.getSession().getAttribute(Constants.EMPLAN_OBJ_LIST_KEY);
        emPlanService.sendMsg(emPlan, processInstanceId, sectionList, measObjList);
        return CommonUtil.success();
    }


    /**
     * 添加流程节点
     *
     * @param processKey
     * @param taskKey     usertask1
     * @param taskName    开启声光报警
     * @param targetKey   TargetEnum枚举
     * @param targetValue
     * @param actionKey   ActionEnum枚举
     * @param actionValue
     * @param finishKey   FinishEnum枚举
     * @param finishValue
     * @param isFinished  是否结束 0：否 ；1：是
     * @param
     * @return
     * @author shaosen
     * @Date 2018年10月17日
     */
    @RequestMapping(value = "emplans", method = RequestMethod.POST)
    public JSONObject add(@RequestBody EmPlan record) {
        emPlanService.add(record);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 删除流程节点
     *
     * @param id 主键
     * @return
     * @author shaosen
     * @Date 2018年10月17日
     */
    @RequestMapping(value = "emplans/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable Integer id) {
        emPlanService.delete(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @author shaosen
     * @Date 2018年10月17日
     */
    @RequestMapping(value = "emplans/batch/{ids}", method = RequestMethod.DELETE)
    public JSONObject deleteBatch(@PathVariable String ids) {
        List<Integer> list = convertStringToList(ids);
        emPlanService.deleteBatch(list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 修改流程节点
     *
     * @param record 参考添加接口
     * @return
     * @author shaosen
     * @Date 2018年10月17日
     */
    @RequestMapping(value = "emplans", method = RequestMethod.PUT)
    public JSONObject update(@RequestBody EmPlan record) {
        emPlanService.update(record);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 通过主键获取流程节点
     *
     * @param id 主键
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"processKey":"FirePlanProcess","taskKey":"usertask1","taskName":"开启声光报警","targetKey":2,"targetName":"类型","targetValue":41,"actionKey":3,"actionName":"联动输出类型","actionValue":1,"finishKey":2,"finishName":"半自动","finishValue":1,"isFinished":false,"crtTime":1539014400000}}
     * @author shaosen
     * @Date 2018年10月17日
     */
    @RequestMapping(value = "emplans/{id}", method = RequestMethod.GET)
    public JSONObject getById(@PathVariable Integer id) {
        EmPlanDto dto = emPlanService.getById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }


    /**
     * 通过processKey获取流程节点列表
     *
     * @param processKey
     * @return {"msg":"请求成功","code":"200","data":["打开灯","打开风机","打开百叶"]}
     * @author shaosen
     * @Date 2018年11月12日
     */
    @RequestMapping(value = "emplans/process-key/{processKey}", method = RequestMethod.GET)
    public JSONObject getNodeListByProcessKey(@PathVariable String processKey) {
        List<JSONObject> list = emPlanService.getNodeListByProcessKeyAndSection(processKey, null);
        JSONObject returnData = new JSONObject();
        returnData.put("processName", ProcessTypeEnum.getEnum(processKey).getName());
        returnData.put("planStatus", list);
        return CommonUtil.success(returnData);

    }


    /**
     * 流程节点分页查询
     *
     * @param processKey 支持模糊查询
     * @param taskKey    usertask1
     * @param taskName   支持模糊查询
     * @param targetKey  TargetEnum枚举
     * @param actionKey  ActionEnum枚举
     * @param finishKey  FinishEnum枚举
     * @param isFinished 是否结束 0：否 ；1：是
     * @param startTime
     * @param endTime
     * @param pageNum    必须
     * @param pageSize   必须
     * @param
     * @param
     * @author shaosen
     * @Date 2018年10月17日
     */
    @RequestMapping(value = "emplans/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody EmPlanVo vo) {
        PageInfo<EmPlanDto> pageInfo = emPlanService.dataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }


    /**
     * 应急预案流程记录管理分页查询
     *
     * @param ProcessInstanceId 流程实例id（搜索条件）
     * @param processKey        流程定义key(必须)
     * @param startTime         时间戳
     * @param endTime           时间戳
     * @param pageNum           必须
     * @param pageSize          必须
     * @param
     * @return
     * @author shaosen
     * @Date 2018年8月10日
     */
    @RequestMapping(value = "plans/his-proc-inst/datagrid", method = RequestMethod.POST)
    public JSONObject getHisProInstDataGrid(@RequestBody PlanPageVo vo) {

        // 考虑到如果发布了新流程，processKey的后缀会发生改变，因此根据processDefinitionId模糊查询历史实例表（包含正在执行的实例）
        NativeHistoricProcessInstanceQuery query = activitiService.createHisProcInstDefIdNameLikeQuery(vo);

        // 开始分页
        PageBean<HistoricProcessInstance> page = new PageBean<>(vo.getPageNum(), vo.getPageSize(), query.list().size());
        page.setList(query.listPage(page.getStart(), vo.getPageSize()));

        LogUtil.info(" page : " + page);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, page);
    }

    /**
     * 获取流程详情
     *
     * @param iid 流程实例id
     * @return
     * @author shaosen
     * @Date 2018年8月10日
     */
    @RequestMapping(value = "plans/his-proc-inst/task/{iid}", method = RequestMethod.GET)
    public JSONObject getHisProcInstDet(@PathVariable String iid) {
        // 通过流程实例id查询历史活动表，获取每个节点详情
        List<HistoricActivityInstance> list = activitiService.getHisActInstListByProcessInstanceId(iid);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    
    
    //--------------------------------预案-组--------------------------------
    
    
    /**
     * 添加预案-组
     * @param planId
     * @param name
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-groups", method = RequestMethod.POST)
    public JSONObject add(@RequestBody EmPlanGroup e) {
    	emPlanGroupService.insertSelective(e);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 修改
     * @param id
     * @param planId
     * @param name
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-groups", method = RequestMethod.PUT)
    public JSONObject update(@RequestBody EmPlanGroup e) {
    	emPlanGroupService.updateByPrimaryKeySelective(e);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 通过id获取详情
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-groups/{id}", method = RequestMethod.GET)
    public JSONObject getDtoById(@PathVariable("id") Integer id) {
    	EmPlanGroupDto dto = emPlanGroupService.getById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }
    
    /**
     * 条件查询，不分页
     * @param id
     * @param planId
     * @param name 支持模糊查询
     * @param staffId
     * @param startTime
     * @param endTime
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-groups/condition", method = RequestMethod.POST)
    public JSONObject getDtoList(@RequestBody(required = false) EmPlanGroupVo vo) {
    	List<EmPlanGroupDto> list = emPlanGroupService.getByCondition(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 分页查询
     * @param id
     * @param planId
     * @param name 支持模糊查询
     * @param staffId
     * @param startTime
     * @param endTime
     * @param pageSize 必传
     * @param pageNum  必传
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-groups/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody EmPlanGroupVo vo) {
    	PageInfo<EmPlanGroupDto> list = emPlanGroupService.dataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 删除
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-groups/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteById(@PathVariable("id") Integer id) {
    	emPlanGroupService.deleteByPrimaryKey(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    
//--------------------------------预案-成员--------------------------------
    
    
    /**
     * 添加预案-成员
     * @param id
     * @param groupId
     * @param staffId
     * @param tel
     * @param tel2
     * @param role 成员所在角色：1为组长；其他为组员
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/{role}", method = RequestMethod.POST)
    public JSONObject addMember(@PathVariable("role") Integer role, @RequestBody EmPlanMember e) {
    	emPlanMemberService.insertSelective(e, role);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 修改
     * @param id
     * @param groupId
     * @param staffId
     * @param tel
     * @param tel2
     * @param role 成员所在角色：1为组长；其他为组员
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/{role}", method = RequestMethod.PUT)
    public JSONObject update(@PathVariable("role") Integer role, @RequestBody EmPlanMember e) {
    	emPlanMemberService.updateByPrimaryKeySelective(e, role);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 通过id获取详情
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/{id}", method = RequestMethod.GET)
    public JSONObject getDtosById(@PathVariable("id") Integer id) {
    	EmPlanMemberDto dto = emPlanMemberService.getById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }
    
    /**
     * 条件查询，不分页
     * @param id
     * @param groupId
     * @param tel 支持模糊查询
     * @param tel2 支持模糊查询
     * @param staffId
     * @param role 成员角色：1为组长，2为组员
     * @param planId 预案id
     * @param startTime
     * @param endTime
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/condition", method = RequestMethod.POST)
    public JSONObject getDtosList(@RequestBody(required = false) EmPlanMemberVo vo) {
    	List<EmPlanMemberDto> list = emPlanMemberService.getByCondition(vo);
    	if(vo.getPlanId() != null)
    		list = list.stream().filter(e -> vo.getPlanId().equals(e.getGroup().getPlanId())).collect(Collectors.toList());
        if(vo.getRole() != null) {
        	if(vo.getRole() == 1)
        		list = list.stream().filter(e -> e.getStaffId().equals(e.getGroup().getStaffId())).collect(Collectors.toList());
        	else
        		list = list.stream().filter(e -> !e.getStaffId().equals(e.getGroup().getStaffId())).collect(Collectors.toList());
        }
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 分页查询
     * @param id
     * @param groupId
     * @param tel 支持模糊查询
     * @param tel2 支持模糊查询
     * @param staffId
     * @param role 成员角色：1为组长，2为组员
     * @param planId 预案id
     * @param startTime
     * @param endTime
     * @param pageSize 必传
     * @param pageNum  必传
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/datagrid", method = RequestMethod.POST)
    public JSONObject memberDataGrid(@RequestBody EmPlanMemberVo vo) {
    	List<EmPlanMemberDto> list = emPlanMemberService.getByCondition(vo);
    	if(vo.getPlanId() != null)
    		list = list.stream().filter(e -> vo.getPlanId().equals(e.getGroup().getPlanId())).collect(Collectors.toList());
        if(vo.getRole() != null) {
        	if(vo.getRole() == 1)
        		list = list.stream().filter(e -> e.getStaffId().equals(e.getGroup().getStaffId())).collect(Collectors.toList());
        	else
        		list = list.stream().filter(e -> !e.getStaffId().equals(e.getGroup().getStaffId())).collect(Collectors.toList());
        }
        ListPageUtil<EmPlanMemberDto> page = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, page);
    }
    
    /**
     * 删除
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/{id}", method = RequestMethod.DELETE)
    public JSONObject deletesById(@PathVariable("id") Integer id) {
    	emPlanMemberService.deleteByPrimaryKey(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 判断某人是否已经加入到某组中
     * @param groupId
     * @param staffId
     * @return
     * @author ya.liu
     * @Date 2019年4月24日
     */
    @RequestMapping(value = "plan-members/checked", method = RequestMethod.POST)
    public JSONObject checked(@RequestBody EmPlanMemberVo vo) {
    	List<EmPlanMemberDto> list = emPlanMemberService.getByCondition(vo);
    	Boolean flag = true;
    	if(list != null && list.size() > 0) flag = false;
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, flag);
    }
    
    /**
     * 树形结构查看预案详情
     * @return
     * @author ya.liu
     * @Date 2019年4月25日
     */
    @RequestMapping(value = "plan-members/tree", method = RequestMethod.GET)
    public JSONObject tree() {
    	List<JSONObject> list = new ArrayList<>();
    	for(ProcessTypeEnum e : ProcessTypeEnum.getEmPlanList()) {
    		JSONObject obj = new JSONObject();
    		obj.put("name", e.getName());
    		List<JSONObject> children1 = new ArrayList<>();
    		EmPlanGroupVo vo1 = new EmPlanGroupVo();
    		vo1.setPlanId(e.getValue());
    		List<EmPlanGroupDto> groupList = emPlanGroupService.getByCondition(vo1);
    		for(EmPlanGroupDto dto : groupList) {
    			EmPlanMemberVo vo2 = new EmPlanMemberVo();
    			vo2.setGroupId(dto.getId());
    			List<EmPlanMemberDto> memberList = emPlanMemberService.getByCondition(vo2);
    			String leader = "-无组长";
    			JSONObject objGroup = new JSONObject();
    			List<JSONObject> children2 = new ArrayList<>();
    			for(EmPlanMemberDto dto2 : memberList) {
    				String tel = dto2.getTel() == null ? (dto2.getTel2() == null ? "无手机号" : dto2.getTel2()) : dto2.getTel();
    				if(dto2.getStaffId().equals(dto.getStaffId())) {
    					leader = "-" + dto.getStaff().getName() + "-" + tel;
    				} else {
    					JSONObject objMember = new JSONObject();
    					objMember.put("name", "组员-" + dto2.getStaff().getName() + "-" + tel);
    					objMember.put("value", dto2.getId());
    					children2.add(objMember);
    				}
    			}
    			
    			objGroup.put("name", dto.getName() + leader);
    			objGroup.put("children", children2);
    			children1.add(objGroup);
    		}
    		obj.put("children", children1);
    		list.add(obj);
    	}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
}
