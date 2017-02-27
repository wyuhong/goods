package com.dhc.biz;

import com.dhc.entity.User;

public interface IUserBiz {

	boolean existLoginname(String loginname);

	boolean existEmail(String email);

	void regist(User user);

	void activatioin(String code);

	User login(User formUser);

	int updatePass(User user);
	
	
}
