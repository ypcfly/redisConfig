package com.ypc.learn.redis.service;

import com.ypc.learn.redis.entity.Book;

import java.util.List;
import java.util.Map;

public interface RedisService {

	/**
	 * 根据对象条件查询list
	 * @param book
	 * @return
	 */
	List<Book> getAllList(Book book);

	/**
	 * 插入新的对象
	 * @param book
	 * @return
	 */
	Map<String, Object> insertBook(Book book);

	/***
	 * 返回所有BOOK
	 * @return
	 */
	List<Book> queryAllBook();

	/**
	 * 根据autrhor查询
	 * @param author
	 * @return
	 */
	List<Book> queryByAuthorName(String author);
}
