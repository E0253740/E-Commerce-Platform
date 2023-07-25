package com.dbs.controller;

import com.dbs.entity.Order;
import com.dbs.service.OrderService;
import com.dbs.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.create(aid, uid, username, cids);
        return new JsonResult<>(OK, data);
    }
}
