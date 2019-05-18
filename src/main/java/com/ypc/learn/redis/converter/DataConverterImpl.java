package com.ypc.learn.redis.converter;

import com.ypc.learn.redis.dto.BookDTO;
import com.ypc.learn.redis.entity.Book;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-4-30 下午9:17
 */
@Component
public class DataConverterImpl implements DataConverter {

	@Override
	public Book convert(BookDTO bookDTO) {
		Book book = new Book();
		book.setId(bookDTO.getId());
		book.setTitle(bookDTO.getTitle());
		book.setPrice(bookDTO.getPrice());
		book.setAuthor(bookDTO.getAuthor());
		book.setPageNum(bookDTO.getPageNum());
		book.setLanguageType(bookDTO.getLanguageType());
		return book;
	}
}
