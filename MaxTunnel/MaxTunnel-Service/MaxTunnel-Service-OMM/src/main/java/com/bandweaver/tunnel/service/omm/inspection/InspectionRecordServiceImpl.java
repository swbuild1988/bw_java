package com.bandweaver.tunnel.service.omm.inspection;

import com.bandweaver.tunnel.common.biz.dto.omm.DefectDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionRecordDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.omm.DefectService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionRecordService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.omm.Defect;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionRecord;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.InspectionRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {

    @Autowired
    private InspectionRecordMapper inspectionRecordMapper;
    @Autowired
    private DefectService defectService;
    @Autowired
    private SectionService sectionService;


    /**
     * 添加
     *
     * @param inspectionRecord
     * @return
     */
    @Override
    public int add(InspectionRecord inspectionRecord) {
        int id = inspectionRecordMapper.add(inspectionRecord);
        return inspectionRecord.getId().intValue();
    }

    /**
     * 添加
     *
     * @param inspectionRecordDto
     * @return
     */
    @Override
    public int add(InspectionRecordDto inspectionRecordDto) {
        LogUtil.info("准备添加记录：" + inspectionRecordDto);
        // 如果有缺陷，先处理缺陷
        if (inspectionRecordDto.getHasDefect()) {
            LogUtil.info("有缺陷，添加缺陷:" + inspectionRecordDto.getDefect());
            DefectDto defect = inspectionRecordDto.getDefect();
            //获取管仓段id
            //Section section = sectionService.getSectionByStoreAndArea(defect.getStore().getId(), defect.getArea().getId());
            defect.setSectionId(inspectionRecordDto.getSectionId());
            
            int defectId = defectService.add(defect);
            LogUtil.info("缺陷ID：" + defectId);
            inspectionRecordDto.setDefectId(defectId);
        }
        LogUtil.info("添加记录：" + inspectionRecordDto);
        InspectionRecord inspectionRecord = (InspectionRecord)inspectionRecordDto;
        return add(inspectionRecord);
    }

    /**
     * 更新
     *
     * @param inspectionRecord
     * @return
     */
    @Override
    public int update(InspectionRecord inspectionRecord) {
        return inspectionRecordMapper.update(inspectionRecord);
    }

    /**
     * 根据id获得记录
     *
     * @param id
     * @return
     */
    @Override
    public InspectionRecord getInspectionRecord(int id) {
        return inspectionRecordMapper.get(id);
    }

    /**
     * 获得任务的所有记录
     *
     * @param taskId
     * @return
     */
    @Override
    public List<InspectionRecord> getInspectionRecords(int taskId) {
        return inspectionRecordMapper.getRecords(taskId);
    }
}
