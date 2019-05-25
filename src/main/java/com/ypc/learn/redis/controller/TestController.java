package com.ypc.learn.redis.controller;

import com.ypc.learn.redis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: ypcfly
 * @Date: 19-5-20 20:39
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/user/list")
    public Map<String,Object> getUserList() {

        return testService.getUserList();
    }

}
