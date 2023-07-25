package com.dbs.mapper;

import com.dbs.entity.Cart;
import com.dbs.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
// 表示标注当前类是一个测试类，不会随同项目一起打包发送
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(15);
        cart.setPid(10000001);
        cart.setNum(2);
        cart.setPrice(1000l);
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {
        cartMapper.updateNumByCid(27, 4, "admin", new Date());
    }

    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(15, 10000001);
        System.out.println(cart);
    }

    @Test
    public void findVOByUid() {
        System.out.println(cartMapper.findVOByUid(15));
    }

    @Test
    public void findByCid() {
        System.out.println(cartMapper.findByCid(27));
    }

    @Test
    public void findVOByCids() {
        Integer[] cids = {27,28};
        System.out.println(cartMapper.findVOByCids(cids));
    }
}
