package com.ypc.learn.redis.service.impl;

import com.google.gson.Gson;
import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.mapper.BookMapper;
import com.ypc.learn.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-4-28 下午10:05
 */
@Service
public class RedisServiceImpl implements RedisService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private BookMapper bookMapper;

	/**
	 * 存放book list的key
	 */
	private static final String BOOK_LIST_KEY = "book_list_key";

	/**
	 *  存放每位作者书的hash key的前缀，即hash key的值为前缀 + author
	 */
	private static final String BOOK_AUTHOR_KEY_PREFIX = "book_list_key_prefix_";

	/**
	 *  使用spring cache时redis中缓存的名称
	 */
	private static final String REDIS_CACHE_VALUE = "redis_cache_value";

	@Override
	public List<Book> getAllList(Book book) {
		// redisTemplate
//		List<Book> bookList = (List<Book>) redisTemplate.opsForHash().get(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor());
//		if (CollectionUtils.isNotEmpty(bookList)) {
//			return bookList;
//		}

//		// 使用StringRedisTemplate
//		List<Book> bookList = (List<Book>) stringRedisTemplate.opsForHash().get(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor());
//		if (CollectionUtils.isNotEmpty(bookList)) {
//			System.out.println(new Gson().toJson(bookList));
//			return bookList;
//		}

		List<Book> bookList = bookMapper.queryByCondition(book);

//		if (CollectionUtils.isNotEmpty(bookList)) {
//			redisTemplate.opsForHash().put(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor(),bookList);
//			// 使用StringRedisTemplate
//			stringRedisTemplate.opsForHash().put(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor(),bookList);
//		}
		return bookList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertBook(Book book) {
		Map<String,Object> resultMap = new HashMap<>();

		int insert = bookMapper.insertBook(book);
		if (insert != 1) {
			throw new RuntimeException("insert into database failed, insert count=" + insert);
		}
		// 更新缓存
//		List<Book> bookList = (List<Book>) redisTemplate.opsForHash().get(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor());
		List<Book> bookList = (List<Book>) stringRedisTemplate.opsForHash().get(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor());
		if (CollectionUtils.isEmpty(bookList)) {
			bookList = new ArrayList<>();
		}
		bookList.add(book);

//		redisTemplate.opsForHash().put(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor(),bookList);
//		stringRedisTemplate.opsForHash().put(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + book.getAuthor(),bookList);

		resultMap.put("success",true);
		resultMap.put("code",200);

		return resultMap;
	}

	@Override
	public List<Book> queryAllBook() {
		Map<Object,Object> resultMap = redisTemplate.opsForHash().entries(BOOK_LIST_KEY);

		for (Map.Entry entry : resultMap.entrySet()) {
			String key = (String) entry.getKey();
			List<Book> value = (List<Book>) entry.getValue();
			LOGGER.info("query entry set from redis key:{} value:{}",key,value);
		}

		List<Book> bookList = bookMapper.queryList();
		// author作为hash key存放到redis
		Map<String,List<Book>> map = bookList.stream().collect(Collectors.groupingBy(Book::getAuthor));
		for (Map.Entry entry : map.entrySet()) {
			String author = (String) entry.getKey();
			List<Book> books = (List<Book>) entry.getValue();
			LOGGER.info(">>>> split result list to sub list, key-->author name:{}, value-->books:{} <<<<",author,books);
			stringRedisTemplate.opsForHash().put(BOOK_LIST_KEY,BOOK_AUTHOR_KEY_PREFIX + entry.getKey(),entry.getValue());
		}

		return bookList;
	}


	@Cacheable(value = REDIS_CACHE_VALUE,key = "'" + BOOK_AUTHOR_KEY_PREFIX + "' + #author")
	@Override
	public List<Book> queryByAuthorName(String author) {
		LOGGER.info(">>>> query by author name, author name={} <<<<",author);
		List<Book> bookList = bookMapper.queryByAuthorName(author);
		return bookList;
	}
}
