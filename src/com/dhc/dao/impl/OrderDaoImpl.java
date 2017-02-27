package com.dhc.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.commons.PageBean;
import com.dbutils.TxQueryRunner;
import com.dhc.dao.IOrderDao;
import com.dhc.entity.Order;
import com.dhc.entity.OrderItem;
import com.dhc.entity.User;

public class OrderDaoImpl implements IOrderDao {

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public void saveOrder(Order order) {
		try {
			// 插入订单
			String sql = "insert into t_order values(?,?,?,?,?,?)";
			Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getStatus(),
					order.getAddress(), order.getUser().getUid() };
			qr.update(sql, params);

			// 插入订单详细条目表
			sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
			for (int j = 0; j < order.getOrderItemList().size(); j++) {
				OrderItem item = order.getOrderItemList().get(j);
				Object[] objs = new Object[] { item.getOrderItemId(), item.getQuantity(), item.getSubtotal(),
						item.getBook().getBid(), item.getBook().getBname(), item.getBook().getCurrPrice(),
						item.getBook().getImage_b(), order.getOid() };
				qr.update(sql, objs);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int countOrdersByUser(User user) {
		int i = 0;
		try {
			Number num = (Number) qr.query("select count(*) from t_order where uid=?", new ScalarHandler<>(),
					new Object[] { user.getUid() });
			i = num.intValue();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public List<Order> listOrdersByUser(User user, PageBean<Order> pb) {
		List<Order> olist = null;
		try {
			int pn = pb.getPageNumber();
			int ps = pb.getPageSize();
			olist = qr.query("select * from t_order where uid=? order by ordertime desc limit ?,? ", new BeanListHandler<>(Order.class),
					new Object[] { user.getUid(), (pn - 1) * ps, ps });
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return olist;
	}

	@Override
	public Order getOrderByOid(Order order) {
		try {
			order = qr.query("select * from t_order where oid=? ", new BeanHandler<>(Order.class), new Object[]{order.getOid()});
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return order;
	}

	@Override
	public int getStatusByOid(String oid) {
		int i = 0;
		try {
			Number num = (Number)qr.query("select status from t_order where oid=? ", new ScalarHandler<>(), new Object[]{oid});
			i = num.intValue();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int updateStatusByOid(String oid, int i) {
		int j = 0;
		try {
			j = qr.update("update t_order set status=? where oid=?", new Object[]{i,oid});
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return j;
	}

}
