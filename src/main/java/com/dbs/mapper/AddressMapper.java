package com.dbs.mapper;

import com.dbs.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/** 收货地址持久层接口 */
@Mapper
public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     * @param address 收获地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计地址数量
     * @param uid
     * @return 当前用户收获地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查询用户收货地址
     * @param uid 用户uid
     * @return 用户收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     * @param aid 收获地址id
     * @return 收货地址数据，若没有找到则返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户id设置该用户所有收货地址为非默认
     * @param uid 用户id
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);


    Integer updateDefaultByAid(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货地址id删除收货地址数据
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户id查询当前用户最后一次被修改的收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    Address findLastModified(Integer uid);
}
