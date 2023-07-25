package com.dbs.service;

import com.dbs.entity.Address;
import com.dbs.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DistrictServiceTests {
    @Autowired
    private DistrictService districtService;

    @Test
    public void testGetByParent() {
        List<District> list = districtService.getByParent("86");
        System.out.println(list);
    }
}
