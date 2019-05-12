package com.ypc.learn.redis.controller;

import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.service.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-5-9 下午9:15
 */
@RestController
@RequestMapping("/string/redis")
public class StringRedisController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private StringRedisService stringRedisService;

	@GetMapping("/test")
	public List<Book> test() {
		stringRedisTemplate.opsForValue().set("stringRedis_key","success");

		return stringRedisService.test();
	}

}
