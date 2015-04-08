package com.suv.flat.bill.bo.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
	@Transactional
	public TxResponse updateGlobalBill(GlobalBill bill) {
		return globalBillDao.updateBill(bill);
	}

	@Override
	public GlobalBill getGlobalBill(GlobalBill bill) {
		return globalBillDao.getBill(bill);
	}

	@Override
	@Transactional
	public TxResponse deleteGlobalBill(GlobalBill bill) {
		return globalBillDao.deleteBill(bill);
	}

	@Override
	@Transactional
	public TxResponse addFlatBill(FlatBill bill) {
		return flatBillDao.addBill(bill);
	}

	@Override
	@Transactional
	public TxResponse updateFlatBill(FlatBill bill) {
		return flatBillDao.updateBill(bill);
	}

	@Override
	public FlatBill getFlatBill(FlatBill bill) {
		return flatBillDao.getBill(bill);
	}

	@Override
	@Transactional
	public TxResponse deleteFlatBill(FlatBill bill) {
		return flatBillDao.deleteBill(bill);
	}

	@Override
	@Transactional
	public TxResponse processBill(String monthStr) {
		return flatBillDao.processMonthlyBill(monthStr);
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
		}else if(reportType.equals(Constants.REPORT_TYPE_SUMMARY)){
			return getSummarisedBillReport(monthStr);
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
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		DecimalFormat df=new DecimalFormat("#.00");
		double totalAmount=0;
		String htmlResp="<br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"10\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Global Bills</font></td></tr>";
		htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Bill ID</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Meter Type</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reading From</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Reading Till</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Last Reading</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Current Reading</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Unit</td>";
		htmlResp+= "<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Sub-Meter Consumption</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Billed Amount</td><td>X</td></tr>";
		
		Iterator<GlobalBill> i=billList.iterator();
		GlobalBill tempTx;
		while(i.hasNext()){
			tempTx=(GlobalBill)i.next();
			htmlResp+="<tr><td class=\"datacell\"><a href=\"javascript:fetchGlobalBill("+ tempTx.getGlobalBillId() + ");\">" + tempTx.getGlobalBillId() + "</a></td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getMeterType() + "</td>";
			htmlResp+="<td class=\"datacell\">" + sdf.format(tempTx.getReadingFrom()) + "</td>";
			htmlResp+="<td class=\"datacell\">" + sdf.format(tempTx.getReadingTill()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + tempTx.getPrevReading() +"</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ tempTx.getCurrentReading() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ tempTx.getConsumptionUnit() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ tempTx.getSubMeterConsumptionUnit() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(tempTx.getBillAmount())+"</td>";
			htmlResp+="<td class=\"datacell\"><a href=\"javascript:deleteGlobalBill("+ tempTx.getGlobalBillId() + ");\">X</a></td></tr>";
			totalAmount+=tempTx.getBillAmount();
		}
		
		htmlResp+="<tr><td class=\"datacell\" colspan=\"8\" align=\"left\"><b>Total Amount</b></td><td class=\"datacell\" align=\"right\">" + totalAmount + "</td><td></td></tr>";
		htmlResp+="</table><br></br>";
		
		return htmlResp;
	}

	@Override
	public String getFlatBillReportHtml(List<FlatBill> billList) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		DecimalFormat df=new DecimalFormat("#.00");
		double totalAmount=0;
		String htmlResp="<br></br>";
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"13\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Global Bills</font></td></tr>";
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
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Round-off</td><td>X</td></tr>";
		
		Iterator<FlatBill> i=billList.iterator();
		FlatBill tempTx;
		while(i.hasNext()){
			tempTx=(FlatBill)i.next();
			htmlResp+="<tr><td class=\"datacell\"><a href=\"javascript:fetchFlatBill("+ tempTx.getFlatBillId() + ");\">" + tempTx.getFlatBillId() + "</a></td>";
			htmlResp+="<td class=\"datacell\">" + tempTx.getAccount().getOwnerName() +"(" + tempTx.getAccount().getFlatNumber() +")</td>";
			htmlResp+="<td class=\"datacell\">" + sdf.format(tempTx.getReadingFrom()) + "</td>";
			htmlResp+="<td class=\"datacell\">" + sdf.format(tempTx.getReadingTill()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + tempTx.getPrevReading() +"</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ tempTx.getCurrentReading() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ tempTx.getConsumptionUnit() + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ df.format(tempTx.getRatePerUnit()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ df.format(tempTx.getConsumptionCharge()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ df.format(tempTx.getCommonMeterCharge()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(tempTx.getTotalPayable())+"</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(tempTx.getTotalRoundOff())+"</td>";
			htmlResp+="<td class=\"datacell\"><a href=\"javascript:deleteFlatBill("+ tempTx.getFlatBillId() + ");\">X</a></td>";
			totalAmount+=tempTx.getTotalRoundOff();
		}
		
		htmlResp+="<tr><td class=\"datacell\" colspan=\"11\" align=\"left\"><b>Total Amount</b></td><td class=\"datacell\" align=\"right\">" + totalAmount + "</td><td></td></tr>";
		htmlResp+="</table><br></br>";
		
		return htmlResp;
	}

	@Override
	public String getSummarisedBillReport(String monthStr) {
		/** Declare Local Variables **/
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat sdfm=new SimpleDateFormat("MMM-yyyy");
		DecimalFormat df=new DecimalFormat("#.00");
		double totalAmount=0;
		String htmlResp="";
		double commonChargePerFlat=0.00;
		double effectiveRatePerUnit=0.00;
		double extraCollectedAmount=0.00;
		GlobalBill commonBill,flatBill;
		List<FlatBill> flatBills;
		
		htmlResp+="<center><u>Electric Bill Intermediate Calculation for the Month of " + monthStr + "</u></center>";
		
		/** Process Global Bills **/
		commonBill=globalBillDao.getGlobalBillByMonthAndType(monthStr, Constants.METER_TYPE_COMMON);
		flatBill=globalBillDao.getGlobalBillByMonthAndType(monthStr, Constants.METER_TYPE_FLAT);
		
		commonChargePerFlat=Math.ceil(commonBill.getBillAmount()/10);
		effectiveRatePerUnit=Double.parseDouble(df.format(Math.ceil(Double.parseDouble(df.format(flatBill.getBillAmount()/flatBill.getSubMeterConsumptionUnit()))*10)/10));
		
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"5\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Common Meter Details</font></td></tr>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Last Reading (" + sdf.format(commonBill.getReadingFrom()) +")</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Current Reading (" + sdf.format(commonBill.getReadingTill()) +")</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Unit</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Payable Amount</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Common Charge Per Flat</td></tr>";
		
		htmlResp+="<td class=\"datacell\" align=\"center\">"+ commonBill.getPrevReading() +"</td>";
		htmlResp+="<td class=\"datacell\" align=\"center\">"+ commonBill.getCurrentReading() +"</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">"+ commonBill.getConsumptionUnit()+ "</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(commonBill.getBillAmount()) + "</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(commonChargePerFlat)+ "</td></tr></table>";
		
		//htmlResp+="<br></br>";
		
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"6\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Flat Meter Details</font></td></tr>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Last Reading (" + sdfm.format(flatBill.getReadingFrom()) +")</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Current Reading (" + sdfm.format(flatBill.getReadingTill()) +")</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Unit</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Payable Amount</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Sub-Meter total Unit</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Effective Rate/Unit</td></tr>";
		
		htmlResp+="<td class=\"datacell\" align=\"center\">"+ flatBill.getPrevReading() +"</td>";
		htmlResp+="<td class=\"datacell\" align=\"center\">"+ flatBill.getCurrentReading() +"</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">"+ flatBill.getConsumptionUnit()+ "</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(flatBill.getBillAmount()) + "</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">" + flatBill.getSubMeterConsumptionUnit() + "</td>";
		htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(effectiveRatePerUnit) + "</td></tr></table>";
		
		
		/** Process Flat Bills **/
		flatBills=getFlatBills(monthStr);
		
		htmlResp+="<table class=\"datatable\" cellspacing=\"1\">";
		htmlResp+="<tr><td class=\"datacell\" colspan=\"7\" bgcolor=\"#848484\" align=\"center\"><font color=\"white\">Flat-Wise Details</font></td></tr>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Flat Owner</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Last Reading (" + sdf.format(flatBill.getReadingFrom()) +")</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Current Reading (" + sdf.format(flatBill.getReadingTill()) +")</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption Unit</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Comsumption Amount</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Consumption+Common Charge</td>";
		htmlResp+="<td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"center\">Payable Round-Off</td></tr>";
		
		for(int i=0;i<flatBills.size();i++){
			htmlResp+="<tr><td class=\"datacell\" bgcolor=\"#E6E6E6\" align=\"left\">"+ flatBills.get(i).getAccount().getOwnerName() + "(" + flatBills.get(i).getAccount().getFlatNumber() +  ")</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ flatBills.get(i).getPrevReading() +"</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ flatBills.get(i).getCurrentReading()+ "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">"+ flatBills.get(i).getConsumptionUnit()+ "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(flatBills.get(i).getConsumptionCharge()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(flatBills.get(i).getTotalPayable()) + "</td>";
			htmlResp+="<td class=\"datacell\" align=\"right\">" + df.format(flatBills.get(i).getTotalRoundOff()) + "</td></tr>";
			totalAmount+=flatBills.get(i).getTotalRoundOff();
		}
		htmlResp+="<tr><td colspan=\"6\" bgcolor=\"#848484\" color=\"#FFFFFF\" align=\"left\">Total</td><td bgcolor=\"#848484\" color=\"#FFFFFF\" align=\"right\">"+ df.format(totalAmount) + "</td></tr></table>";
		
		extraCollectedAmount=totalAmount-(commonBill.getBillAmount()+flatBill.getBillAmount());
		
		htmlResp+="<u><b>Extra round-off amount (to be deposited in flat account) : " + df.format(extraCollectedAmount) + "</b></u>";
		
		return htmlResp;
	}

}
