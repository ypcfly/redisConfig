package com.ypc.learn.redis.controller;

import com.google.gson.Gson;
import com.ypc.learn.redis.converter.DataConverter;
import com.ypc.learn.redis.dto.BookDTO;
import com.ypc.learn.redis.service.RedisService;
import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.service.StringRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-4-28 下午9:48
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private StringRedisService stringRedisService;

	@Autowired
	private DataConverter converter;

	/**
	 * 添加/修改对象
	 * @param bookDTO
	 * @return
	 */
	@PostMapping("/insert")
	public Map<String,Object> insertBook(@RequestBody BookDTO bookDTO) {
		LOGGER.info(">>>> request parameters are :{} <<<<",gson.toJson(bookDTO));

		Book book = converter.convert(bookDTO);

		return redisService.insertBook(book);
	}

	/**
	 * 根据条件查询列表
	 * @param bookDTO
	 * @return
	 */
	@PostMapping("/getList")
	public List<Book> getAllList(@RequestBody BookDTO bookDTO) {
		LOGGER.info(">>>> request parameters are :{} <<<<",gson.toJson(bookDTO));
		Book book = converter.convert(bookDTO);

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			LOGGER.error(">>>> error message:{} <<<<",e.getMessage());
//		}

		return redisService.getAllList(book);
	}

	/**
	 * 查询所有
	 * @return
	 */
	@PostMapping("/book/list")
	public List<Book> queryAll() {
		LOGGER.info(">>>> query all books from database <<<<");
		return redisService.queryAllBook();
	}

	/**
	 * 使用stringRedisTemplate保存
	 * @param bookDTO
	 * @return
	 */
	@PostMapping("/save")
	public Map<String,Object> save(@RequestBody BookDTO bookDTO) {
		LOGGER.info(">>>> request parameters are :{} <<<<",gson.toJson(bookDTO));

		Book book = converter.convert(bookDTO);

		return stringRedisService.saveBook(book);
	}

	/**
	 * 根据author查询，用于测试spring cache
	 * @param author
	 * @return
	 */
	@PostMapping("/query/{author}")
	public List<Book> queryByAuthor(@PathVariable("author") String author) {
		LOGGER.info(">>>> query books by author name, author name={} <<<<",author);

		return redisService.queryByAuthorName(author);
	}

}
