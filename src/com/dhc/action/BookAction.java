package com.dhc.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.BeanKit;
import com.commons.PageBean;
import com.commons.PropKit;
import com.dhc.biz.IBookBiz;
import com.dhc.biz.impl.BookBizImpl;
import com.dhc.entity.Book;
import com.dhc.entity.Category;
import com.servlet.BaseServlet;

public class BookAction extends BaseServlet {

	private IBookBiz bbiz = new BookBizImpl();

	public String listBooksByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//0 获取请求流中的数据封装到对象中
			Category cat = BeanKit.toBean(request.getParameterMap(), Category.class);
			PageBean<Book> pb = new PageBean<Book>();
			//第一步：设置pageNumber
			pb.setPageNumber(super.getPageNumber(request));
			//第二步：设置pageSize
			pb.setPageSize(PropKit.use("pagesize.properties").getInt("book_page_size"));
			//第三步：设置url
			pb.setUrl(super.getUrl(request, null));
			//第四步：设置list(list是指定页码显示内容的集合)
			pb.setList(bbiz.listBooksByCategory(cat,pb));
			//第五步：设置totalRecords
			pb.setTotalRecords(bbiz.countBooksByCategory(cat));
			//第六步：将pb存放到request中
			//为何此处没有计算总页数？因为在封装的PageBean中已经计算出总页数了
			request.setAttribute("pb", pb);
			//第六步：转发到下一个页面
			return "f:/jsps/book/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public String getBookByBid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//将请求流中的bid封装到book对象中
			Book book = BeanKit.toBean(request.getParameterMap(), Book.class);
			book = bbiz.getBookByBid(book);
			request.setAttribute("book", book);
			return "f:/jsps/book/desc.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public String listBooksByAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//0 将请求流中的bid封装到book对象中
			Book book = BeanKit.toBean(request.getParameterMap(), Book.class);
			PageBean<Book> pb = new PageBean<Book>();
			//第一步：pageNumber
			pb.setPageNumber(super.getPageNumber(request));
			//第二步：pageSize
			pb.setPageSize(PropKit.use("pagesize.properties").getInt("book_page_size"));
			//第三步：设置url
			pb.setUrl(super.getUrl(request, null));
			//第四步：设置list
			pb.setList(bbiz.listBooksByAuthor(book,pb));
			//第五步：设置totalRecords
			pb.setTotalRecords(bbiz.countBooksByAuthor(book));
			//第六步：将pb存放到request中
			//为何此处没有计算总页数？因为在封装的PageBean中已经计算出总页数了
			request.setAttribute("pb", pb);
			//第六步：转发到下一个页面
			return "f:/jsps/book/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public String listBooksByPress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//0 将请求流中的bid封装到book对象中
			Book book = BeanKit.toBean(request.getParameterMap(), Book.class);
			PageBean<Book> pb = new PageBean<Book>();
			//第一步：pageNumber
			pb.setPageNumber(super.getPageNumber(request));
			//第二步：pageSize
			pb.setPageSize(PropKit.use("pagesize.properties").getInt("book_page_size"));
			//第三步：设置url
			pb.setUrl(super.getUrl(request, null));
			//第四步：设置list
			pb.setList(bbiz.listBooksByPress(book,pb));
			//第五步：设置totalRecords
			pb.setTotalRecords(bbiz.countBooksByPress(book));
			//第六步：将pb存放到request中
			//为何此处没有计算总页数？因为在封装的PageBean中已经计算出总页数了
			request.setAttribute("pb", pb);
			//第六步：转发到下一个页面
			return "f:/jsps/book/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public String listBooksByBnameFuzzy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//0 将请求流中的bid封装到book对象中
			Book book = BeanKit.toBean(request.getParameterMap(), Book.class);
			PageBean<Book> pb = new PageBean<Book>();
			//第一步：pageNumber
			pb.setPageNumber(super.getPageNumber(request));
			//第二步：pageSize
			pb.setPageSize(PropKit.use("pagesize.properties").getInt("book_page_size"));
			//第三步：设置url
			pb.setUrl(super.getUrl(request, null));
			//第四步：设置list
			pb.setList(bbiz.listBooksByBnameFuzzy(book,pb));
			//第五步：设置totalRecords
			pb.setTotalRecords(bbiz.countBooksByBnameFuzzy(book));
			//第六步：将pb存放到request中
			//为何此处没有计算总页数？因为在封装的PageBean中已经计算出总页数了
			request.setAttribute("pb", pb);
			//第六步：转发到下一个页面
			return "f:/jsps/book/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public String listBooksByConditionsFuzzy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//0 将请求流中的bid封装到book对象中
			Book book = BeanKit.toBean(request.getParameterMap(), Book.class);
			PageBean<Book> pb = new PageBean<Book>();
			//第一步：pageNumber
			pb.setPageNumber(super.getPageNumber(request));
			//第二步：pageSize
			pb.setPageSize(PropKit.use("pagesize.properties").getInt("book_page_size"));
			//第三步：设置url
			pb.setUrl(super.getUrl(request, null));
			//第四步：设置list
			pb.setList(bbiz.listBooksByConditionsFuzzy(book,pb));
			//第五步：设置totalRecords
			pb.setTotalRecords(bbiz.listBooksByConditionsFuzzy(book));
			//第六步：将pb存放到request中
			//为何此处没有计算总页数？因为在封装的PageBean中已经计算出总页数了
			request.setAttribute("pb", pb);
			//第六步：转发到下一个页面
			return "f:/jsps/book/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	

}
