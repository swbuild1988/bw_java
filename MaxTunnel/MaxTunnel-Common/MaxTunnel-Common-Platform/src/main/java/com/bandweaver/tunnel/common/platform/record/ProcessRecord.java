package com.bandweaver.tunnel.common.platform.record;

import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 记录流程参数，用单例
 * 该类主要用作流程启动后，保存流程进行到的状态，帮助后续操作
 *
 */
public class ProcessRecord {
    private static ProcessRecord instance;

    private HashMap<String, EmPlan> emPlanHashMap = new HashMap<>();
    private HashMap<String, String> nextTaskValueHashMap = new HashMap<>();

    private ProcessRecord(){}

    public static synchronized ProcessRecord getProcessRecord(){
        if (instance == null) {
            instance = new ProcessRecord();
        }
        return instance;
    }

    public void setEmPlanHashMap(String instanceId, EmPlan emPlan){
        emPlanHashMap.put(instanceId, emPlan);
    }

    public EmPlan getEmPlan(String instanceId){
        return emPlanHashMap.get(instanceId);
    }

    public void removeEmPlanHashMap(String instanceId){
        emPlanHashMap.remove(instanceId);
    }

    public void setNextTaskValueHashMap(String instanceId, String nextTaskValue){
        nextTaskValueHashMap.put(instanceId, nextTaskValue);
    }

    public String getNextTaskValue(String instanceId){
        return nextTaskValueHashMap.get(instanceId);
    }

    public void removeNexTaskValueHashMap(String instanceId){
        nextTaskValueHashMap.remove(instanceId);
    }
}
