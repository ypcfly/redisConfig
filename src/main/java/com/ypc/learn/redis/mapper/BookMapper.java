package com.ypc.learn.redis.mapper;

import com.ypc.learn.redis.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {

	List<Book> queryByCondition(Book book);

	List<Book> queryList();

	int insertBook(Book book);

	List<Book> queryByAuthorName(@Param("author") String author);

    List<Book> queryAllList();

	int deleteById(Integer id);

	int updateById(Book book);
}
