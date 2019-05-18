package com.ypc.learn.redis.controller;

import com.ypc.learn.redis.converter.DataConverter;
import com.ypc.learn.redis.dto.BookDTO;
import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: ypcfly
 * @Date: 19-5-18 20:11
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class RedisTestController {

    @Autowired
    private BookService bookService;

    @Autowired
    private DataConverter dataConverter;

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        return bookService.deleteById(id);
    }

    @PostMapping("/save/book")
    public Map<String,Object> saveBook(@RequestBody BookDTO bookDTO) {
        Book book = dataConverter.convert(bookDTO);
        return bookService.saveBook(book);
    }

}
