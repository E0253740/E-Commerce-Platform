package com.dbs.mapper;

import com.dbs.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
// 表示标注当前类是一个测试类，不会随同项目一起打包发送
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试，只要满足以下四个方法，就可以单独运行，不用启动整个项目，提高了测试效率
     * 1. 必须被@Test注解修饰
     * 2. 返回值类型必须为void
     * 3. 方法的参数列表不指定任何类型
     * 4. 方法的访问修饰符必须是public
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("josh");
        user.setPassword("123");

        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void testFindByName(){
        User user = userMapper.findByUsername("josh");
        System.out.println(user);
    }

    @Test
    public void testUpdatePasswordByUid() {

        userMapper.updatePasswordByUid(12, "1234", "admin", new Date());
    }

    @Test
    public void testFindByUid() {
        User user = userMapper.findByUid(12);
        System.out.println(user);
    }

    @Test
    public void testUpdateInfoByUid() {
        User user = new User();
        user.setUid(12);
        user.setPhone("8888");
        user.setEmail("joshda@dbs.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void testUpdateAvatarByUid() {
        userMapper.updateAvatarByUid(13, "/upload/avatar.png", "admin", new Date());
    }
}
