package com.dhc.dao.impl;

import org.junit.Test;

public class UserDaoImplTest {

	@Test
	public void testExistLoginname(){
		UserDaoImpl udao = new UserDaoImpl();
		System.out.println(udao.existLoginname("wangWuuuuuuuuu"));
	}
}
