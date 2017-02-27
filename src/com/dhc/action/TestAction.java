package com.dhc.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.PropKit;
import com.commons.StrKit;
import com.dhc.biz.ITestBiz;
import com.dhc.biz.impl.TestBizImpl;
import com.dhc.entity.Book;
import com.servlet.BaseServlet;

public class TestAction extends BaseServlet {
	
	private ITestBiz tbiz = new TestBizImpl();

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> blist = tbiz.findAllBooks();
		request.setAttribute("blist", blist);
		return "f:/test.jsp";
	}
	
	
	//需求：我需要拿出第一页的前12条记录
	
	//当前页的页码pageNumber 1
	//符合条件的所有记录list  前12条记录存放到List中 List<Book> size=12
	//符合条件的总记录数totalRecords 96
	//每页显示的记录条数pageSize 12
	//总页数 (96%12)?(96/12):(96/12+1) = 8
	
	
	public String paging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//第一步：得到当前页的页码（pageNumber）
		//如果前台没有传递当前页的页码，默认为第一页
		int pageNumber = 1;
		String pnString = request.getParameter("pageNumber");
		if(StrKit.notBlank(pnString)){
			pageNumber = Integer.parseInt(pnString);
		}
		//第二步：获取每页显示的记录条数（pageSize）
		int pageSize = PropKit.use("pagesize.properties").getInt("book_page_size");
		
		//第三步：查询出当前表中的记录条数totalRecords
		int totalRecords =  tbiz.getBooksCount();
		
		//第四步：把指定页码中的记录集合取出来
		// select bid,currPrice,author from t_book limit 0,12;
		// 0 开始显示的记录索引号  12每页显示的记录条数
		// (pageNumber-1)*pageSize====>0 
		// 第2页的显示的开始索引号是多少 （2-1）*12=12
		
		// 9 tr  2 ps   (9%2)?(9/2):(9/2+1) tp(9+2-1)/2
		// 显示第2页索引号是多少开始 
		// 第一页 0，1 第二页2，3 第三页4，5 第四页6，7 第五页8
		List<Book> blist = tbiz.findBooksByPageNumber(pageNumber,pageSize); //size =12
		// select * from t_book limit (pageNumber-1)*pageSize,pageSize
		
		// 第五步：计算出总页数
		int totalPages = (totalRecords+pageSize-1)/pageSize;
		
		// 第六步：将这些参数写到请求流中
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("blist", blist);
		
		// 第七步：转发到下一个页面
		return "f:/test.jsp";
		
		// pageNumber--pageSize--totalRecords(总记录条数)--totalPages(总页数)--List<Book>(要传入当前页的页码和每页显示的记录条数)limit 0,12
	}
	

}
