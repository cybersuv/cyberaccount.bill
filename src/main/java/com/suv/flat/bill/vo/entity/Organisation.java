package com.suv.flat.bill.vo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="organisations")
public class Organisation {
	
	@Id @GeneratedValue
	@Column(name="org_id")
	private long orgId;
	
	@Column(name="org_name")
	private String orgName;
	
	@Column(name="org_mnemonic")
	private String orgMnemonic;

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgMnemonic() {
		return orgMnemonic;
	}

	public void setOrgMnemonic(String orgMnemonic) {
		this.orgMnemonic = orgMnemonic;
	}

	@Override
	public String toString() {
		return "Organisation [orgId=" + orgId + ", orgName=" + orgName
				+ ", orgMnemonic=" + orgMnemonic + "]";
	}
	
	

}
