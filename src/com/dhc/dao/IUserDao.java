package com.dhc.dao;

import com.dhc.entity.User;

public interface IUserDao {

	boolean existLoginname(String loginname);

	boolean existEmail(String email);

	int save(User user);

	User findByCode(String code);

	void updateStatus(String uid, boolean b);

	User findByNamePwd(User formUser);

	int updatePass(User user);

}
