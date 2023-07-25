package com.dbs.controller;


import com.dbs.controller.ex.*;
import com.dbs.entity.User;
import com.dbs.service.UserService;
import com.dbs.service.ex.InsertException;
import com.dbs.service.ex.UsernameDuplicatedException;
import com.dbs.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    /** 约定大于配置的开发思想 --> 省略了大量配置甚至注解的编写
     * 1. 接收数据方式: 请求处理方法的参数列表设置为pojo类型来接受前端的数据
     *  Springboot会将前端的url地址中的参数名和pojo类的属性名进行比较，
     *  如果这两个名称相同，则将值注入到pojo类中对应的属性上
     */

    @PostMapping("/reg")
    public JsonResult<Void> register(User user) {
        userService.register(user);
        return new JsonResult<>(OK);
    }

    /**
     * 2. 接收数据方式: 请求处理方法的参数列表为非pojo类型
     *  Springboot会直接将请求的参数名和方法的参数名进行比较，如果名称相同则完成值的自动依赖注入
     */
    @PostMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);

        // 将数据封装在Http Session中, session是全局的
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());

        return new JsonResult<User>(OK, user);
    }

    @PostMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);

        return new JsonResult<>(OK);
    }

    @GetMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User user = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,user);
    }

    @PostMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // User 对象有四部分数据 username, phone, email, gender
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }


    /**
     * 设置上传文件的最大值
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /** 限制上传文件的类型 */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/jpg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile 接口是由SpringMVC提供的，这个接口为我们包装了获取文件类型的数据(file) ---> 任何类型的file都可以接收
     * Springboot它有整合SpringMVC, 只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，Springboot会自动将文件中的数据赋值给这个参数
     * @param session
     * @param file
     * @return
     */
    @PostMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        } else if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        // 判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }

        String parent = session.getServletContext().getRealPath("upload");
        System.out.println(parent);
        File dir = new File(parent);
        if(!dir.exists()) {
            dir.mkdirs(); // 如果目录不存在，创建目录
        }

        // 获取到这个文件名称，使用UUID工具创建一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String fileType = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + fileType;

        File dest = new File(dir, filename); // 是一个空文件
        // 参数file数据写入到这个空文件中
        try {
            file.transferTo(dest); // 将file文件中数据写入到dest文件
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 返回头像路径 ---> /upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        // 返回用户头像的路径给前端页面，将来用于头像的展示
        return new JsonResult<>(OK, avatar);
    }

//    @PostMapping("/reg")
//    public JsonResult<Void> register(User user) {
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            userService.register(user);
//            result.setState(200);
//            result.setMessage("注册成功");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        } catch (InsertException e) {
//            result.setState(5000);
//            result.setMessage("注册时产生未知异常");
//        }
//
//        return result;
//    }


}
