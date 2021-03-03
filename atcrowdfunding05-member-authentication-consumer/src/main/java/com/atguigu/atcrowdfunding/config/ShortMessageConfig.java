package com.atguigu.atcrowdfunding.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zbystart
 * @create 2021-03-01 20:26
 */
@Component
@ConfigurationProperties("short.message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortMessageConfig {
    private String host;
    private String path;
    private String method;
    private String appCode;
}
