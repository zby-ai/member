package com.atguigu.atcrowdfunding.config;

import com.atguigu.atcrowdfunding.interceptor.CrowdMemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zbystart
 * @create 2021-03-01 19:26
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/member/auth/to/reg/page").setViewName("reg");
        registry.addViewController("/member/auth/to/login").setViewName("login");
        registry.addViewController("/member/auth/to/home/page").setViewName("member");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CrowdMemberInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/member/auth/to/login",
                        "/",
                        "/member/auth/to/reg/page",
                        "/member/auth/logout",
                        "/member/auth/get/code",
                        "/member/auth/check/loginacct",
                        "/member/auth/add/user",
                        "/member/auth/login/check",
                        "/bootstrap/**",
                        "/css/**",
                        "/fonts/**",
                        "/img/**",
                        "/jquery/**",
                        "/layer/**",
                        "/script/**",
                        "/ztree/**");
    }
}
