package com.dhc.biz.impl;

import java.util.List;

import com.commons.PageBean;
import com.dhc.biz.IBookBiz;
import com.dhc.dao.IBookDao;
import com.dhc.dao.impl.BookDaoImpl;
import com.dhc.entity.Book;
import com.dhc.entity.Category;

public class BookBizImpl implements IBookBiz {
	
	private IBookDao bdao = new BookDaoImpl();
	

	@Override
	public Book getBookByBid(Book book) {
		try {
			book = bdao.getBookByBid(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return book;
	}


	@Override
	public List<Book> listBooksByCategory(Category cat, PageBean<Book> pb) {
		List<Book> blist = null;
		try {
			blist = bdao.listBooksByCategory(cat,pb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}


	@Override
	public int countBooksByCategory(Category cat) {
		int i = 0;
		try {
			i = bdao.countBooksByCategory(cat);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}


	@Override
	public List<Book> listBooksByAuthor(Book book, PageBean<Book> pb) {
		List<Book> blist = null;
		try {
			blist = bdao.listBooksByAuthor(book,pb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}


	@Override
	public int countBooksByAuthor(Book book) {
		int i = 0;
		try {
			i = bdao.countBooksByAuthor(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}


	@Override
	public List<Book> listBooksByPress(Book book, PageBean<Book> pb) {
		List<Book> blist = null;
		try {
			blist = bdao.listBooksByPress(book,pb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}


	@Override
	public int countBooksByPress(Book book) {
		int i = 0;
		try {
			i = bdao.countBooksByPress(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}


	@Override
	public List<Book> listBooksByBnameFuzzy(Book book, PageBean<Book> pb) {
		List<Book> blist = null;
		try {
			blist = bdao.listBooksByBnameFuzzy(book,pb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}


	@Override
	public int countBooksByBnameFuzzy(Book book) {
		int i = 0;
		try {
			i = bdao.countBooksByBnameFuzzy(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}


	@Override
	public List<Book> listBooksByConditionsFuzzy(Book book, PageBean<Book> pb) {
		List<Book> blist = null;
		try {
			blist = bdao.listBooksByConditionsFuzzy(book,pb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}


	@Override
	public int listBooksByConditionsFuzzy(Book book) {
		int i = 0;
		try {
			i = bdao.listBooksByConditionsFuzzy(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

}
