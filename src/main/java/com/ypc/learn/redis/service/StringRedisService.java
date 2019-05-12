package com.ypc.learn.redis.service;

import com.ypc.learn.redis.entity.Book;

import java.util.List;
import java.util.Map;

public interface StringRedisService {
	/**
	 * 保存book
	 * @param book
	 * @return
	 */
	Map<String, Object> saveBook(Book book);

	/**
	 * stringRedis 测试方法
	 * @return
	 */
	List<Book> test();
}
