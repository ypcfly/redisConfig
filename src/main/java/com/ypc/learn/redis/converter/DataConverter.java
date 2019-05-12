package com.ypc.learn.redis.converter;

import com.ypc.learn.redis.dto.BookDTO;
import com.ypc.learn.redis.entity.Book;

public interface DataConverter {

	Book convert(BookDTO bookDTO);
}
