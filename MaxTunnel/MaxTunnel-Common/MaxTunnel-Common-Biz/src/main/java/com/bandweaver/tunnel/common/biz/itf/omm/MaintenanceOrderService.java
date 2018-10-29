package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.MaintenanceOrderDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.MaintenanceOrder;
import com.bandweaver.tunnel.common.biz.vo.omm.MaintenanceOrderVo;

import java.util.List;

public interface MaintenanceOrderService {
    int add(int tunnelId, int defectId);

    int add(MaintenanceOrder maintenanceOrder);

    int update(MaintenanceOrder maintenanceOrder);

    MaintenanceOrderDto getOrder(Integer id);

    MaintenanceOrder getOrderByInstance(String instanceId);

    List<MaintenanceOrderDto> getMaintenanceOrderDtosByTunnel(Integer tunnelId);

    List<MaintenanceOrderDto> getMaintenanceOrderDtosByCondition(MaintenanceOrderVo vo);

    /**
     * 设置维修人员
     * @param id
     * @param personId
     */
    void setMaintenancePerson(int id, int personId);

    /**
     * 完结维修工单
     * @param id
     */
    void completeMaintenanceOrder(int id);

	void setMaintenancePersonAndRemark(Integer id, Integer personId, String remark);
}
