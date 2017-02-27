package com.dhc.dao.impl;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dbutils.TxQueryRunner;
import com.dhc.dao.IUserDao;
import com.dhc.entity.User;

public class UserDaoImpl implements IUserDao {

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public boolean existLoginname(String loginname) {
		boolean bl = false;
		try {
			Number num = (Number) qr.query("select count(*) from t_user where loginname=? ", new ScalarHandler<>(),
					new Object[] { loginname });
			if (num.intValue() > 0) {
				bl = true;
			}
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
			Number num = (Number) qr.query("select count(*) from t_user where email=? ", new ScalarHandler<>(),
					new Object[] { email });
			if (num.intValue() > 0) {
				bl = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return bl;
	}

	@Override
	public int save(User user) {
		int i = 0;
		try {
			i = qr.update("insert into t_user values(?,?,?,?,?,?)", new Object[] { user.getUid(), user.getLoginname(),
					user.getLoginpass(), user.getEmail(), user.isStatus(), user.getActivationCode() });
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

	@Override
	public User findByCode(String code) {
		User user = null;
		try {
			user = qr.query("select * from t_user where activationCode=? ", new BeanHandler<>(User.class),
					new Object[] { code });
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return user;
	}

	@Override
	public void updateStatus(String uid, boolean b) {
		try {
			qr.update("update t_user set status=? where uid=? ", new Object[] { b, uid });
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public User findByNamePwd(User formUser) {
		User user = null;
		try {
			user = qr.query("select * from t_user where loginname=? and loginpass=? ", new BeanHandler<>(User.class),
					new Object[] { formUser.getLoginname(), formUser.getLoginpass() });
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return user;
	}

	@Override
	public int updatePass(User user) {
		int i = 0;
		try {
			i = qr.update("update t_user set loginpass=? where uid=? ", new Object[]{user.getNewpass(),user.getUid()});
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return i;
	}

}
