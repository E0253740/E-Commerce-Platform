package com.dbs.controller;


import com.dbs.entity.District;
import com.dbs.service.DistrictService;
import com.dbs.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController{

    @Autowired
    private DistrictService districtService;

    @GetMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> list = districtService.getByParent(parent);
        return new JsonResult<>(OK, list);
    }

}
