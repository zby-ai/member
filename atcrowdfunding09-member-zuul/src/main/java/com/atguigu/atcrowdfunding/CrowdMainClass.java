package com.atguigu.atcrowdfunding;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zbystart
 * @create 2021-02-27 15:57
 */
@EnableEurekaClient
@SpringBootApplication
public class CrowdMainClass {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass.class,args);
    }
}
