package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.entity.po.Member;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-27 12:25
 */

public interface MemberService {

    void addMember(Member member);

    void putMemberById(Member member);

    void removeMemberById(Integer id);

    List<Member> getMemberList();

    Member getUserByLoginacct(String loginacct);
}
