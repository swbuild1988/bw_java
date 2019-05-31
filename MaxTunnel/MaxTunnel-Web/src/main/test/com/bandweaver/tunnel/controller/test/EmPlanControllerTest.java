package com.bandweaver.tunnel.controller.test;

import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.controller.quartz.TaskEntrance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "classpath:context/applicationContext.xml")
public class EmPlanControllerTest {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private EmPlanService emPlanService;

    @Test
    public void startPlan() {
        int sectionId = 1059;
        int processValue = 4003;

        List<Section> sectionList = sectionService.getSectionListByParentId(sectionId);
        String processInstanceId = emPlanService.start(sectionList, processValue);
        LogUtil.info(processInstanceId);
    }


    /**
     * 启动某section的processValue预案
     *
     * @param sectionId
     * @param processValue
     */
    private String startPlanBySectionId(int sectionId, int processValue) {
        // 查询仓以及仓关联的进气出气仓等
        List<Section> sectionList = sectionService.getSectionListByParentId(sectionId);
        String processInstanceId = emPlanService.start(sectionList, processValue);
        return processInstanceId;

        // 最后再查一次
//        EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute(Constants.EMPLAN_OBJ_KEY);
//        String processInstanceId = (String) ContextUtil.getSession().getAttribute(Constants.EMPLAN_PROCESSINSTANCE_ID);
//        List<MeasObj> measObjList = (List<MeasObj>) ContextUtil.getSession().getAttribute(Constants.EMPLAN_OBJ_LIST_KEY);
//        emPlanService.sendMsg(emPlan, processInstanceId, sectionList, measObjList)
    }

}