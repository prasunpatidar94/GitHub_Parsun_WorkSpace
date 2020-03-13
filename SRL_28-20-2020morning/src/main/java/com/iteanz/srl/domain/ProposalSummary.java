package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
public class ProposalSummary {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "ProposalSummary_seq", allocationSize = 1, name = "ProposalSummary_s")
	@Column(name = "propsumid")
	private Long propsumid;
	private long rfpno;
	private double preis;//private int price;
	private String suppliername;//private String suppliername;
	private long proposalno;//
	private String comments;
	private int ratingsscore;
	private String supplierstatus;
	private String changeddate;
	private String createddate;
	private String createdBy;
	private String changeddBy;
	private String sessionid;
	private String lifnr;
	private String status;
	private String approvedBy;
	private String approvedDate;
	private  String position;
	public Long getPropsumid() {
		return propsumid;
	}
	public void setPropsumid(Long propsumid) {
		this.propsumid = propsumid;
	}
	public long getRfpno() {
		return rfpno;
	}
	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public long getProposalno() {
		return proposalno;
	}
	public void setProposalno(long proposalno) {
		this.proposalno = proposalno;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getRatingsscore() {
		return ratingsscore;
	}
	public void setRatingsscore(int ratingsscore) {
		this.ratingsscore = ratingsscore;
	}
	public String getSupplierstatus() {
		return supplierstatus;
	}
	public void setSupplierstatus(String supplierstatus) {
		this.supplierstatus = supplierstatus;
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
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	

	
	
}
