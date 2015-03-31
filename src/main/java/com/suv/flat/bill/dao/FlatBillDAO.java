package com.suv.flat.bill.dao;

import java.util.List;

import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.FlatBill;


public interface FlatBillDAO {
	
	public TxResponse addBill(FlatBill paramBill);
	
	public TxResponse updateBill(FlatBill paramBill);
	
	public FlatBill getBill(FlatBill paramBill);
	
	public List<FlatBill> getBills(String monthStr);
	
	public TxResponse deleteBill(FlatBill paramBill);
	
	public long getTotalFlatBillUnit(String monthStr);
	
	public TxResponse updateGlobalFlatBillSubMeterReading(String monthStr);

}
