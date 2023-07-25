package com.dbs.service.impl;

import com.dbs.entity.User;
import com.dbs.mapper.UserMapper;
import com.dbs.service.UserService;
import com.dbs.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        String username = user.getUsername();
        // findByUsername 来检测用户是否被注册过
        User result = userMapper.findByUsername(username);
        if(result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // 密码加密处理实现，md5算法
        // 串 + password + 串 ---> 再由md5算法加密, 连续加密3次
        // 串 ---> salt
        String oldPassword = user.getPassword();
        // 随机生成salt
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 记录salt
        user.setSalt(salt);
        String newPassword = getMD5Password(oldPassword, salt);
        user.setPassword(newPassword);


        // 补全数据
        user.setIsDelete(0);
        user.setCreatedUser(username);
        user.setModifiedUser(username);
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 未被占用，可以继续执行注册业务逻辑
        Integer rows = userMapper.insert(user);
        if(rows != 1) {
            throw new InsertException("用户注册过程中产生异常");
        }

    }

    @Override
    public User login(String username, String password) {
        // 根据用户名称查询用户数据是否存在
        User result = userMapper.findByUsername(username);
        if(result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        /**
         * 检测用户的密码是否匹配
         * 1. 先获取到数据库中加密后的密码
         * 2. 再和用户传递过来的密码进行比较
         *  2.1 获取salt
         *  2.2 使用md5算法也加密三次
         */
        String salt = result.getSalt();
        String newMD5Password = getMD5Password(password, salt);

        if(!newMD5Password.equals(result.getPassword())) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        System.out.println(result);
        // 判断 isDelete 是否为1
        if(result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        // 返回当前用户数据，返回的数据是为了辅助其他页面做数据的展示用的(uid, username, avatar)
        // 我们只想返回这三个，其他的不需要，所以可以重新返回一个新对象
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return result;

    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        // 原始密码和数据库中密码进行比较
        String oldMD5Password = getMD5Password(oldPassword, result.getSalt());
        if(!result.getPassword().equals(oldMD5Password)) throw new PasswordNotMatchException("密码错误");
        // 将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMD5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新时产生异常");
        }

    }

    /**
     * 个人信息展示页面
     * @param uid 用户id
     * @return 返回给前端这些值作为pre populate来展示信息
     */
    @Override
    public User getByUid(Integer uid) {
        User result =  userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户信息不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        // 只需要返回这几个值用于前端展示

        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户信息不存在");
        }
        user.setUid(uid);
//        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if(rows != 1) {
            throw new UpdateException("更新数据时发生异常");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户信息不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新用户头像异常");
        }
    }

    /**
     * 定义一个md5算法加密方法
     */
    private String getMD5Password(String password, String salt) {
        // MD5加密算法的方法调用 ----> 3次加密
        for(int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
