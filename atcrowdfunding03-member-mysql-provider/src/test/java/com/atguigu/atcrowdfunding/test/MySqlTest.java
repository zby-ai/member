package com.atguigu.atcrowdfunding.test;

import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zbystart
 * @create 2021-02-27 12:06
 */

@SpringBootTest
public class MySqlTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMemberMapper() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123123");
        memberMapper.insert(new Member(null,"jack",encode,"杰克","jack@qq.com",1,2,"杰克","1111123322",0));
    }
}
