package com.suv.flat.bill.vo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="global_bill")
public class GlobalBill {
	@Id @GeneratedValue
	@Column(name="global_bill_id")
	private long globalBillId;
	
	@Column(name="month_stamp")
	private String monthStamp;
	
	@Column(name="meter_type")
	private String meterType;
	
	@Column(name="reading_from")
	private Date readingFrom;
	
	@Column(name="reading_till")
	private Date readingTill;
	
	@Column(name="prev_reading")
	private long prevReading;
	
	@Column(name="current_reading")
	private long currentReading;
	
	@Column(name="billed_consumption_unit")
	private long consumptionUnit;
	
	@Column(name="sub_meter_consumption_unit")
	private long subMeterConsumptionUnit;
	
	@Column(name="bill_amount")
	private double billAmount;
	
	@Column(name="create_date")
	private Date createDate;

	public long getGlobalBillId() {
		return globalBillId;
	}

	public void setGlobalBillId(long globalBillId) {
		this.globalBillId = globalBillId;
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

	public long getSubMeterConsumptionUnit() {
		return subMeterConsumptionUnit;
	}

	public void setSubMeterConsumptionUnit(long subMeterConsumptionUnit) {
		this.subMeterConsumptionUnit = subMeterConsumptionUnit;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	@Override
	public String toString() {
		return "GlobalBill [globalBillId=" + globalBillId + ", monthStamp="
				+ monthStamp + ", meterType=" + meterType + ", readingFrom="
				+ readingFrom + ", readingTill=" + readingTill
				+ ", prevReading=" + prevReading + ", currentReading="
				+ currentReading + ", consumptionUnit=" + consumptionUnit
				+ ", subMeterConsumptionUnit=" + subMeterConsumptionUnit
				+ ", billAmount=" + billAmount + ", createDate=" + createDate
				+ "]";
	}

	
	
	

}
