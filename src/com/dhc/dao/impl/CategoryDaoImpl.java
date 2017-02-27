package com.dhc.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dbutils.TxQueryRunner;
import com.dhc.dao.ICategoryDao;
import com.dhc.entity.Category;

public class CategoryDaoImpl implements ICategoryDao {
	
	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public List<Category> listCategories() {
		List<Category> clist = null;
		try {
			clist = qr.query("select * from t_category", new BeanListHandler<>(Category.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return clist;
	}

}
