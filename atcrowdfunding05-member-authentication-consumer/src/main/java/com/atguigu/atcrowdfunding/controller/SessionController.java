package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.api.RedisServiceRemote;
import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author zbystart
 * @create 2021-03-02 13:54
 */
@RestController
public class SessionController {


    @GetMapping("/session/get/login/message")
    public ResultSetEntity<Member> getloginMemberMessage(HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute(CrowdAdminConstant.ADMIN_USER_KEY);
        return ResultSetEntity.succeedYesData("查询成功！",member);
    }
}
