package com.dbs.service.impl;

import com.dbs.entity.Address;
import com.dbs.entity.Order;
import com.dbs.entity.OrderItem;
import com.dbs.mapper.OrderMapper;
import com.dbs.service.AddressService;
import com.dbs.service.CartService;
import com.dbs.service.OrderService;
import com.dbs.service.ex.InsertException;
import com.dbs.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;

    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids) {
        List<CartVO> list = cartService.getVOByCid(uid, cids);
        Date now = new Date();
        // 计算产品的总价
        Long totalPrice = 0l;
        for(CartVO c : list) {
            totalPrice += c.getRealPrice() * c.getNum();
        }

        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);
        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全数据：totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：status
        order.setStatus(0);
        // 补全数据：下单时间
        order.setOrderTime(now);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);
        order.setModifiedTime(now);
        // 插入数据
        Integer rows = orderMapper.insertOrder(order);
        if(rows != 1) throw new InsertException("插入异常");


        // 遍历carts，循环插入订单商品数据
        for (CartVO cart : list) {
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(now);
            item.setModifiedUser(username);
            item.setModifiedTime(now);
            // 插入订单商品数据
            Integer rows2 = orderMapper.insertOrderItem(item);
            if (rows2 != 1) {
                throw new InsertException("插入订单商品数据时出现未知错误，请联系系统管理员");
            }
        }
        return order;
    }


}
