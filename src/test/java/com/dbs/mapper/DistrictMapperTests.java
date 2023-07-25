package com.dbs.mapper;

import com.dbs.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DistrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;
    @Test
    public void testFindByParent() {
        List<District> list = districtMapper.findByParent("120200");
        System.out.println(list);
    }

    @Test
    public void findNameByCode() {
        String name = districtMapper.findNameByCode("610000");
        System.out.println(name);
    }


}
