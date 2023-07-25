package com.dbs.controller;

import com.dbs.entity.Address;
import com.dbs.service.AddressService;
import com.dbs.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{
    @Autowired
    private AddressService addressService;

    @PostMapping("/add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.getByUid(uid);
        return new JsonResult<>(OK, list);
    }

    @PostMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.setDefault(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @PostMapping("/{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.delete(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}
