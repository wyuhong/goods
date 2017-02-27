package com.dhc.biz.impl;

import java.util.List;

import com.dhc.biz.ICategoryBiz;
import com.dhc.dao.ICategoryDao;
import com.dhc.dao.impl.CategoryDaoImpl;
import com.dhc.entity.Category;

public class CategoryBizImpl implements ICategoryBiz {

	private ICategoryDao cdao = new CategoryDaoImpl();
	
	@Override
	public List<Category> listCategories() {
		List<Category> clist = null;
		try {
			clist = cdao.listCategories();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return clist;
	}

}
