package com.ypc.learn.redis.service;

import com.ypc.learn.redis.entity.Book;

import java.util.Map;

/**
 * @Author: ypcfly
 * @Date: 19-5-18 20:25
 * @Description:
 */
public interface BookService {

    /**
     * 根据id删除
     * @param id
     * @return
     */
    String deleteById(Integer id);

    /**
     * 保存book
     * @param book
     * @return
     */
    Map<String, Object> saveBook(Book book);
}
