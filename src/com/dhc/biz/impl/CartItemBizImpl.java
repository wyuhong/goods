package com.dhc.biz.impl;

import java.sql.SQLException;
import java.util.List;

import com.connection.ConnectionPoolManager;
import com.dhc.biz.ICartItemBiz;
import com.dhc.dao.ICartItemDao;
import com.dhc.dao.impl.CartItemDaoImpl;
import com.dhc.entity.CartItem;
import com.dhc.entity.User;

public class CartItemBizImpl implements ICartItemBiz {

	private ICartItemDao cdao = new CartItemDaoImpl();
	
	@Override
	public List<CartItem> listCartItemsByUser(User user) {
		List<CartItem> clist = null;
		try {
			clist = cdao.listCartItemsByUser(user);
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
			ConnectionPoolManager.beginTransaction();
			int count = cdao.countCartItemByBidUid(cart);
			if(count > 0 ){
				i = cdao.updateCartItemQuantity(cart);
			}else{
				i = cdao.saveCartItem(cart);
			}
			ConnectionPoolManager.commitTransaction();
		} catch (Exception e) {
			try {
				ConnectionPoolManager.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public int removeCartItemByCid(CartItem cart) {
		int i = 0;
		try {
			ConnectionPoolManager.beginTransaction();
			i = cdao.removeCartItemByCid(cart);
			ConnectionPoolManager.commitTransaction();
		} catch (Exception e) {
			try {
				ConnectionPoolManager.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public void removeCartItemsByCids(String[] cids) {
		try {
			ConnectionPoolManager.beginTransaction();
			for (String cid : cids) {
				CartItem cart  = new CartItem();
				cart.setCartItemId(cid);
				cdao.removeCartItemByCid(cart);
			}
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
	public void minusQuantityByCidAjax(CartItem cart) {
		try {
			ConnectionPoolManager.beginTransaction();
			cdao.minusQuantityByCidAjax(cart);
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
	public void plusQuantityByCidAjax(CartItem cart) {
		try {
			ConnectionPoolManager.beginTransaction();
			cdao.plusQuantityByCidAjax(cart);
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
	public List<CartItem> listCartItemsByCids(String cartItemIds) {
		List<CartItem> clist = null;
		try {
			clist = cdao.listCartItemsByCids(cartItemIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return clist;
	}

}
