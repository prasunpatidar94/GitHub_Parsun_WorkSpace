package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity

public class Invite {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "invite_seq", allocationSize = 1, name = "invite_s")
	@Column(name = "inviteId")
	private Long inviteId;
	//private String sectionDepartment;
	//private String user1;	
	private String remarks;
	private String status;	
	private String actionDate;
	//private String emailid;
	private String userid;//lifnr
	private String name;
	private String mobile;
	private String email;
	private String department;
	private String position;
	private String mailstatus;
	private String changeddate;
	private String createddate;
	private String createdBy;
	private String changeddBy;
	
	
	private long contractId;


	public Long getInviteId() {
		return inviteId;
	}


	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getActionDate() {
		return actionDate;
	}


	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getMailstatus() {
		return mailstatus;
	}


	public void setMailstatus(String mailstatus) {
		this.mailstatus = mailstatus;
	}


	public String getChangeddate() {
		return changeddate;
	}


	public void setChangeddate(String changeddate) {
		this.changeddate = changeddate;
	}


	public String getCreateddate() {
		return createddate;
	}


	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getChangeddBy() {
		return changeddBy;
	}


	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}


	public long getContractId() {
		return contractId;
	}


	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	
	
	
}
