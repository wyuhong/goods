package com.dhc.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.BeanKit;
import com.dhc.biz.ICartItemBiz;
import com.dhc.biz.impl.CartItemBizImpl;
import com.dhc.entity.Book;
import com.dhc.entity.CartItem;
import com.dhc.entity.User;
import com.servlet.BaseServlet;

public class CartItemAction extends BaseServlet {

	private ICartItemBiz cbiz = new CartItemBizImpl();
	
	public String mycart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("sessionUser");
		List<CartItem> clist = cbiz.listCartItemsByUser(user);
		request.setAttribute("clist", clist);
		return "f:/jsps/cart/list.jsp";
	}
	
	public String saveCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求流中的所有数据封装到对象中
		CartItem cart = BeanKit.toBean(request.getParameterMap(), CartItem.class);
		Book book = BeanKit.toBean(request.getParameterMap(), Book.class);
		cart.setBook(book);
		cart.setUser((User)request.getSession().getAttribute("sessionUser"));
		//调用业务层方法将购物车条目添加到表中
		cbiz.saveCartItem(cart);
		return "r:/cartItemAction?flag=mycart";
	}
	
	public String removeCartItemByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求流中的所有数据封装到对象中
		CartItem cart = BeanKit.toBean(request.getParameterMap(), CartItem.class);
		cbiz.removeCartItemByCid(cart);
		return "r:/cartItemAction?flag=mycart";
	}
	public String removeCartItemByCidAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求流中的所有数据封装到对象中
		CartItem cart = BeanKit.toBean(request.getParameterMap(), CartItem.class);
		cbiz.removeCartItemByCid(cart);
		return null;
	}
	
	public String minusQuantityByCidAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求流中的所有数据封装到对象中
		CartItem cart = BeanKit.toBean(request.getParameterMap(), CartItem.class);
		cbiz.minusQuantityByCidAjax(cart);
		return null;
	}
	
	public String plusQuantityByCidAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求流中的所有数据封装到对象中
		CartItem cart = BeanKit.toBean(request.getParameterMap(), CartItem.class);
		cbiz.plusQuantityByCidAjax(cart);
		return null;
	}
	
	public String removeCartItemsByCids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] cids = request.getParameterValues("a[]");
		if(cids != null & cids.length > 0){
			cbiz.removeCartItemsByCids(cids);
		}
		return null;
	}
	
	//
	public String listCartItemsByCids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cartItemIds = request.getParameter("cartItemIds");
		double total = Double.parseDouble(request.getParameter("total"));
		List<CartItem> clist = cbiz.listCartItemsByCids(cartItemIds);
		request.setAttribute("clist", clist);
		request.setAttribute("total", total);
		request.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/showitem.jsp";
	}

}
