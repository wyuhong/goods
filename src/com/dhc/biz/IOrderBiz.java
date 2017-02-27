package com.dhc.biz;

import java.util.List;

import com.commons.PageBean;
import com.dhc.entity.Order;
import com.dhc.entity.User;

public interface IOrderBiz {

	void saveOrder(Order order);

	int countOrdersByUser(User user);

	List<Order> listOrdersByUser(User user, PageBean<Order> pb);

	Order getOrderByOid(Order order);

	int getStatusByOid(String oid);

	int updateStatusByOid(String oid, int i);

}
