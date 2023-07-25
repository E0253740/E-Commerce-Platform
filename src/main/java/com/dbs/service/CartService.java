package com.dbs.service;


import com.dbs.entity.Cart;
import com.dbs.vo.CartVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartService {
    /**
     * 将商品添加到购物车
     * @param uid
     * @param pid
     * @param amount 新增商品数量
     * @param username 用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    List<CartVO> getVOByUid(Integer uid);

    /**
     * 更新用户购物车商品数量
     * @param cid
     * @param uid
     * @param username
     * @return 添加成功后，新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);


    List<CartVO> getVOByCid(Integer uid, Integer[] cids);
}
