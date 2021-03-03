package com.atguigu.atcrowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zbystart
 * @create 2021-03-01 22:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phone;
    private String code;
}
