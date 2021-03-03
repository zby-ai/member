package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.api.MemberServiceRemote;
import com.atguigu.atcrowdfunding.api.RedisServiceRemote;
import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-27 14:25
 */
@RestController
@RequestMapping("/member/authentication")
public class TestMemberController {

    @Autowired
    private MemberServiceRemote memberServiceRemote;

    @Autowired
    private RedisServiceRemote redisServiceRemote;

    @GetMapping("/get/memberList")
    public ResultSetEntity<List<Member>> getMemberList() {
        ResultSetEntity<List<Member>> memberList = memberServiceRemote.getMemberList();
        return memberList;
    }

    @GetMapping("/get/redis/value/by/key")
    public ResultSetEntity<String> getRedisValueByKey(@RequestParam("key") String key) {
        ResultSetEntity<String> valueByKey = redisServiceRemote.getValueByKey(key);
        return valueByKey;
    }

}
