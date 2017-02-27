package com.dhc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.commons.PageBean;
import com.commons.StrKit;
import com.dbutils.TxQueryRunner;
import com.dhc.dao.IBookDao;
import com.dhc.entity.Book;
import com.dhc.entity.Category;

public class BookDaoImpl implements IBookDao {

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public Book getBookByBid(Book book) {
		try {
			book = qr.query("select * from t_book where bid=? ", new BeanHandler<>(Book.class),
					new Object[] { book.getBid() });
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
			int pn = pb.getPageNumber();
			int ps = pb.getPageSize();
			blist = qr.query("select * from t_book where cid=? limit ?,? ", new BeanListHandler<>(Book.class),
					new Object[] { cat.getCid(), (pn - 1) * ps, ps });
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
			Number num = (Number) qr.query("select count(*) from t_book where cid= ? ", new ScalarHandler<>(),
					new Object[] { cat.getCid() });
			i = num.intValue();
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
			int pn = pb.getPageNumber();
			int ps = pb.getPageSize();
			blist = qr.query("select * from t_book where author=? limit ?,? ", new BeanListHandler<>(Book.class),
					new Object[] { book.getAuthor(), (pn - 1) * ps, ps });
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
			Number num = (Number) qr.query("select count(*) from t_book where author= ? ", new ScalarHandler<>(),
					new Object[] { book.getAuthor() });
			i = num.intValue();
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
			int pn = pb.getPageNumber();
			int ps = pb.getPageSize();
			blist = qr.query("select * from t_book where press=? limit ?,? ", new BeanListHandler<>(Book.class),
					new Object[] { book.getPress(), (pn - 1) * ps, ps });
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
			Number num = (Number) qr.query("select count(*) from t_book where press= ? ", new ScalarHandler<>(),
					new Object[] { book.getPress() });
			i = num.intValue();
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
			int pn = pb.getPageNumber();
			int ps = pb.getPageSize();
			blist = qr.query("select * from t_book where bname like ? limit ?,? ", new BeanListHandler<>(Book.class),
					new Object[] { "%"+book.getBname()+"%", (pn - 1) * ps, ps });
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
			Number num = (Number) qr.query("select count(*) from t_book where bname like ? ", new ScalarHandler<>(),
					new Object[] { "%"+book.getBname()+"%" });
			i = num.intValue();
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
			int pn = pb.getPageNumber();
			int ps = pb.getPageSize();
			List<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer(" select * from t_book where 1=1 ");
			if(StrKit.notBlank(book.getBname())){
				sql.append(" and bname like ? ");
				params.add("%"+book.getBname()+"%");
			}
			if(StrKit.notBlank(book.getAuthor())){
				sql.append(" and author like ? ");
				params.add("%"+book.getAuthor()+"%");
			}
			if(StrKit.notBlank(book.getPress())){
				sql.append(" and press like ? ");
				params.add("%"+book.getPress()+"%");
			}
			sql.append(" limit ?,? ");
			params.add((pn - 1) * ps);
			params.add(ps);
			blist = qr.query(sql.toString(), new BeanListHandler<>(Book.class),params.toArray());
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
			List<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer(" select count(*) from t_book where 1=1 ");
			if(StrKit.notBlank(book.getBname())){
				sql.append(" and bname like ? ");
				params.add("%"+book.getBname()+"%");
			}
			if(StrKit.notBlank(book.getAuthor())){
				sql.append(" and author like ? ");
				params.add("%"+book.getAuthor()+"%");
			}
			if(StrKit.notBlank(book.getPress())){
				sql.append(" and press like ? ");
				params.add("%"+book.getPress()+"%");
			}
			Number num = (Number) qr.query(sql.toString(), new ScalarHandler<>(),params.toArray());
			i = num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

}
