package com.dbs.controller;


import com.dbs.service.CartService;
import com.dbs.utils.JsonResult;
import com.dbs.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;

    @PostMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession httpSession) {
        cartService.addToCart(getUidFromSession(httpSession),
                pid, amount, getUsernameFromSession(httpSession));

        return new JsonResult<>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> list = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<>(OK, list);
    }

    @PostMapping("/{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer newNum = cartService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, newNum);
    }

    @GetMapping("/list")
    public JsonResult<List<CartVO>> getVOByCid(Integer[] cids, HttpSession session) {
        List<CartVO> list = cartService.getVOByCid(getUidFromSession(session),cids);
        return new JsonResult<>(OK,list);
    }
}
