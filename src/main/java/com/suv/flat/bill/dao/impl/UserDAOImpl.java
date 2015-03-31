package com.suv.flat.bill.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.suv.flat.bill.dao.UserDAO;
import com.suv.flat.bill.vo.AuthResponse;
import com.suv.flat.bill.vo.entity.User;

public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {

	@Override
	public AuthResponse authenticateUser(User paramUser) {
		AuthResponse response = new AuthResponse();
		try {
			System.out.println("Going to authenticate user : " + paramUser);
			
			User authUser= ((List<User>)getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(User.class)
					.add(Restrictions.eq("user_login", paramUser.getUser_login()))
					.add(Restrictions.eq("password",paramUser.getPassword())))).get(0);
			
			//User authUser = (User) getHibernateTemplate().find("from users where user_login=? and user_password=?",paramUser.getUser_login(),paramUser.getPassword()).get(0);
			if (authUser != null) {
				response.setStatus(true);
				response.setUser(authUser);
			} else {
				response.setStatus(false);
				response.setExceptionMsg("Couldn't authenticate the user with given user id and/or password");
			}
		} catch (Exception ex) {
			response.setStatus(false);
			response.setExceptionMsg("Exception occured in authentication. Nested message is : "
					+ ex.getMessage());
		}
		return response;
	}

}
