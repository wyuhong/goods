package com.dhc.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dhc.biz.ICategoryBiz;
import com.dhc.biz.impl.CategoryBizImpl;
import com.dhc.entity.Category;
import com.servlet.BaseServlet;

public class CategoryAction extends BaseServlet {

	private ICategoryBiz cbiz = new CategoryBizImpl();
	
	public String listCategories(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Category> catlist = new ArrayList<Category>();
		List<Category> clist = cbiz.listCategories();
		for (Category cat : clist) {
			if(cat.getPid() != null){
				cat.setUrl("/goods/bookAction?flag=listBooksByCategory&cid="+cat.getCid());
				cat.setTarget("body");
			}
			catlist.add(cat);
		}
		String catliststring = JSON.toJSONString(catlist);
		PrintWriter out = response.getWriter();
		out.print(catliststring);
		out.flush();
		out.close();
		return null;
	}

}
