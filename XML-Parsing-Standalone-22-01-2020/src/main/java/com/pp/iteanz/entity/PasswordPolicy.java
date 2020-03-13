package com.pp.iteanz.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "PasswordPolicy")
public class PasswordPolicy {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "PasswordPolicy_seq", allocationSize = 1, name = "PasswordPolicy_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "begda")
	Date begda;

	@Column(name = "endda")
	Date endda;
	
	@Column(name = "crdat")
	Date crdat;
	
	@Column(name = "crtim")
	Time crtim;
	
	@Column(name = "cddat")
	Date cddat;
	
	@Column(name = "cdtim")
	Time cdtim;
	
	@Column(name = "pwdht")
	int pwdht;
	
	@Column(name = "upchk")
	String upchk;
	
	@Column(name = "lwck")
	String lwck;
	
	@Column(name = "splck")
	String splck;
	
	@Column(name = "lghigh")
	int lghigh;
	
	@Column(name = "lglow")
	int lglow;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "status")
	private Status status;
	
	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "createdBy")
	UserMaster createdBy;
	
	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "changedBy")
	UserMaster changedBy;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public int getPwdht() {
		return pwdht;
	}

	public void setPwdht(int pwdht) {
		this.pwdht = pwdht;
	}

	public String getUpchk() {
		return upchk;
	}

	public void setUpchk(String upchk) {
		this.upchk = upchk;
	}

	public String getLwck() {
		return lwck;
	}

	public void setLwck(String lwck) {
		this.lwck = lwck;
	}

	public String getSplck() {
		return splck;
	}

	public void setSplck(String splck) {
		this.splck = splck;
	}

	public int getLghigh() {
		return lghigh;
	}

	public void setLghigh(int lghigh) {
		this.lghigh = lghigh;
	}

	public int getLglow() {
		return lglow;
	}

	public void setLglow(int lglow) {
		this.lglow = lglow;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
	
	
	
}
