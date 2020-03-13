package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class RFPInvitedVendors {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPInvitedVendors_seq", allocationSize = 1, name = "RFPInvitedVendors_s")
	@Column(name = "invid")
	private Long invid;
	
	private String version;
	private String lifnr;
	private String name;
	private String contactno;
	private String contactperson;
	private String tempflag;
	private String sellifnr;
	private String mailstatus;
	private String status;
	private String comments;
	private String emailid;
	private String createdby;
	private String changedby;
	private String changeddate;
	private String sessionid;
	private String additionalEmail;

	
	private long cp_fk;
	public long getCp_fk() {
		return cp_fk;
	}
	public void setCp_fk(long cp_fk) {
		this.cp_fk = cp_fk;
	}
	private String createddate;
	//private String proposalno;
	private String ack;
	//private long proposalno;
	
	
	public Long getInvid() {
		return invid;
	}
	public String getAdditionalEmail() {
		return additionalEmail;
	}
	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}
	public void setInvid(Long invid) {
		this.invid = invid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public String getTempflag() {
		return tempflag;
	}
	public void setTempflag(String tempflag) {
		this.tempflag = tempflag;
	}
	public String getSellifnr() {
		return sellifnr;
	}
	public void setSellifnr(String sellifnr) {
		this.sellifnr = sellifnr;
	}
	public String getMailstatus() {
		return mailstatus;
	}
	public void setMailstatus(String mailstatus) {
		this.mailstatus = mailstatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getChangedby() {
		return changedby;
	}
	public void setChangedby(String changedby) {
		this.changedby = changedby;
	}
	public String getChangeddate() {
		return changeddate;
	}
	public void setChangeddate(String changeddate) {
		this.changeddate = changeddate;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}

	
}

