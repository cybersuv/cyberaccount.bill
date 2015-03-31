package com.suv.flat.bill.dao;

import java.util.List;

import com.suv.flat.bill.vo.entity.Accounts;

public interface AccountsDAO {
	
	public Accounts getAccount(Accounts paramAccount);
	
	public List<Accounts> getAccounts(String searchStr);

}
