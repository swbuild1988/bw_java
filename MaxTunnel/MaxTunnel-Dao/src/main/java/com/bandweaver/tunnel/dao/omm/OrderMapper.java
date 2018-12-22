package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.OrderDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Order;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    int add(Order order);

    int update(Order order);

    List<OrderDto> getOrderDtosByTunnel(@Param("tunnelId") Integer tunnelId);

}
