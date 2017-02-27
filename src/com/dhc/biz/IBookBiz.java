package com.dhc.biz;

import java.util.List;

import com.commons.PageBean;
import com.dhc.entity.Book;
import com.dhc.entity.Category;

public interface IBookBiz {

	Book getBookByBid(Book book);

	List<Book> listBooksByCategory(Category cat, PageBean<Book> pb);

	int countBooksByCategory(Category cat);

	List<Book> listBooksByAuthor(Book book, PageBean<Book> pb);

	int countBooksByAuthor(Book book);

	List<Book> listBooksByPress(Book book, PageBean<Book> pb);

	int countBooksByPress(Book book);

	List<Book> listBooksByBnameFuzzy(Book book, PageBean<Book> pb);

	int countBooksByBnameFuzzy(Book book);

	List<Book> listBooksByConditionsFuzzy(Book book, PageBean<Book> pb);

	int listBooksByConditionsFuzzy(Book book);

}
