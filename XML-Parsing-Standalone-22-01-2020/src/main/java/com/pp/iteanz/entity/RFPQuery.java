package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
public class RFPQuery {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPQuery_seq", allocationSize = 1, name = "RFPQuery_s")
	@Column(name = "rfpqueryId")
	private long rfpqueryId;

	private long rfpno;
	private long proposalno;
	private String mailbody;
	private String createdBy;
	private String createdDate;
	public long getRfpqueryId() {
		return rfpqueryId;
	}
	public void setRfpqueryId(long rfpqueryId) {
		this.rfpqueryId = rfpqueryId;
	}
	public long getRfpno() {
		return rfpno;
	}
	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}
	public long getProposalno() {
		return proposalno;
	}
	public void setProposalno(long proposalno) {
		this.proposalno = proposalno;
	}
	public String getMailbody() {
		return mailbody;
	}
	public void setMailbody(String mailbody) {
		this.mailbody = mailbody;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
