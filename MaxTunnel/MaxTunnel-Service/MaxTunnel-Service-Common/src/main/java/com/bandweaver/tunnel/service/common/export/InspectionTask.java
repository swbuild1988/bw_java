package com.bandweaver.tunnel.service.common.export;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.OfTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 巡检报表
 */
public class InspectionTask extends BaseTask implements Runnable {

    private String fileDirPath;

    private int type;

    public InspectionTask(String fileDirPath, int type) {
        this.fileDirPath = fileDirPath;
        this.type = type;
    }


    @Override
    public void run() {
        //获取当前周
        int currentWeekOrMonth = getCurrentTime(type);
        checkLastExport(currentWeekOrMonth, type, OfTypeEnum.INSPECTION_REPORT.getValue());
    }


    public void checkLastExport(int currentWeekOrMonth, int type, int ofType) {

        int lastWeekOrMonth = currentWeekOrMonth - 1;
        if (lastWeekOrMonth < 1) {
            return;
        }

        boolean exported = checkdbLog(lastWeekOrMonth, type, ofType);
        if (exported) {
            return;
        } else {
            //导出报表并继续检查上一周有没有导出过报表
            export(lastWeekOrMonth, type, ofType);
            checkLastExport(lastWeekOrMonth, type, ofType);
        }
    }



    public void export(int lastWeekOrMonth, int type, int ofType) {


        Date startTime = getStartTime(type, lastWeekOrMonth);
        Date endTime = getEndTime(type, lastWeekOrMonth);
        //get data
        JSONObject jsonObject = inspectionTaskService.getInspectionExcel(startTime, endTime);

        List<Item> data = new ArrayList<>();
        data.addAll(getTitleYear(startTime, endTime, 0, 10));//把标题日期加入data数组中

        Item item1 = new Item(jsonObject.getIntValue("task"), 1, 10);//本月巡检次数
        Item item2 = new Item(jsonObject.getIntValue("defect"), 2, 10);//本月发现缺陷数
        Item item3 = new Item(jsonObject.getIntValue("odd"), 3, 10);//上月剩余缺陷数
        Item item4 = new Item(jsonObject.getIntValue("order"), 4, 10);//本月维修数
        Item item5 = new Item(jsonObject.getIntValue("proportion"), 5, 10);//维修率
        Item item6 = new Item(jsonObject.getIntValue("not"), 6, 10);//尚未维修数

        data.add(item1);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        data.add(item5);
        data.add(item6);

        LogUtil.info(Thread.currentThread().getName() + ": data.size = " + data.size());
        doExport(data, getTemplateFilePath(fileDirPath, ofType), type, OfTypeEnum.getEnum(ofType), lastWeekOrMonth);

    }
}
