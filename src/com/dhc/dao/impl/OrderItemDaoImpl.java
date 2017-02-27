package com.dhc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.MapListHandler;

import com.commons.BeanKit;
import com.dbutils.TxQueryRunner;
import com.dhc.dao.IOrderItemDao;
import com.dhc.entity.Book;
import com.dhc.entity.Order;
import com.dhc.entity.OrderItem;

public class OrderItemDaoImpl implements IOrderItemDao {

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public List<OrderItem> listOrderItemsByOrder(Order order) {
		List<OrderItem> oilist = new ArrayList<OrderItem>();
		try {
			List<Map<String, Object>> mlist = qr.query(
					"select * from t_orderitem t,t_book b where t.bid=b.bid and oid=? ", new MapListHandler(),
					new Object[] { order.getOid() });
			for (Map<String, Object> map : mlist) {
				if(map != null && !map.isEmpty()){
					OrderItem oi = BeanKit.toBean(map, OrderItem.class);
					Book book = BeanKit.toBean(map, Book.class);
					oi.setBook(book);
					oilist.add(oi);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return oilist;
	}

}
