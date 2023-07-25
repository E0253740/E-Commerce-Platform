package com.dbs.mapper;

import com.dbs.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void testInsert(){
        Address address = new Address();
        address.setUid(13);
        address.setPhone("133336");
        address.setName("女朋友");
        addressMapper.insert(address);
    }

    @Test
    public void testCountByUid() {
        int result = addressMapper.countByUid(13);
        System.out.println(result);
    }

    @Test
    public void testFindByUid() {
        List<Address> list = addressMapper.findByUid(15);
        System.out.println(list);
    }


    @Test
    public void testFindByAid() {
        Address address = addressMapper.findByAid(3);
        System.out.println(address);
    }

    @Test
    public void testUpdateNonDefault() {
        addressMapper.updateNonDefault(15);
    }

    @Test
    public void testUpdateDefaultByAid() {
        addressMapper.updateDefaultByAid(4, "admin", new Date());
    }

    @Test
    public void testDeleteByAid() {
        addressMapper.deleteByAid(1);
    }

    @Test
    public void testFindLastModified() {
        Address address = addressMapper.findLastModified(15);
        System.out.println(address);
    }
}
