package com.suv.flat.bill.bo.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.suv.flat.bill.bo.BillingBO;
import com.suv.flat.bill.common.Constants;
import com.suv.flat.bill.dao.AccountsDAO;
import com.suv.flat.bill.dao.FlatBillDAO;
import com.suv.flat.bill.dao.GlobalBillDAO;
import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.Accounts;
import com.suv.flat.bill.vo.entity.FlatBill;
import com.suv.flat.bill.vo.entity.GlobalBill;


public class BillingBoImpl implements BillingBO {
	GlobalBillDAO globalBillDao;

	// DI by Spring
	public void setGlobalBillDao(GlobalBillDAO globalDao) {
		this.globalBillDao = globalDao;
	}

	AccountsDAO accountsDao;

	// DI by Spring
	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}
	
	FlatBillDAO flatBillDao;
	
	public void setFlatBillDao(FlatBillDAO flatBillDao){
		this.flatBillDao=flatBillDao;
	}

	@Override
	@Transactional
	public TxResponse addGlobalBill(GlobalBill bill) {
		return globalBillDao.addBill(bill);
	}

	@Override
	public TxResponse updateGlobalBill(GlobalBill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GlobalBill getGlobalBill(GlobalBill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TxResponse deleteGlobalBill(GlobalBill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public TxResponse addFlatBill(FlatBill bill) {
		return flatBillDao.addBill(bill);
	}

	@Override
	public TxResponse updateFlatBill(FlatBill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GlobalBill getFlatBill(FlatBill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TxResponse deleteFlatBill(FlatBill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TxResponse processBill(String monthStr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlatBill> getFlatBills(String monthStr) {
		return flatBillDao.getBills(monthStr);
	}

	@Override
	public List<GlobalBill> getGlobalBills(String monthStr) {
		return globalBillDao.getGlobalBills(monthStr);
	}

	@Override
	public String getBillReport(String monthStr,String reportType) {
		if(reportType.equals(Constants.REPORT_TYPE_GLOBAL_BILL)){
			return getGlobalBillReportHtml(getGlobalBills(monthStr));
		}else if(reportType.equals(Constants.REPORT_TYPE_FLAT_BILL)){
			return getFlatBillReportHtml(getFlatBills(monthStr));
		}
		return null;
	}

	@Override
	public List<String> getMonthStamps() {
		return globalBillDao.getBilledMonths();
	}

	@Override
	public List<Accounts> getAccountBySearchString(String searchStr) {
		return accountsDao.getAccounts(searchStr);
	}

	@Override
	public String getGlobalBillReportHtml(List<GlobalBill> billList) {
		double totalAmount=0;
		String htmlResp="<br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"9\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Global Bills</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Bill ID</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Meter Type</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reading From</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reading Till</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Last Reading</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Current Reading</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Unit</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Sub-Meter Consumption</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Billed Amount</td></tr>";
		
		Iterator<GlobalBill> i=billList.iterator();
		GlobalBill tempTx;
		while(i.hasNext()){
			tempTx=(GlobalBill)i.next();
			htmlResp+="<tr><td class=\"datacell\">" + tempTx.getGlobalBillId() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getMeterType() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getReadingFrom() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getReadingTill() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getPrevReading() +"</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getCurrentReading() + "</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getConsumptionUnit() + "</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getSubMeterConsumptionUnit() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + tempTx.getBillAmount()+"</td></tr>";
			totalAmount+=tempTx.getBillAmount();
		}
		
		htmlResp+="<tr><td class=\"datacell\" colspan=\"8\" align=\"left\"><b>Total Amount</b></td><td class=\"datacell\" align=\"right\">" + totalAmount + "</td></tr>";
		htmlResp+="</table><br></br>";
		
		return htmlResp;
	}

	@Override
	public String getFlatBillReportHtml(List<FlatBill> billList) {
		double totalAmount=0;
		String htmlResp="<br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"12\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Global Bills</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Bill ID</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Owner(Flat)</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reading From</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reading Till</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Last Reading</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Current Reading</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Unit</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Rate/Unit</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Charge</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Common Charge</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Payment Amount</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Round-off</td></tr>";
		
		Iterator<FlatBill> i=billList.iterator();
		FlatBill tempTx;
		while(i.hasNext()){
			tempTx=(FlatBill)i.next();
			htmlResp+="<tr><td class=\"datacell\">" + tempTx.getFlatBillId() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getAccount().getOwnerName() +"(" + tempTx.getAccount().getFlatNumber() +")</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getReadingFrom() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getReadingTill() + "</td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getPrevReading() +"</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getCurrentReading() + "</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getConsumptionUnit() + "</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getRatePerUnit() + "</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getConsumptionCharge() + "</td>";
			htmlResp+="<td class=\"datacell\">"+ tempTx.getCommonMeterCharge() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + tempTx.getTotalPayable()+"</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + tempTx.getTotalRoundOff()+"</td></tr>";
			totalAmount+=tempTx.getTotalRoundOff();
		}
		
		htmlResp+="<tr><td class=\"datacell\" colspan=\"11\" align=\"left\"><b>Total Amount</b></td><td class=\"datacell\" align=\"right\">" + totalAmount + "</td></tr>";
		htmlResp+="</table><br></br>";
		
		return htmlResp;
	}

}
