package com.dbs.service.impl;

import com.dbs.entity.Cart;
import com.dbs.entity.Product;
import com.dbs.mapper.CartMapper;
import com.dbs.mapper.ProductMapper;
import com.dbs.service.CartService;
import com.dbs.service.ex.AccessDeniedException;
import com.dbs.service.ex.CartNotFoundException;
import com.dbs.service.ex.InsertException;
import com.dbs.service.ex.UpdateException;
import com.dbs.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    /**
     * 依赖于购物车的持久层以及商品的持久层
     */
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        // 查询当前要添加的购物车是否已存在
        Cart cart = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if(cart == null) { //表示这个商品没有被添加到购物车中，进行新增操作
            Cart newCart = new Cart();
            // 补全数据
            newCart.setUid(uid);
            newCart.setPid(pid);
            newCart.setNum(amount); // 假设前端已经加好了，直接拿来set就行
            Product product = productMapper.findById(pid);
            newCart.setPrice(product.getPrice());

            newCart.setCreatedUser(username);
            newCart.setCreatedTime(date);
            newCart.setModifiedUser(username);
            newCart.setModifiedTime(date);

            Integer rows = cartMapper.insert(newCart);
            if(rows != 1) {
                throw new InsertException("插入数据时产生未知异常");
            }
        } else { // 当前商品在购物车中已经存在，则更新这条数据的num值
            Integer num = cart.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(cart.getCid(), num, username, date);
            if(rows != 1) {
                throw new UpdateException("更新数据时产生未知异常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result == null) throw new CartNotFoundException("购物车数据不存在");
        if (!result.getUid().equals(uid)) throw new AccessDeniedException("数据非法访问");

        int newNum = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, newNum, username, new Date());
        if(rows != 1) throw new UpdateException("更新数据失败");
        return newNum;
    }

    @Override
    public List<CartVO> getVOByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        Iterator<CartVO> it = list.iterator();
        while (it.hasNext()) {
            CartVO cartVO = it.next();
            if(!cartVO.getUid().equals(uid)) {
                list.remove(cartVO);
            }
        }
        return list;
    }
}
