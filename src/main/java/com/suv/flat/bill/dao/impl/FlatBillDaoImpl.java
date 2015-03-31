package com.suv.flat.bill.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.internal.ast.tree.UpdateStatement;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.suv.flat.bill.common.Constants;
import com.suv.flat.bill.dao.FlatBillDAO;
import com.suv.flat.bill.dao.GlobalBillDAO;
import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.Accounts;
import com.suv.flat.bill.vo.entity.FlatBill;
import com.suv.flat.bill.vo.entity.GlobalBill;

public class FlatBillDaoImpl extends HibernateDaoSupport implements FlatBillDAO {

	GlobalBillDAO globalBillDao;

	public void setGlobalBillDao(GlobalBillDAO globalBillDao) {
		this.globalBillDao = globalBillDao;
	}

	@Override
	public TxResponse addBill(FlatBill paramBill) {
		TxResponse tx = new TxResponse();
		try {
			// getHibernateTemplate().flush();
			System.out.println("Going to save : " + paramBill);
			getHibernateTemplate().save(paramBill);
			System.out.println("Going to update global bill..");
			
			if (updateGlobalFlatBillSubMeterReading(paramBill.getMonthStamp()).isStatus()) {
				System.out.println("Global bill updation status..preparing response..");
				tx.setStatus(true);
				tx.setResponseId(paramBill.getFlatBillId());
				tx.setResponseMsg("Flat Bill has been succesfully added with Bill ID : "
						+ paramBill.getFlatBillId());
				System.out.println("Response prepared..");
			} else {
				tx.setStatus(true);
				tx.setResponseId(paramBill.getFlatBillId());
				tx.setResponseMsg("Flat Bill has been succesfully added with Bill ID : "
						+ paramBill.getFlatBillId()
						+ ", but Global bill unit not updated.");
			}
		} catch (Exception ex) {
			tx.setStatus(false);
			tx.setResponseId(-1);
			tx.setResponseMsg("Exception occured in Flat Bill addition. Nested message is : "
					+ ex.getMessage());
		}
		return tx;
	}

	@Override
	public TxResponse updateBill(FlatBill paramBill) {
		TxResponse tx = new TxResponse();
		try {
			// getHibernateTemplate().flush();
			System.out.println("Going to update : " + paramBill);
			getHibernateTemplate().update(paramBill);
			tx.setStatus(true);
			tx.setResponseId(paramBill.getFlatBillId());
			tx.setResponseMsg("Flat Bill ID " + paramBill.getFlatBillId()
					+ " has been succesfully updated.");
		} catch (Exception ex) {
			tx.setStatus(false);
			tx.setResponseId(-1);
			tx.setResponseMsg("Exception occured in Flat Bill updation. Mested message is : "
					+ ex.getMessage());
		}
		return tx;
	}

	@Override
	public FlatBill getBill(FlatBill paramBill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TxResponse deleteBill(FlatBill paramBill) {
		TxResponse tx = new TxResponse();
		try {
			// getHibernateTemplate().flush();
			System.out.println("Going to delete : " + paramBill);
			getHibernateTemplate().delete(paramBill);
			tx.setStatus(true);
			tx.setResponseId(paramBill.getFlatBillId());
			tx.setResponseMsg("Flat Bill[" + paramBill.getFlatBillId()
					+ "] has been succesfully deleted.");
		} catch (Exception ex) {
			tx.setStatus(false);
			tx.setResponseId(-1);
			tx.setResponseMsg("Exception occured in Flat Bill deeltion. Mested message is : "
					+ ex.getMessage());
		}
		return tx;
	}

	@Override
	public List<FlatBill> getBills(String monthStr) {
		@SuppressWarnings("unchecked")
		List<FlatBill> bills = ((List<FlatBill>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria.forClass(FlatBill.class).add(
								Restrictions.eq("monthStamp", monthStr)
										.ignoreCase())));
		return bills;
	}

	@Override
	public long getTotalFlatBillUnit(String monthStr) {
		long totalBillAmt = (long) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(FlatBill.class).setProjection(
						Projections.sum("consumptionUnit"))).get(0);
		System.out.println("Total Flat Bill Got : " + totalBillAmt);
		return totalBillAmt;
	}

	@Override
	public TxResponse updateGlobalFlatBillSubMeterReading(String monthStr) {
		TxResponse tx = new TxResponse();
		System.out.println("Getting flat global bill for month : " + monthStr);
		try {
			GlobalBill tempGlobalBill = globalBillDao.getGlobalBillByMonthAndType(monthStr,Constants.METER_TYPE_FLAT);
			System.out.println("Gor GlobalBill : " + tempGlobalBill.toString());
			tempGlobalBill
					.setSubMeterConsumptionUnit(getTotalFlatBillUnit(monthStr));
			getHibernateTemplate().flush();
			getHibernateTemplate().update(tempGlobalBill);
			tx.setStatus(true);
			tx.setResponseId(tempGlobalBill.getGlobalBillId());
			tx.setResponseMsg("Updated");
			System.out.println("Global bill updated...");
		} catch (Exception ex) {
			tx.setStatus(false);
			tx.setResponseMsg(ex.getMessage());
			tx.setResponseId(-1);
			ex.printStackTrace();
		}
		return tx;
	}

}
