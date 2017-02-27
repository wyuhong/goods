package com.dhc.dao.impl;

import java.util.Date;

import org.junit.Test;

public class MyTest {

	@Test
	public void testInteger(){
		System.out.println(new Date());
		System.out.println(String.format("%tF %<tT", new Date()));
	}
}
