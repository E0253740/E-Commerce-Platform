package com.dbs.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，如果没有则重定向到登陆页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @return 返回值为true表示放行当前请求，返回值为false表示拦截当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if(obj == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        // 请求放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
