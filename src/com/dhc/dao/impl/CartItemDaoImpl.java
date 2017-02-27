package com.dhc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.commons.BeanKit;
import com.commons.StrKit;
import com.dbutils.TxQueryRunner;
import com.dhc.dao.ICartItemDao;
import com.dhc.entity.Book;
import com.dhc.entity.CartItem;
import com.dhc.entity.User;

public class CartItemDaoImpl implements ICartItemDao {

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public List<CartItem> listCartItemsByUser(User user) {
		List<CartItem> clist = new ArrayList<CartItem>();
		try {
			List<Map<String, Object>> mlist = qr.query(
					" select * from t_cartitem c,t_book b where c.bid=b.bid and uid=? ", new MapListHandler(),
					new Object[] { user.getUid() });
			for (Map<String, Object> map : mlist) {
				if (map != null && !map.isEmpty()) {
					CartItem cat = BeanKit.toBean(map, CartItem.class);
					Book book = BeanKit.toBean(map, Book.class);
					cat.setBook(book);
					cat.setUser(user);
					clist.add(cat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return clist;
	}

	@Override
	public int saveCartItem(CartItem cart) {
		int i = 0;
		try {
			i = qr.update("insert into t_cartitem(cartItemId,quantity,bid,uid) values(?,?,?,?)", new Object[] {
					StrKit.uuid(), cart.getQuantity(), cart.getBook().getBid(), cart.getUser().getUid() });
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int removeCartItemByCid(CartItem cart) {
		int i = 0;
		try {
			i = qr.update("delete from t_cartitem where cartItemId=? ", new Object[] { cart.getCartItemId() });
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int countCartItemByBidUid(CartItem cart) {
		int i = 0;
		try {
			Number num = (Number) qr.query("select count(*) from t_cartitem where bid=? and uid=?",
					new ScalarHandler<>(), new Object[] { cart.getBook().getBid(), cart.getUser().getUid() });
			i = num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int updateCartItemQuantity(CartItem cart) {
		int i = 0;
		try {
			String cartItemId = (String) qr.query("select cartItemId from t_cartitem where bid=? and uid=? ",
					new ScalarHandler<>(), new Object[] { cart.getBook().getBid(), cart.getUser().getUid() });
			i = qr.update("update t_cartitem set quantity=quantity+? where cartItemId=? ", new Object[]{cart.getQuantity(),cartItemId});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int minusQuantityByCidAjax(CartItem cart) {
		int i = 0;
		try {
			i = qr.update("update t_cartitem set quantity=quantity-1 where cartItemId=? ", new Object[]{cart.getCartItemId()});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int plusQuantityByCidAjax(CartItem cart) {
		int i = 0;
		try {
			i = qr.update("update t_cartitem set quantity=quantity+1 where cartItemId=? ", new Object[]{cart.getCartItemId()});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public List<CartItem> listCartItemsByCids(String cartItemIds) {
		List<CartItem> clist = new ArrayList<CartItem>();
		try {
			Object[] cids = cartItemIds.split(",");
			String insql = this.insql(cids.length);
			String sql = " select * from t_cartitem c,t_book b where c.bid=b.bid and " + insql;
			List<Map<String, Object>> mlist = qr.query(sql, new MapListHandler(),cids);
			for (Map<String, Object> map : mlist) {
				if (map != null && !map.isEmpty()) {
					CartItem cat = BeanKit.toBean(map, CartItem.class);
					Book book = BeanKit.toBean(map, Book.class);
					User user = BeanKit.toBean(map, User.class);
					cat.setBook(book);
					cat.setUser(user);
					clist.add(cat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return clist;
	}

	private String insql(int length) {
		StringBuilder sb = new StringBuilder("cartItemId in (");
		for(int i = 0; i < length; i++) {
			sb.append("?");
			if(i < length - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

}
