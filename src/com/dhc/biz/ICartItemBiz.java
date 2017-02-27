package com.dhc.biz;

import java.util.List;

import com.dhc.entity.CartItem;
import com.dhc.entity.User;

public interface ICartItemBiz {

	List<CartItem> listCartItemsByUser(User user);

	int saveCartItem(CartItem cart);

	int removeCartItemByCid(CartItem cart);

	void removeCartItemsByCids(String[] cids);

	void minusQuantityByCidAjax(CartItem cart);

	void plusQuantityByCidAjax(CartItem cart);

	List<CartItem> listCartItemsByCids(String cartItemIds);

}
