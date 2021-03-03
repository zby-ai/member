package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.po.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zbystart
 * @create 2021-03-02 16:03
 */
@Component
@Slf4j
public class CrowdMemberInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        log.info("拦截的路径" + request.getRequestURI());
        Member member = (Member) session.getAttribute(CrowdAdminConstant.ADMIN_USER_KEY);
        if (member == null) {
            request.setAttribute(CrowdAdminConstant.ADMIN_LOGIN_MASSAGE,"请先登录");
            request.getRequestDispatcher("/member/auth/to/login").forward(request,response);
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
