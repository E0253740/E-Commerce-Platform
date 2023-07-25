package com.dbs.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceTests {
    @Autowired
    private CartService cartService;

    @Test
    public void addToCart() {
        cartService.addToCart(15, 10000001, 5, "admin");
    }


}
