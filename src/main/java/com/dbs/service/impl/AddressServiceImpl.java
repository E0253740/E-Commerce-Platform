package com.dbs.service.impl;

import com.dbs.entity.Address;
import com.dbs.mapper.AddressMapper;
import com.dbs.service.AddressService;
import com.dbs.service.DistrictService;
import com.dbs.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/** 新增收货地址的实现类 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    // 在添加用户收货地址的业务层依赖于DistrictService的业务层接口
    @Autowired
    private DistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {

        // 先统计该用户已有多少条地址数据
        Integer count = addressMapper.countByUid(uid);
        if(count >= maxCount) {
            throw new AddressExceedLimitException("用户收货地址已达上限");
        }

        // 将Address中数据进行补全: 省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //uid,isDefault
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;//1表示默认收货地址,0反之
        address.setIsDefault(isDefault);

        //补全四项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //调用插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户的收货地址时产生未知异常");
        }

    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for(Address address : list) {
            //address.setAid(null);
            address.setUid(null);
            //address.setProvinceName(null);
            address.setProvinceCode(null);
            //address.setCityName(null);
            address.setCityCode(null);
            //address.setAreaName(null);
            address.setAreaCode(null);
            address.setZip(null);
            //address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {

        Address result = addressMapper.findByAid(aid);
        if(result == null) throw new AddressNotFoundException("收货地址不存在");

        // 检测当前获取的收货地址数据归属
        if(!result.getUid().equals(uid)) throw new AccessDeniedException("非法数据访问");

        // 先将所有收获地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if(rows < 1) throw new UpdateException("更新数据时产生异常");

        // 将用户选中的某条地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if(rows != 1) throw new UpdateException("更新数据时产生异常");

    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result == null) throw new AddressNotFoundException("收货地址数据不存在");
        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        Integer rows = addressMapper.deleteByAid(aid);
        if(rows != 1) throw new DeleteException("删除地址信息时产生未知异常");

        Integer count = addressMapper.countByUid(uid);
        if(count == 0) {
            return;// 直接终止程序
        }

        if(result.getIsDefault() == 0) {
            return;
        }

        // 删除的数据为默认数据，则需要再另外设置一条为默认数据，这里我们选择最后更新的数据
        Address lastModifiedAddress = addressMapper.findLastModified(uid);
        rows = addressMapper.updateDefaultByAid(lastModifiedAddress.getAid(),  username, new Date());

        if(rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }

    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if(address == null) throw new AddressNotFoundException("收货地址数据不存在");
        if(!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }


}
