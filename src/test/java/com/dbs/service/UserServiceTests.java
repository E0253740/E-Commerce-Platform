package com.dbs.service;

import com.dbs.entity.User;
import com.dbs.mapper.UserMapper;
import com.dbs.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// 表示标注当前类是一个测试类，不会随同项目一起打包发送
public class UserServiceTests {

    @Autowired
    private UserService userService;

    /**
     * 单元测试，只要满足以下四个方法，就可以单独运行，不用启动整个项目，提高了测试效率
     * 1. 必须被@Test注解修饰
     * 2. 返回值类型必须为void
     * 3. 方法的参数列表不指定任何类型
     * 4. 方法的访问修饰符必须是public
     */

    @Test
    public void testRegister(){
        try {
            User user = new User();
            user.setUsername("xingda2");
            user.setPassword("123");

            userService.register(user);
            System.out.println("ok");
        } catch (ServiceException e) {

            // 获取异常的类对象和名称
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
        }
    }

    @Test
    public void testLogin(){
        User user = userService.login("xingda1", "123");
        System.out.println(user);
    }

    @Test
    public void testChangePassword(){
        userService.changePassword(13, "xingda2", "123", "1234");
    }

    @Test
    public void testGetByUid() {
        User user = userService.getByUid(12);
        System.out.println(user);
    }

    @Test
    public void testChangeInfo() {
        User user = new User();
        user.setPhone("87918470");
        user.setEmail("aaa.com");
        user.setGender(0);
        userService.changeInfo(13, "admin", user);
    }

    @Test
    public void testChangeAvatar() {
        userService.changeAvatar(13, "/upload/test.png", "admin");
    }
}
