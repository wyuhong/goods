package com.dhc.entity;

import java.math.BigDecimal;

public class CartItem {
	private String cartItemId;
	private int quantity;
	private Book book;
	private User user;
	
	/*
	 * 购物车小计功能，BigDecimal构造器要使用带String参数的
	 * 购物车小计 = 当前价格*数量
	 */
	public double getSubtotal() {
		BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static void main(String[] args) {
		System.out.println(new BigDecimal(2.0+"").subtract(new BigDecimal(1.9+"")));
	}
}
