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
		TxResponse tx = new TxResponse();
		try {
			// getHibernateTemplate().flush();
			System.out.println("Going to update : " + paramBill);
			getHibernateTemplate().update(paramBill);
			tx.setStatus(true);
			tx.setResponseId(paramBill.getGlobalBillId());
			tx.setResponseMsg("Global Bill ID " + paramBill.getGlobalBillId()
					+ " has been succesfully updated.");
			System.out.println("Global Bill ID " + paramBill.getGlobalBillId()
					+ " has been succesfully updated.");
		} catch (Exception ex) {
			tx.setStatus(false);
			tx.setResponseId(-1);
			tx.setResponseMsg("Exception occured in Global Bill updation. Nested message is : "
					+ ex.getMessage());
			System.out.println("Exception occured in Global Bill updation. Nested message is : "
					+ ex.getMessage());
		}
		return tx;
	}

	@Override
	public GlobalBill getBill(GlobalBill paramBill) {
		return getHibernateTemplate().get(GlobalBill.class, paramBill.getGlobalBillId());
	}

	@Override
	public TxResponse deleteBill(GlobalBill paramBill) {
		TxResponse tx = new TxResponse();
		try {
			// getHibernateTemplate().flush();
			System.out.println("Going to delete : " + paramBill);
			getHibernateTemplate().delete(paramBill);
			tx.setStatus(true);
			tx.setResponseId(paramBill.getGlobalBillId());
			tx.setResponseMsg("Global Bill[" + paramBill.getGlobalBillId()
					+ "] has been succesfully deleted.");
		} catch (Exception ex) {
			tx.setStatus(false);
			tx.setResponseId(-1);
			tx.setResponseMsg("Exception occured in Global Bill deeltion. Mested message is : "
					+ ex.getMessage());
		}
		return tx;
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
