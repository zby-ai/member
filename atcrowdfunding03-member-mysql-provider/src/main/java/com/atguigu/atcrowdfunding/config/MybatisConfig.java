package com.atguigu.atcrowdfunding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author zbystart
 * @create 2021-03-01 19:04
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

//    @Bean
//    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
//        return dataSourceTransactionManager;
//    }
}
