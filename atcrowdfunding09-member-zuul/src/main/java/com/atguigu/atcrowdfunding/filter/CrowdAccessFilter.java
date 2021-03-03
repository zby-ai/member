package com.atguigu.atcrowdfunding.filter;

import com.alibaba.fastjson.JSON;
import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.atguigu.atcrowdfunding.constant.AccessPassResources;
import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.po.Member;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author zbystart
 * @create 2021-03-02 10:19
 */
@Component
@Slf4j
public class CrowdAccessFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取请求路径
//        ServerHttpRequest request = exchange.getRequest();
//        RequestPath requestPath = request.getPath();
//        String path = requestPath.toString();
//        log.info("请求资源路径：" + path.toString());



//        log.info("从session获取的登录信息:" + member);
//        // 没有登陆,检查路径是否是可以放行的路径
//        if (member == null) {
//
//            // 获取请求对象
//            ServerHttpResponse response = exchange.getResponse();
//            Set<String> passSet =  AccessPassResources.PASS_RES_SET;
//
//            // 判断请求路径是否是允许放行的路径
//            if (!passSet.contains(path)) {
//
//                // 判断是否是静态资源
//                if ( AccessPassResources.judgeCurrentServletPathWetherStaticResource(path) ) {
//                    return chain.filter(exchange);
//                }
//
//                // 不是可以放行的资源，直接请求重定向
//                String url = requestPath.contextPath().toString() + "member/auth/to/login";
//                log.info("重定向的请求路径：" + url);
//                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
//                response.setStatusCode(HttpStatus.SEE_OTHER);
//                response.getHeaders().set(HttpHeaders.LOCATION, url);
//                return response.setComplete();
//            }
//            return chain.filter(exchange);
//        }
        // 登录直接放行
       return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

//    private Member getLoginMessage() {
//        String host = "http://www.atclowd.com";
//        String path = "/session/get/login/message";
//        String method = "get";
//
//        try {
//            HttpResponse httpResponse = HttpUtils.doGet(host, path, method,new HashMap<>(),null);
//            HttpEntity entity = httpResponse.getEntity();
//            InputStream content = entity.getContent();
//            ObjectInputStream objectInputStream = new ObjectInputStream(content);
//            ResultSetEntity<Member>  resultSetEntity = (ResultSetEntity<Member>) objectInputStream.readObject();
//            Member member = resultSetEntity.getData();
//            return member;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
