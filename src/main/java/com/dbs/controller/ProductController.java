package com.dbs.controller;


import com.dbs.entity.Product;
import com.dbs.service.ProductService;
import com.dbs.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{
    @Autowired
    private ProductService productService;

    @GetMapping("/hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.findHotList();
        return new JsonResult<>(OK, data);
    }

    @GetMapping("/{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult<>(OK,data);
    }
}
