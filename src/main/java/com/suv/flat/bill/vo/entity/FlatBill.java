package com.suv.flat.bill.vo.entity;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name="flat_bill")
public class FlatBill {
	@Transient
	DecimalFormat f = new DecimalFormat("##.00");
	
	@Id @GeneratedValue
	@Column(name="flat_bill_id")
	private long flatBillId;
	
	@Column(name="month_stamp")
	private String monthStamp;
	
	@Column(name="reading_from")
	private Date readingFrom;
	
	@Column(name="reading_till")
	private Date readingTill;
	
	@Column(name="prev_reading")
	private long prevReading;
	
	@Column(name="current_reading")
	private long currentReading;
	
	@Column(name="consumption_unit")
	private long consumptionUnit;
	
	@Column(name="rate_per_unit")
	private double ratePerUnit;
	
	@Column(name="consumption_charge")
	private double consumptionCharge;
	
	@Column(name="common_charge")
	private double commonMeterCharge;
	
	@Column(name="total_payable")
	private double totalPayable;
	
	@Column(name="total_round_off")
	private double totalRoundOff;
	
	@ManyToOne
	@JoinColumn(name="owner_id",nullable=false)
	Accounts account;

	public long getFlatBillId() {
		return flatBillId;
	}

	public void setFlatBillId(long flatBillId) {
		this.flatBillId = flatBillId;
	}

	public String getMonthStamp() {
		return monthStamp;
	}

	public void setMonthStamp(String monthStamp) {
		this.monthStamp = monthStamp;
	}

	public Date getReadingFrom() {
		return readingFrom;
	}

	public void setReadingFrom(Date readingFrom) {
		this.readingFrom = readingFrom;
	}

	public Date getReadingTill() {
		return readingTill;
	}

	public void setReadingTill(Date readingTill) {
		this.readingTill = readingTill;
	}

	public long getPrevReading() {
		return prevReading;
	}

	public void setPrevReading(long prevReading) {
		this.prevReading = prevReading;
	}

	public long getCurrentReading() {
		return currentReading;
	}

	public void setCurrentReading(long currentReading) {
		this.currentReading = currentReading;
	}

	public long getConsumptionUnit() {
		return consumptionUnit;
	}

	public void setConsumptionUnit(long consumptionUnit) {
		this.consumptionUnit = consumptionUnit;
	}

	public double getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(double ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}

	public double getConsumptionCharge() {
		return consumptionCharge;
	}

	public void setConsumptionCharge(double consumptionCharge) {
		this.consumptionCharge = consumptionCharge;
	}

	public double getCommonMeterCharge() {
		return commonMeterCharge;
	}

	public void setCommonMeterCharge(double commonMeterCharge) {
		this.commonMeterCharge = commonMeterCharge;
	}

	public double getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(double totalPayable) {
		this.totalPayable = totalPayable;
	}

	public double getTotalRoundOff() {
		return totalRoundOff;
	}

	public void setTotalRoundOff(double totalRoundOff) {
		this.totalRoundOff = totalRoundOff;
	}

	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "FlatBill [flatBillId=" + flatBillId + ", monthStamp="
				+ monthStamp + ", readingFrom=" + readingFrom
				+ ", readingTill=" + readingTill + ", prevReading="
				+ prevReading + ", currentReading=" + currentReading
				+ ", consumptionUnit=" + consumptionUnit + ", ratePerUnit="
				+ ratePerUnit + ", consumptionCharge=" + consumptionCharge
				+ ", commonMeterCharge=" + commonMeterCharge
				+ ", totalPayable=" + totalPayable + ", totalRoundOff="
				+ totalRoundOff + ", account=" + account + "]";
	}
	
	private double getComputedConsumptionCharge(){
		return Double.parseDouble(f.format(this.consumptionUnit*this.ratePerUnit));
	}
	
	private double getTotalPayableAmount(){
		return Double.parseDouble(f.format(this.consumptionCharge+this.commonMeterCharge));
	}
	
	private double getTotalPayableRoundOff(){
		return Math.ceil(this.totalPayable);
	}
	
	public void computeBillingComponnets(){
		this.consumptionCharge=getComputedConsumptionCharge();
		this.totalPayable=getTotalPayableAmount();
		this.totalRoundOff=getTotalPayableRoundOff();
	}
	

}
