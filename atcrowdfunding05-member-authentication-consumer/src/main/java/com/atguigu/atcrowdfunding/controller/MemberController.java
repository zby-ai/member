package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.api.MemberServiceRemote;
import com.atguigu.atcrowdfunding.api.RedisServiceRemote;
import com.atguigu.atcrowdfunding.config.ShortMessageConfig;
import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.entity.vo.MemberVO;
import com.atguigu.atcrowdfunding.utils.CrowdUtils;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zbystart
 * @create 2021-02-27 15:39
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Autowired
    private MemberServiceRemote memberServiceRemote;

    @Autowired
    private RedisServiceRemote redisServiceRemote;

    @Autowired
    private ShortMessageConfig shortMessageConfig;


//    @RequestMapping("/")
//    public String index() {
//        return "test";
//    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping("/auth/get/code")
    @ResponseBody
    public ResultSetEntity<String> getCode(@RequestParam("phone") String phone) {
        // 发送短信验证码
        ResultSetEntity<String> resultSetEntity = CrowdUtils.sendCodeByShortMessage(shortMessageConfig.getHost(), shortMessageConfig.getPath(), shortMessageConfig.getMethod(), phone, shortMessageConfig.getAppCode());
        if ("FAILURE".equals(resultSetEntity.getCode())) {
            return resultSetEntity;
        }
        // 将验证码保存到redis中
        String code = resultSetEntity.getData();
        String key = CrowdAdminConstant.TEMPLATE_CODE_PREFIX + phone;
        ResultSetEntity<String> redisResult = redisServiceRemote.addKeyAndValueWirthTimeout(key, code,5L, TimeUnit.MINUTES);
        if ("FAILURE".equals(redisResult.getCode())) {
            return redisResult;
        }
        return redisResult;
    }

    /**
     * 保存用户信息
     * @param memberVO
     * @return
     */
    @PostMapping("/auth/add/user")
    @ResponseBody
    public ResultSetEntity addUser(MemberVO memberVO) {
        String phone = memberVO.getPhone();
        String code = memberVO.getCode();
        String key = CrowdAdminConstant.TEMPLATE_CODE_PREFIX + phone;
        // 从redis中获取给用户发送的验证码
        ResultSetEntity<String> redisResult = redisServiceRemote.getValueByKey(key);
        if ("FAILURE".equals(redisResult.getCode())) {
            return redisResult;
        }
        String codeRedis = redisResult.getData();
        // 判断验证码是否一致
        if (codeRedis == null || codeRedis.length() == 0) {
            return ResultSetEntity.failureYesData("验证码已经过期,请重新获取验证码！");
        }

        if (!Objects.equals(code,codeRedis)) {
            return ResultSetEntity.failureYesData("验证码错误!");
        }

        // 验证码一致就删除在redis中的验证码
        ResultSetEntity<String> removeResult = redisServiceRemote.removeValueByKey(key);
        if ("FAILURE".equals(removeResult.getCode())) {
            return removeResult;
        }

        // 保存账户信息
        Member member = new Member();

        // 对密码进行加密
        String userpswdForm = memberVO.getUserpswd();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(userpswdForm);
        memberVO.setUserpswd(encode);

        // 将表单的数据复制到bean
        BeanUtils.copyProperties(memberVO,member);
        ResultSetEntity<String> memberResult = memberServiceRemote.addUser(member);
        if ("FAILURE".equals(memberResult.getCode())) {
            return memberResult;
        }
        return ResultSetEntity.succeedNoData();
    }

    /**
     * 查询登录账号是否可用
     * @param loginacct
     * @return
     */
    @GetMapping("/auth/check/loginacct")
    @ResponseBody
    public ResultSetEntity<String> checkLoginacct(@RequestParam("loginacct") String loginacct) {
        ResultSetEntity<Member> memberResult = memberServiceRemote.getUserByLoginacct(loginacct);
        if ("FAILURE".equals(memberResult.getCode())) {
            return ResultSetEntity.failureYesData(memberResult.getMssage());
        }
        Member member = memberResult.getData();
        if (member != null) {
            return ResultSetEntity.failureYesData("登录账号不可用");
        }
        return ResultSetEntity.succeedNoData();
    }

    /**
     * 检查登录账号
     * @param loginacct
     * @param userpswd
     * @return
     */
    @PostMapping("/auth/login/check")
    @ResponseBody
    public ResultSetEntity<String> checkLogin(@RequestParam("loginacct") String loginacct,
                                              @RequestParam("userpswd") String userpswd,
                                              HttpServletRequest request) {
        // 根据loginacct获取用户信息
        ResultSetEntity<Member> memberResult = memberServiceRemote.getUserByLoginacct(loginacct);
        if ("FAILURE".equals(memberResult.getCode())) {
            return ResultSetEntity.failureYesData(memberResult.getMssage());
        }
        // 判断loginacct是否存在于数据库
        Member member = memberResult.getData();
        if (member == null) {
            return ResultSetEntity.failureYesData("用户名或密码错误!");
        }
        // 判断密码是否正确
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if ( !bCryptPasswordEncoder.matches(userpswd, member.getUserpswd()) ) {
            return ResultSetEntity.failureYesData("用户名或密码错误!");
        }

        // 存入session域
        member.setUserpswd("");
        request.getSession().setAttribute(CrowdAdminConstant.ADMIN_USER_KEY,member);
        return ResultSetEntity.succeedNoData();
    }

    /**
     * 注销用户
     * @param session
     * @return
     */
    @GetMapping("/auth/logout")
    @ResponseBody
    public ResultSetEntity<String> logoutUser(HttpSession session) {
//        session.invalidate();
        try {
            session.removeAttribute(CrowdAdminConstant.ADMIN_USER_KEY);
            return ResultSetEntity.succeedNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }
}
