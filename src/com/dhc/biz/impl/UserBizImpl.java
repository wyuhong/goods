package com.dhc.biz.impl;

import java.sql.SQLException;
import java.text.MessageFormat;

import javax.mail.Session;

import com.commons.PropKit;
import com.commons.StrKit;
import com.connection.ConnectionPoolManager;
import com.dhc.biz.IUserBiz;
import com.dhc.dao.IUserDao;
import com.dhc.dao.impl.UserDaoImpl;
import com.dhc.entity.User;
import com.mail.Mail;
import com.mail.MailKit;

public class UserBizImpl implements IUserBiz {
	
	private IUserDao udao = new UserDaoImpl();

	@Override
	public boolean existLoginname(String loginname) {
		boolean bl = false;
		try {
			bl = udao.existLoginname(loginname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return bl;
	}

	@Override
	public boolean existEmail(String email) {
		boolean bl = false;
		try {
			bl = udao.existEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return bl;
	}

	@Override
	public void regist(User user) {
		try {
			ConnectionPoolManager.beginTransaction();
			
			user.setUid(StrKit.uuid());
			user.setStatus(false);
			user.setActivationCode(StrKit.uuid() + StrKit.uuid());
			
			int i = udao.save(user);
			
			if(i > 0){
				PropKit.use("email_template.properties");
				String host = PropKit.get("host");
				String username = PropKit.get("username");
				String password = PropKit.get("password");
				Session session = MailKit.createSession(host, username, password);
				
				
				String from = PropKit.get("from");
				String to = user.getEmail();
				String subject = PropKit.get("subject");
				
				//{0}  {1} 
				String content = MessageFormat.format(PropKit.get("content"), user.getActivationCode());
				Mail mail = new Mail(from, to, subject, content);
				
				MailKit.send(session, mail);
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
	public void activatioin(String code) {
		try {
			User user = udao.findByCode(code);
			if(user == null) throw new Exception("无效的激活码！");
			if(user.isStatus()) throw new Exception("您已经激活过了，不要二次激活！");
			udao.updateStatus(user.getUid(), true);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public User login(User formUser) {
		User user = null;
		try {
			user = udao.findByNamePwd(formUser);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return user;
	}

	@Override
	public int updatePass(User user) {
		int i = 0;
		try {
			ConnectionPoolManager.beginTransaction();
			i = udao.updatePass(user);
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

}
