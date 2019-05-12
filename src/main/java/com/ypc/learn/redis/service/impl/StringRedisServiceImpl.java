package com.ypc.learn.redis.service.impl;

import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.service.StringRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-5-4 下午8:09
 */
@Service
public class StringRedisServiceImpl implements StringRedisService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringRedisServiceImpl.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	@Override
	public Map<String, Object> saveBook(Book book) {
		return null;
	}

	@Override
	public List<Book> test() {

		return null;
	}
}
