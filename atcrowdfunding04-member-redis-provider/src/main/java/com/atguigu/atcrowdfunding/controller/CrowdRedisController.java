package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author zbystart
 * @create 2021-02-27 15:11
 */
@RestController
@RequestMapping("/member/redis")
public class CrowdRedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/add/key/and/value")
    public ResultSetEntity<String> addKeyAndValue(@RequestParam("key")String key,
                                                  @RequestParam("value")String value) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            return ResultSetEntity.succeedNoData();

        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }

    @PostMapping("/add/key/and/value/writh/timeout")
    public ResultSetEntity<String> addKeyAndValueWirthTimeout(@RequestParam("key")String key,
                                                             @RequestParam("value")String value,
                                                             @RequestParam("time")Long time,
                                                             @RequestParam("timeUnit")TimeUnit timeUnit
                                                             ) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value,time,timeUnit);
            return ResultSetEntity.succeedNoData();

        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }


    @PutMapping("/put/value/by/key")
    public ResultSetEntity<String> putValueByKey(@RequestParam("key")String key,
                                                 @RequestParam("value")String value) {
        try {

            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            return ResultSetEntity.succeedNoData();
        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }

    @GetMapping("/get/value/by/key")
    public ResultSetEntity<String> getValueByKey(@RequestParam("key")String key) {
        try {

            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String value = operations.get(key);
            return ResultSetEntity.succeedYesData("查询成功！",value);
        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }

    @DeleteMapping("/remove/value/by/key")
    public ResultSetEntity<String> removeValueByKey(@RequestParam("key")String key) {
        try {

            Boolean delete = redisTemplate.delete(key);
            if (delete) {
                return ResultSetEntity.succeedYesData("删除成功!!" ,key);
            }
            return ResultSetEntity.failureYesData("删除失败！");
        } catch (Exception e) {
            return ResultSetEntity.failureYesData(e.getMessage());
        }

    }
}
