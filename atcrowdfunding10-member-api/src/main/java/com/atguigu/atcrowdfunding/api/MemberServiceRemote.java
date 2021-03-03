package com.atguigu.atcrowdfunding.api;

import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-27 12:39
 */
@Component
@FeignClient("atcrowd-member-mysql-provider")
public interface MemberServiceRemote {
    @GetMapping("/member/get/memberList")
    public ResultSetEntity<List<Member>> getMemberList();

    @GetMapping("/member/get/user/by/loginacct")
    public ResultSetEntity<Member> getUserByLoginacct(@RequestParam("loginacct") String loginacct);

    @PostMapping("/member/add/user")
    public ResultSetEntity<String> addUser(@RequestBody Member member);

}
