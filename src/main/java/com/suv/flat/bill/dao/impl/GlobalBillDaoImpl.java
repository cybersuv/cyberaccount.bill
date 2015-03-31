package com.suv.flat.bill.dao.impl;



import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.suv.flat.bill.dao.GlobalBillDAO;
import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.FlatBill;
import com.suv.flat.bill.vo.entity.GlobalBill;

public class GlobalBillDaoImpl extends HibernateDaoSupport implements GlobalBillDAO {

	@Override
	public TxResponse addBill(GlobalBill paramBill) {
		TxResponse response=new TxResponse();
		try{
			paramBill.setCreateDate(new Date());
			getHibernateTemplate().save(paramBill);
			response.setStatus(true);
			response.setResponseId(paramBill.getGlobalBillId());
			response.setResponseMsg("Global bill added successfully will bill ID : " + paramBill.getGlobalBillId());
		}catch(Exception ex){
			response.setStatus(false);
			response.setResponseId(-1);
			response.setResponseMsg("Exception occured in bill addition. Message : " + ex.getMessage());
			System.out.println("Exception in GlobalBill Addition : " + ex.getMessage());
		}
		return response;
	}

	@Override
	public TxResponse updateBill(GlobalBill paramBill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GlobalBill getBill(GlobalBill paramBill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TxResponse deleteBill(GlobalBill paramBill) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBilledMonths() {
		return (List<String>) getHibernateTemplate().find("select distinct monthStamp from GlobalBill");
	}

	@Override
	public List<GlobalBill> getGlobalBills(String monthStr) {
		System.out.println("Trying to get global bills for : " + monthStr);
		@SuppressWarnings("unchecked")
		List<GlobalBill> bills = ((List<GlobalBill>)getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(GlobalBill.class)
				.add(Restrictions.eq("monthStamp", monthStr))));
		return bills;
	}

	@Override
	public GlobalBill getGlobalBillByMonthAndType(String monthStr,
			String meterType) {
		System.out.println("Inside GlobalBillDAO... "+ meterType + " " + monthStr);
		@SuppressWarnings("unchecked")
		List<GlobalBill> bills = ((List<GlobalBill>)getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(GlobalBill.class)
				.add(Restrictions.eq("monthStamp", monthStr)).add(Restrictions.eq("meterType", meterType))));
		System.out.println("Query executed..." + bills.size());
		return bills.get(0);
	}

	
}
