package com.atguigu.atcrowdfunding.api;

import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author zbystart
 * @create 2021-02-27 15:24
 */
@Component
@FeignClient("atcrowd-member-redis-provider")
public interface RedisServiceRemote {
    @PostMapping("/member/redis/add/key/and/value")
    public ResultSetEntity<String> addKeyAndValue(@RequestParam("key")String key,
                                                  @RequestParam("value")String value);

    @PostMapping("/member/redis/add/key/and/value/writh/timeout")
    public ResultSetEntity<String> addKeyAndValueWirthTimeout(@RequestParam("key")String key,
                                                             @RequestParam("value")String value,
                                                             @RequestParam("time")Long time,
                                                             @RequestParam("timeUnit") TimeUnit timeUnit
    );

    @PutMapping("/member/redis/put/value/by/key")
    public ResultSetEntity<String> putValueByKey(@RequestParam("key")String key,
                                                 @RequestParam("value")String value);

    @GetMapping("/member/redis/get/value/by/key")
    public ResultSetEntity<String> getValueByKey(@RequestParam("key")String key);

    @DeleteMapping("/member/redis/remove/value/by/key")
    public ResultSetEntity<String> removeValueByKey(@RequestParam("key")String key);
}
