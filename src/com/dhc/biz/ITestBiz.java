package com.dhc.biz;

import java.util.List;

import com.dhc.entity.Book;

public interface ITestBiz {

	List<Book> findAllBooks();

	int getBooksCount();

	List<Book> findBooksByPageNumber(int pageNumber, int pageSize);

}
