package com.dbs.mapper;


import com.dbs.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口
 */

@Mapper
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user 用户数据
     * @return 受影响的行数 (增删改)
     */
    Integer insert(User user);

    /**
     * 根据用户uid修改用户密码
     * @param username 用户名
     * @return 如果找到相应用户则返回这个用户的数据，如果没有则返回null值
     */
    User findByUsername(String username);

    /**
     * 根据用户uid来修改密码
     * @param uid 用户id
     * @return 返回值为受影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id查询用户数据
     * @param uid 用户的id
     * @return 如果找到则返回对象，反之返回null值
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户的数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @Param 注解的作用: SQL映射文件中#{}占位符的变量名 ---> 解决的问题: 当SQL语句的占位符和映射的接口方法参数名不一致时，需要将某个参数强行注入到某个占位符变量上时，
     * 使用这个注解来标注映射关系
     * 根据uid修改用户头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
