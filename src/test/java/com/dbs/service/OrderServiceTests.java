package com.dbs.service;

import com.dbs.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        Integer[] cids = {27,28};
        Order order = orderService.create(6, 15, "joshxd", cids);
    }
}
