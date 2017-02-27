package com.dhc.dao;

import java.util.List;

import com.dhc.entity.Order;
import com.dhc.entity.OrderItem;

public interface IOrderItemDao {

	List<OrderItem> listOrderItemsByOrder(Order order);

}
