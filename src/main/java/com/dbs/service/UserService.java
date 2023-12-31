package com.dbs.service;

import com.dbs.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块业务层接口
 */

public interface UserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void register(User user);

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(String username, String password);

    void changePassword(Integer uid, String username, String oldPassword, String newPassword);


    /**
     * 根据用户id查询用户信息
     * @param uid 用户id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据操作
     * @param uid 用户的id
     * @param username 用户的名称
     * @param user 用户对象的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户的头像
     * @param uid 用户id
     * @param avatar 用户头像
     * @param username 用户名称
     * @return
     */
    void changeAvatar(Integer uid, String avatar, String username);


}
