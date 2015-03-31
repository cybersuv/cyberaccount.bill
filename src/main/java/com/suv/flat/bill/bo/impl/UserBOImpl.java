package com.suv.flat.bill.bo.impl;

import com.suv.flat.bill.bo.UserBO;
import com.suv.flat.bill.dao.UserDAO;
import com.suv.flat.bill.vo.AuthResponse;
import com.suv.flat.bill.vo.entity.User;

public class UserBOImpl implements UserBO {
	
	UserDAO userDao;
	
	public void setUserDAO(UserDAO userDao){
		this.userDao=userDao;
	}

	@Override
	public AuthResponse authenticate(User paramUser) {
		return userDao.authenticateUser(paramUser);
	}

}
