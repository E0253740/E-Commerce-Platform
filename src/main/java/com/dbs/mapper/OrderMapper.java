package com.dbs.mapper;

import com.dbs.entity.Order;
import com.dbs.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/** 订单的持久层接口 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项的数据
     * @param orderItem 订单项数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
