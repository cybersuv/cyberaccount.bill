package com.suv.flat.bill.vo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user_type")
public class UserRole {
	
	@Id @GeneratedValue
	@Column(name="user_type_id")
	//@OneToMany(fetch=FetchType.EAGER, mappedBy="userRoleId")
	private int userRoleId;
	
	@Column(name="user_type_name")
	private String userRole;

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", userRole=" + userRole
				+ "]";
	}
	
	
}
