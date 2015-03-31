package com.suv.flat.bill.bo;

import com.suv.flat.bill.vo.AuthResponse;
import com.suv.flat.bill.vo.entity.User;

public interface UserBO {
	
	public AuthResponse authenticate(User paramUser);

}
