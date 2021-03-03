package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.mapper.MemberMapper;
import com.atguigu.atcrowdfunding.service.MemberService;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-27 12:26
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/get/memberList")
    public ResultSetEntity<List<Member>> getMemberList() {
        try {
            List<Member> memberList = memberService.getMemberList();
            return ResultSetEntity.succeedYesData("查询成功！",memberList);
        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }

    @PostMapping("/add/user")
    public ResultSetEntity<String> addUser(@RequestBody Member member) {
        try {
            memberService.addMember(member);
            return ResultSetEntity.succeedNoData();
        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }

    @GetMapping("/get/user/by/loginacct")
    public ResultSetEntity<Member> getUserByLoginacct(@RequestParam("loginacct") String loginacct) {
        try {
            Member member = memberService.getUserByLoginacct(loginacct);
            return ResultSetEntity.succeedYesData("查询成功",member);
        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }


}
