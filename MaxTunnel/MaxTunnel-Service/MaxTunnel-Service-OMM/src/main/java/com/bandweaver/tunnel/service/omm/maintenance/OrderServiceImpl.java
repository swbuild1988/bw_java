package com.bandweaver.tunnel.service.omm.maintenance;

import com.bandweaver.tunnel.common.biz.dto.omm.OrderDto;
import com.bandweaver.tunnel.common.biz.itf.omm.OrderService;
import com.bandweaver.tunnel.common.biz.pojo.omm.Order;
import com.bandweaver.tunnel.dao.omm.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int add(Order order) {
        return orderMapper.add(order);
    }

    @Override
    public List<OrderDto> getOrderDtosByTunnel(Integer tunnelId) {
        return orderMapper.getOrderDtosByTunnel(tunnelId);
    }
}
