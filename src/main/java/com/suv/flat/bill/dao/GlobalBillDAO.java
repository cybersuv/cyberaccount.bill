package com.suv.flat.bill.dao;

import java.util.List;

import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.GlobalBill;

public interface GlobalBillDAO {
	
	public TxResponse addBill(GlobalBill paramBill);
	
	public TxResponse updateBill(GlobalBill paramBill);
	
	public GlobalBill getBill(GlobalBill paramBill);
	
	public TxResponse deleteBill(GlobalBill paramBill);
	
	public List<String> getBilledMonths();
	
	public List<GlobalBill> getGlobalBills(String monthStr);
	
	public GlobalBill getGlobalBillByMonthAndType(String monthStr,String meterType);

}
