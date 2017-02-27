package com.dhc.dao;

import java.util.List;

import com.dhc.entity.Book;

public interface ITestDao {

	List<Book> findAllBooks();

	int getBooksCount();

	List<Book> findBooksByPageNumber(int pageNumber, int pageSize);

}
