package com.pp.iteanz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "ApprovalLog")
public class ApprovalLog {
	@Id
	@GeneratedValue
	@Column(name = "id")
	int id;

	@Column(name = "req_No")
	long reqNo;

	@Column(name = "subProcess")
	String subProcess;

	@Column(name = "initiator")
	String initiator;

	@Column(name = "aprv")
	String aprv;

	@Column(name = "aprvlevel")
	String aprvlevel;
	
	@Column(name = "lgort")
	private String lgort;// StorageLocation

	@Column(name = "werks")
	String werks;

	@Column(name = "approvedBy")
	String approvedBy;

	@Column(name = "approvedId")
	String approvedId;

	@Temporal(TemporalType.DATE)
	@Column(name = "approvedDate")
	Date approvedDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "approvedTime")
	Date approvedTime;

	@Column(name = "mailstatus")
	int mailstatus;

	@Column(name = "aprvstatus")
	int aprvstatus;

	@Column(name = "remark")
	String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "status")
	private Status status;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "appid")
	private WorkflowConfig appid;

	@Temporal(TemporalType.DATE)
	@Column(name = "crdat")
	Date crdat;

	@Temporal(TemporalType.DATE)
	@Column(name = "cddat")
	Date cddat;

	@Column(name = "createdBy")
	String createdBy;

	@Column(name = "changedBy")
	String changedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getReqNo() {
		return reqNo;
	}

	public void setReqNo(long reqNo) {
		this.reqNo = reqNo;
	}

	public String getSubProcess() {
		return subProcess;
	}

	public void setSubProcess(String subProcess) {
		this.subProcess = subProcess;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getAprv() {
		return aprv;
	}

	public void setAprv(String aprv) {
		this.aprv = aprv;
	}

	public String getAprvlevel() {
		return aprvlevel;
	}

	public void setAprvlevel(String aprvlevel) {
		this.aprvlevel = aprvlevel;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(String approvedId) {
		this.approvedId = approvedId;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
	}

	public int getMailstatus() {
		return mailstatus;
	}

	public void setMailstatus(int mailstatus) {
		this.mailstatus = mailstatus;
	}

	public int getAprvstatus() {
		return aprvstatus;
	}

	public void setAprvstatus(int aprvstatus) {
		this.aprvstatus = aprvstatus;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public WorkflowConfig getAppid() {
		return appid;
	}

	public void setAppid(WorkflowConfig appid) {
		this.appid = appid;
	}

	public Date getCrdat() {
		return crdat;
	}

	public void setCrdat(Date crdat) {
		this.crdat = crdat;
	}

	public Date getCddat() {
		return cddat;
	}

	public void setCddat(Date cddat) {
		this.cddat = cddat;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	
}
