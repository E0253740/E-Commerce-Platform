package com.dbs.controller;

import com.dbs.controller.ex.*;
import com.dbs.service.ex.*;
import com.dbs.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层类的基类
 */
public class BaseController {
    /** 操作成功的状态码 */
    public static final int OK = 200;

    // 当项目中产生异常，会被统一拦截到此方法中
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);

        if(e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if(e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户密码错误的异常");
        } else if(e instanceof AddressExceedLimitException) {
            result.setState(4003);
            result.setMessage("用户收货地址超出上限的异常");
        } else if(e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户收货地址不存在的异常");
        } else if(e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("用户收货地址非法访问异常");
        } else if(e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("商品数据不存在的异常");
        } else if(e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车数据不存在的异常");
        } else if(e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册时产生未知异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生未知异常");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除数据时产生未知异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    /**
     * 获取httpsession对象中的uid
     * @param httpSession
     * @return 当前登陆的用户uid值
     */
    protected final Integer getUidFromSession(HttpSession httpSession) {
        return Integer.valueOf(httpSession.getAttribute("uid").toString());
    }


    /**
     * 获取httpsession对象中的username
     * @param httpSession
     * @return 当前登陆的用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession httpSession) {
        return httpSession.getAttribute("username").toString();
    }

}
