package com.ypc.learn.redis.service.impl;

import com.ypc.learn.redis.constant.LanguageTypeEnum;
import com.ypc.learn.redis.entity.Book;
import com.ypc.learn.redis.mapper.BookMapper;
import com.ypc.learn.redis.service.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: ypcfly
 * @Date: 19-5-18 20:27
 * @Description:
 */
@Service
public class BookServiceImpl implements BookService {

    private static final String book_list_key_prefix = "book_list_key_prefix_";

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String deleteById(Integer id) {

        int result = bookMapper.deleteById(id);
        //更新缓存
        List<Book> bookList = bookMapper.queryList();
        updateRedisCache(bookList);
        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveBook(Book book) {
        Map<String,Object> resultMap = new HashMap<>();
        Integer id = book.getId();
        int count = 0;
        if (id == null) {
            count = bookMapper.insertBook(book);
        } else {
            count = bookMapper.updateById(book);
        }

        if (count != 1) {
            throw new RuntimeException("insert to database failed");
        }
        //更新缓存
        List<Book> bookList = bookMapper.queryList();
        updateRedisCache(bookList);

        resultMap.put("success",true);
        resultMap.put("code",200);
        return resultMap;
    }


    private void updateRedisCache(List<Book> bookList) {
        LOGGER.info(">>>> update redis cache <<<<");
        if (CollectionUtils.isNotEmpty(bookList)) {
            for (LanguageTypeEnum typeEnum : LanguageTypeEnum.values()) {
                List<Book> subList = bookList.stream().filter(b -> typeEnum.getValue().equals(b.getLanguageType())).collect(Collectors.toList());
                redisTemplate.opsForValue().set(book_list_key_prefix + typeEnum.getName(),subList);
            }
        }
    }
}
