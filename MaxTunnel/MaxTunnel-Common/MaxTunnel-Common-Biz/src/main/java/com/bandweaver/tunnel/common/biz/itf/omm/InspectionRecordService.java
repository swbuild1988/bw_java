package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionRecordDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionRecord;

import java.util.List;

public interface InspectionRecordService {
    /**
     * 添加
     * @param inspectionRecord
     * @return
     */
    int add(InspectionRecord inspectionRecord);

    /**
     * 添加
     * @param inspectionRecordDto
     * @return
     */
    int add(InspectionRecordDto inspectionRecordDto);

    /**
     * 更新
     * @param inspectionRecord
     * @return
     */
    int update(InspectionRecord inspectionRecord);

    /**
     * 根据id获得记录
     * @param id
     * @return
     */
    InspectionRecord getInspectionRecord(int id);

    /**
     * 获得任务的所有记录
     * @param taskId
     * @return
     */
    List<InspectionRecord> getInspectionRecords(int taskId);
}
