package com.dhc.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.BeanKit;
import com.commons.PageBean;
import com.commons.PropKit;
import com.commons.StrKit;
import com.dhc.biz.ICartItemBiz;
import com.dhc.biz.IOrderBiz;
import com.dhc.biz.impl.CartItemBizImpl;
import com.dhc.biz.impl.OrderBizImpl;
import com.dhc.entity.CartItem;
import com.dhc.entity.Order;
import com.dhc.entity.OrderItem;
import com.dhc.entity.User;
import com.servlet.BaseServlet;

public class OrderAction extends BaseServlet {

	private ICartItemBiz cbiz = new CartItemBizImpl();
	private IOrderBiz obiz = new OrderBizImpl();

	public String saveOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取前台的购物车条目ID，通过多个ID获取一个购物车条目集合
		String cids = request.getParameter("cartItemIds");
		List<CartItem> clist = cbiz.listCartItemsByCids(cids);
		// 创建订单
		Order order = new Order();
		order.setOid(StrKit.uuid());
		// 将Date转换为String
		order.setOrdertime(String.format("%tF %<tT", new Date()));
		// 设置状态，1表示未付款
		order.setStatus(1);
		order.setAddress(request.getParameter("address"));
		order.setUser((User) request.getSession().getAttribute("sessionUser"));
		// order表中的合计字段
		BigDecimal total = new BigDecimal(0 + "");
		for (CartItem cat : clist) {
			total = total.add(new BigDecimal(cat.getSubtotal() + ""));
		}
		order.setTotal(total.doubleValue());
		// 创建订单详细条目对象
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (CartItem cat : clist) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(StrKit.uuid());
			orderItem.setQuantity(cat.getQuantity());
			orderItem.setSubtotal(cat.getSubtotal());
			orderItem.setBook(cat.getBook());
			// 注意这里的写法，这个是双向的一对多
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		// 调用业务层保存订单对象
		obiz.saveOrder(order);
		// 调用业务层删除购物车条目
		cbiz.removeCartItemsByCids(cids.split(","));
		// 将订单对象存放到request中
		request.setAttribute("order", order);
		return "f:/jsps/order/ordersucc.jsp";
	}

	public String listOrdersByUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("sessionUser");
		PageBean<Order> pb = new PageBean<Order>();
		//第一步：设置pageNumber
		pb.setPageNumber(super.getPageNumber(request));
		//第二步：设置pageSize
		pb.setPageSize(PropKit.use("pagesize.properties").getInt("book_page_size"));
		//第三步：设置url
		pb.setUrl(super.getUrl(request, null));
		//第四步：设置list(list是指定页码显示内容的集合)
		pb.setList(obiz.listOrdersByUser(user,pb));
		//第五步：设置totalRecords
		pb.setTotalRecords(obiz.countOrdersByUser(user));
		//第六步：将pb存放到request中
		request.setAttribute("pb", pb);
		//第七步：转发到下一个页面
		return "f:/jsps/order/list.jsp";
	}
	
	//
	public String getOrderByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Order order = BeanKit.toBean(request.getParameterMap(), Order.class);
		order = obiz.getOrderByOid(order);
		request.setAttribute("order", order);
		request.setAttribute("action", request.getParameter("action"));
		return "f:/jsps/order/desc.jsp";
	}
	
	public String cancelOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		//校验订单状态
		int status = obiz.getStatusByOid(oid);
		if(status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消订单！");
			return "f:/jsps/msg.jsp";
		}
		obiz.updateStatusByOid(oid, 5);//设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消，欢迎再来！");
		return "f:/jsps/msg.jsp";		
	}
	
	public String confirmOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		//校验订单状态
		int status = obiz.getStatusByOid(oid);
		if(status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能确认收货！");
			return "f:/jsps/msg.jsp";
		}
		obiz.updateStatusByOid(oid, 4);//设置状态为交易成功！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜，交易成功！");
		return "f:/jsps/msg.jsp";		
	}
	

}
