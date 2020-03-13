package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Invite {
	
	@Id
	@GeneratedValue
	private long inviteId;
	private String status;	
	
	/*private String sectionDepartment;
	private String user1;	
	*/
	private String remarks;
	private String actionDate;
	private String eunam;
	private String mailstatus;
	private String email;
	private String contact;
	private String userDesignation;
	private String department;
	
	
	public long getInviteId() {
		return inviteId;
	}
	public void setInviteId(long inviteId) {
		this.inviteId = inviteId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
/*	public String getSectionDepartment() {
		return sectionDepartment;
	}
	public void setSectionDepartment(String sectionDepartment) {
		this.sectionDepartment = sectionDepartment;
	}
	
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	*/
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getEunam() {
		return eunam;
	}
	public void setEunam(String eunam) {
		this.eunam = eunam;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUserDesignation() {
		return userDesignation;
	}
	public void setUserDesignation(String userDesignation) {
		this.userDesignation = userDesignation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMailstatus() {
		return mailstatus;
	}
	public void setMailstatus(String mailstatus) {
		this.mailstatus = mailstatus;
	}
	
}

