package com.suv.flat.bill.dao;

import com.suv.flat.bill.vo.AuthResponse;
import com.suv.flat.bill.vo.entity.User;

public interface UserDAO {
	
	public AuthResponse authenticateUser(User paramUser);

}
