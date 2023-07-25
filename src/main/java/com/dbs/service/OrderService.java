package com.dbs.service;

import com.dbs.entity.Address;
import com.dbs.entity.Order;

public interface OrderService {
    Order create(Integer aid, Integer uid, String username, Integer[] cids);
}
