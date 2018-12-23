package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectionRecordMapper {
    int add(InspectionRecord inspectionRecord);

    int update(InspectionRecord inspectionRecord);

    InspectionRecord get(@Param("id") int id);

    List<InspectionRecord> getRecords(@Param("taskId") int taskId);
}
