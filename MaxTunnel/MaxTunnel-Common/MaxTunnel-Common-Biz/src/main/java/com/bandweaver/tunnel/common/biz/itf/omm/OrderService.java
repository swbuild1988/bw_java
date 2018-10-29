package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.OrderDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    int add(Order order);

    List<OrderDto> getOrderDtosByTunnel(Integer tunnelId);

}
