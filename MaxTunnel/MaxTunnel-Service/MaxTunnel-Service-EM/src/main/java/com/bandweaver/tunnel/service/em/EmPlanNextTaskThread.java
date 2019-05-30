package com.bandweaver.tunnel.service.em;

import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class EmPlanNextTaskThread extends Thread {

    private EmPlanService emPlanService;

    private String instanceId;
    private List<Section> sectionList;

    public EmPlanNextTaskThread(String _instanceId, List<Section> _sections) {
        this.instanceId = _instanceId;
        this.sectionList = _sections;
        this.emPlanService = SpringContextHolder.getBean("emPlanService");
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        LogUtil.info("线程中获得线程名：" + this.getName());
        if (emPlanService == null) LogUtil.info("emPlanService为空");
        emPlanService.nextTask(instanceId, sectionList);
    }
}
