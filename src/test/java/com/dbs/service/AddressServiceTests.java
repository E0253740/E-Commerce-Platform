package com.dbs.service;

import com.dbs.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressServiceTests {
    @Autowired
    private AddressService addressService;

    @Test
    public void testAddNewAddress() {
        Address address = new Address();
        address.setPhone("1333369999");
        address.setName("女朋友");
        addressService.addNewAddress(13, "admin", address);
    }

    @Test
    public void testSetDefault() {
        addressService.setDefault(3, 15, "admin");
    }

    @Test
    public void testDelete() {
        addressService.delete(4, 15, "admin");
    }
}
