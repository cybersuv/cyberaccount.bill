package com.suv.flat.bill.vo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id @GeneratedValue
	@Column(name="user_id")
	private int userid;
	
	@Column(name="user_name")
	private String user_name;
	
	@Column(name="user_login")
	private String user_login;
	
	@Column(name="user_password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name="user_role_id", referencedColumnName="user_type_id")
	private UserRole userRole;
	
	@Column(name="user_create_date")
	private Date create_date;
	
	@ManyToOne
	@JoinColumn(name="org_id")
	private Organisation organisation;
	
	public User(){
		this.userid=-1;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_login() {
		return user_login;
	}
	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", user_name=" + user_name
				+ ", user_login=" + user_login + ", password=" + password
				+ ", userRole=" + userRole + ", create_date=" + create_date
				+ ", organisation=" + organisation + "]";
	}
	
	
	
}
