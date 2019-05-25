package com.ypc.learn.redis.runner;

import com.ypc.learn.redis.constant.LanguageTypeEnum;
import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.mapper.BookMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ypcfly
 * @Date: 19-5-18 20:12
 * @Description:
 */
//@Component
public class RedisRunner implements ApplicationRunner {

    private static final String book_list_key_prefix = "book_list_key_prefix_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Book> bookList = bookMapper.queryAllList();
        if (CollectionUtils.isNotEmpty(bookList)) {
            for (LanguageTypeEnum typeEnum : LanguageTypeEnum.values()) {
                List<Book> subList = bookList.stream().filter(b -> typeEnum.getValue().equals(b.getLanguageType())).collect(Collectors.toList());
                redisTemplate.opsForValue().set(book_list_key_prefix + typeEnum.getName(),subList);
            }
        }

    }
}
