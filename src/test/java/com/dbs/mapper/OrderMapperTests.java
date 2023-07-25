package com.dbs.mapper;

import com.dbs.entity.Address;
import com.dbs.entity.Order;
import com.dbs.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(15);
        order.setRecvName("邢通");
        order.setRecvName("83585341");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(40);
        orderItem.setPid(10000004);
        orderItem.setTitle("得力（deli）1548A商务办公桌面计算器 太阳能双电源");
        orderMapper.insertOrderItem(orderItem);
    }
}
