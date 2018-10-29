package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.DefectDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Defect;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DefectMapper {

    Integer add(Defect defect);

    int update(Defect defect);

    /**
     * 根据工单获取缺陷
     * @param orderId
     * @return
     */
    Defect getDefectByOrder(@Param("orderId") int orderId);

    DefectDto getDefectDto(@Param("id") int id);

    /**
     * 获得管廊的缺陷
     *
     * @param tunnelId
     * @return
     */
    List<DefectDto> getDefectsOfTunnel(@Param("tunnelId") Integer tunnelId);

    /**
     * 根据条件获取缺陷
     * @param tunnelId
     * @param typeValue
     * @param objectId
     * @param levelValue
     * @param statusValue
     * @param startTime
     * @param endTime
     * @return
     */
    List<DefectDto> getDefectsByCondition(DefectVo vo);

    /**
     * 根据管廊和缺陷类型获得缺陷个数
     *
     * @param tunnelId
     * @param typeValue
     * @return
     */
    int getCountOfDefectByTunnelAndType(@Param("tunnelId") Integer tunnelId, @Param("typeValue") Integer typeValue);
    
    List<InspectionPlan> getDefectCountByTunnelId();
    
    List<DefectDto> getDefects(DefectVo defectVo);
    
}
