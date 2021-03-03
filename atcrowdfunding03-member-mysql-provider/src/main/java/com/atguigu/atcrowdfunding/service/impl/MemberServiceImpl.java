package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.entity.po.MemberExample;
import com.atguigu.atcrowdfunding.mapper.MemberMapper;
import com.atguigu.atcrowdfunding.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-27 12:25
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class},readOnly = false)
    public void addMember(Member member) {
        memberMapper.insert(member);
    }

    @Override
    public void putMemberById(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }

    @Override
    public void removeMemberById(Integer id) {
        memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Member> getMemberList() {
        return memberMapper.selectByExample(new MemberExample());
    }

    @Override
    public Member getUserByLoginacct(String loginacct) {
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);

        List<Member> members = memberMapper.selectByExample(memberExample);

        if (members == null || members.size() == 0) {
            return null;
        }
        return members.get(0);
    }
}
