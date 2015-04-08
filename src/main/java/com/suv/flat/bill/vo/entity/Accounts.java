package com.suv.flat.bill.vo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class Accounts {
	
	@Id	@GeneratedValue
	@Column(name="owner_id")
	private int ownerId;
	
	@Column(name="owner_name")
	private String ownerName;
	
	@Column(name="flat_number")
	private String flatNumber;
	
	@Column(name="floor")
	private String floor;
	
	@Column(name="is_active")
	private int isActive;
	
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="mod_date")
	private Date modDate;
	
	/*@Column(name="org_id")
	private int orgId;*/
	
	/*@OneToMany(fetch = FetchType.LAZY,mappedBy="account")
	private List<FlatBill> flatBills;
	
	
	public List<FlatBill> getFlatBills() {
		return flatBills;
	}

	public void setFlatBills(List<FlatBill> flatBills) {
		this.flatBills = flatBills;
	}*/

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	/*public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}*/

	@Override
	public String toString() {
		return "Accounts [ownerId=" + ownerId + ", ownerName=" + ownerName
				+ ", flatNumber=" + flatNumber + ", floor=" + floor
				+ ", isActive=" + isActive + ", createDate=" + createDate
				+ ", modDate=" + modDate +  "]";
	}
	
	

}
