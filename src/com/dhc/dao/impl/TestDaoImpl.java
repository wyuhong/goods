package com.dhc.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dbutils.TxQueryRunner;
import com.dhc.dao.ITestDao;
import com.dhc.entity.Book;

public class TestDaoImpl implements ITestDao {

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public List<Book> findAllBooks() {
		List<Book> blist = null;
		try {
			blist = qr.query("select * from t_book ", new BeanListHandler<>(Book.class));
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
			Number num = (Number) qr.query("select count(*) from t_book ", new ScalarHandler<>());
			i = num.intValue();
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
			blist = qr.query("select * from t_book limit ?,? ", new BeanListHandler<>(Book.class),
					new Object[] { (pageNumber - 1) * pageSize ,pageSize});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return blist;
	}

}
