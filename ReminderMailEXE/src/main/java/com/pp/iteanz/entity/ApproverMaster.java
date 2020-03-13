package com.pp.iteanz.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "ApproverMaster")
public class ApproverMaster {
	@Id
	@GeneratedValue
	@Column(name = "id")
	long id;

	@Column(name = "bukrs")
	String bukrs;

	@Column(name = "adrid")
	String adrid;
	
	@Column(name = "begda")
	Date begda;

	@Column(name = "endda")
	Date endda;

	@Column(name = "alevl")
	String alevl;
	
	@Column(name = "crdat")
	Date crdat;
	
	@Column(name = "crtim")
	Time crtim;
	
	@Column(name = "cddat")
	Date cddat;
	
	@Column(name = "cdtim")
	Time cdtim;
	
	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "createdBy")
	UserMaster createdBy;
	
	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "changedBy")
	UserMaster changedBy;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "status")
	private Status status;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getAdrid() {
		return adrid;
	}

	public void setAdrid(String adrid) {
		this.adrid = adrid;
	}

	public Date getBegda() {
		return begda;
	}

	public void setBegda(Date begda) {
		this.begda = begda;
	}

	public Date getEndda() {
		return endda;
	}

	public void setEndda(Date endda) {
		this.endda = endda;
	}

	public String getAlevl() {
		return alevl;
	}

	public void setAlevl(String alevl) {
		this.alevl = alevl;
	}

	public Date getCrdat() {
		return crdat;
	}

	public void setCrdat(Date crdat) {
		this.crdat = crdat;
	}

	public Time getCrtim() {
		return crtim;
	}

	public void setCrtim(Time crtim) {
		this.crtim = crtim;
	}

	public Date getCddat() {
		return cddat;
	}

	public void setCddat(Date cddat) {
		this.cddat = cddat;
	}

	public Time getCdtim() {
		return cdtim;
	}

	public void setCdtim(Time cdtim) {
		this.cdtim = cdtim;
	}

	public UserMaster getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}

	public UserMaster getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(UserMaster changedBy) {
		this.changedBy = changedBy;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
