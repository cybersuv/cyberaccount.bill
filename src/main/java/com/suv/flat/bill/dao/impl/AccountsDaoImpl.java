package com.suv.flat.bill.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.suv.flat.bill.dao.AccountsDAO;
import com.suv.flat.bill.vo.entity.Accounts;


public class AccountsDaoImpl extends HibernateDaoSupport implements AccountsDAO {

	@Override
	public Accounts getAccount(Accounts paramAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Accounts> getAccounts(String searchStr) {
		List<Accounts> accounts = ((List<Accounts>)getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Accounts.class)
				.add(Restrictions.like("ownerName", searchStr,MatchMode.ANYWHERE).ignoreCase())));
		//List<Accounts> accounts = (List<Accounts>)getHibernateTemplate().find("from Accounts a where upper(a.ownerName) like '%" + searchStr.toUpperCase() + "%'");
		return accounts;
	}

}
