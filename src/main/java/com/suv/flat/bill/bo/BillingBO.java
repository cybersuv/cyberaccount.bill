package com.suv.flat.bill.bo;

import java.util.List;

import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.Accounts;
import com.suv.flat.bill.vo.entity.FlatBill;
import com.suv.flat.bill.vo.entity.GlobalBill;

public interface BillingBO {
	
	/** Methods related to GlobalBill **/
	public TxResponse addGlobalBill(GlobalBill bill);
	
	public TxResponse updateGlobalBill(GlobalBill bill);
	
	public GlobalBill getGlobalBill(GlobalBill bill);
	
	public TxResponse deleteGlobalBill(GlobalBill bill);
	
	/** Methods related to FlatBill **/
	public TxResponse addFlatBill(FlatBill bill);
	
	public TxResponse updateFlatBill(FlatBill bill);
	
	public GlobalBill getFlatBill(FlatBill bill);
	
	public TxResponse deleteFlatBill(FlatBill bill);
	
	/** Process Bill **/
	public TxResponse processBill(String monthStr);
	
	public List<FlatBill> getFlatBills(String monthStr);
	
	public List<GlobalBill> getGlobalBills(String monthStr);
	
	public String getBillReport(String monthStr,String reportType);
	
	/** Generic **/
	public List<String> getMonthStamps();
	
	public List<Accounts> getAccountBySearchString(String searchStr);
	
	public String getGlobalBillReportHtml(List<GlobalBill> billList);
	
	public String getFlatBillReportHtml(List<FlatBill> billList);
	
	

}
