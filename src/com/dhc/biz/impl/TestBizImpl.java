package com.dhc.biz.impl;

import java.util.List;

import com.dhc.biz.ITestBiz;
import com.dhc.dao.ITestDao;
import com.dhc.dao.impl.TestDaoImpl;
import com.dhc.entity.Book;

public class TestBizImpl implements ITestBiz {
	
	private ITestDao tdao = new TestDaoImpl();

	@Override
	public List<Book> findAllBooks() {
		List<Book> blist = null;
		try {
			blist = tdao.findAllBooks();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}

	@Override
	public int getBooksCount() {
		int i = 0;
		try {
			i = tdao.getBooksCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public List<Book> findBooksByPageNumber(int pageNumber, int pageSize) {
		List<Book> blist = null;
		try {
			blist = tdao.findBooksByPageNumber(pageNumber,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}

}
