package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.MaintenanceOrderDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.MaintenanceOrder;
import com.bandweaver.tunnel.common.biz.vo.omm.MaintenanceOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaintenanceOrderMapper {
    int add(MaintenanceOrder maintenanceOrder);

    int update(MaintenanceOrder maintenanceOrder);

    MaintenanceOrderDto getOrder(@Param("id") Integer id);

    MaintenanceOrder getOrderByInstance(@Param("processInstanceId") String processInstanceId);

    List<MaintenanceOrderDto> getMaintenanceOrderDtosByTunnel(@Param("tunnelId") Integer tunnelId);

    List<MaintenanceOrderDto> getMaintenanceOrderDtosByCondition(MaintenanceOrderVo vo);
}
