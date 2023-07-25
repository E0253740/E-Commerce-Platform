package com.dbs.service.impl;

import com.dbs.entity.District;
import com.dbs.mapper.DistrictMapper;
import com.dbs.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;


    @Override
    public List<District> getByParent(String parent) {
        List<District> list= districtMapper.findByParent(parent);
        /**
         * 在进行网络传输时，为了尽量避免无效数据传递，可以将无效数据设置为null
         * 好处是可以节省流量，提升效率
         */
        for(District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
