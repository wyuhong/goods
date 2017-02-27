package com.dhc.biz.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.commons.PageBean;
import com.connection.ConnectionPoolManager;
import com.dhc.biz.IOrderBiz;
import com.dhc.dao.IOrderDao;
import com.dhc.dao.IOrderItemDao;
import com.dhc.dao.impl.OrderItemDaoImpl;
import com.dhc.dao.impl.OrderDaoImpl;
import com.dhc.entity.Order;
import com.dhc.entity.OrderItem;
import com.dhc.entity.User;

public class OrderBizImpl implements IOrderBiz {

	private IOrderDao odao = new OrderDaoImpl();
	private IOrderItemDao oidao = new OrderItemDaoImpl();
	
	@Override
	public void saveOrder(Order order) {
		try {
			ConnectionPoolManager.beginTransaction();
			odao.saveOrder(order);
			ConnectionPoolManager.commitTransaction();
		} catch (Exception e) {
			try {
				ConnectionPoolManager.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int countOrdersByUser(User user) {
		int i = 0;
		try {
			i = odao.countOrdersByUser(user);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public List<Order> listOrdersByUser(User user, PageBean<Order> pb) {
		List<Order> olist = new ArrayList<Order>();
		try {
			List<Order> list = odao.listOrdersByUser(user,pb);
			if(list != null && !list.isEmpty()){
				for (Order order : list) {
					List<OrderItem> oilist = oidao.listOrderItemsByOrder(order);
					order.setOrderItemList(oilist);
					olist.add(order);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return olist;
	}

	@Override
	public Order getOrderByOid(Order order) {
		try {
			order = odao.getOrderByOid(order);
			List<OrderItem> oilist = oidao.listOrderItemsByOrder(order);
			order.setOrderItemList(oilist);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return order;
	}

	@Override
	public int getStatusByOid(String oid) {
		int i = 0;
		try {
			i = odao.getStatusByOid(oid);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int updateStatusByOid(String oid, int i) {
		int j = 0;
		try {
			ConnectionPoolManager.beginTransaction();
			j = odao.updateStatusByOid(oid,i);
			ConnectionPoolManager.commitTransaction();
		} catch (Exception e) {
			try {
				ConnectionPoolManager.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		return j;
	}
	

}
