package com.ypc.learn.redis.entity;

import java.io.Serializable;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-4-28 下午9:59
 */
public class Book implements Serializable {

	private Integer id;

	private String title;

	private Integer pageNum;

	private Double price;

	private String author;

	private Integer languageType;

	public Integer getLanguageType() {
		return languageType;
	}

	public void setLanguageType(Integer languageType) {
		this.languageType = languageType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
