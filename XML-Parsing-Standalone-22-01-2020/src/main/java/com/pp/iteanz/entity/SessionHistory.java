package com.pp.iteanz.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SessionHistory")
public class SessionHistory {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "SessionHistory_seq", allocationSize = 1, name = "SessionHistory_s")
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "adrid")
	String adrid;
	
	@Column(name = "sysid")
	String sysid;
	
	@Column(name = "logindt")
	Date logindt;
	
	@Column(name = "logintm")
	Time logintm;
	
	@Column(name = "logotdt")
	Date logotdt;
	
	@Column(name = "logottm")
	Time logottm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdrid() {
		return adrid;
	}

	public void setAdrid(String adrid) {
		this.adrid = adrid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public Date getLogindt() {
		return logindt;
	}

	public void setLogindt(Date logindt) {
		this.logindt = logindt;
	}

	public Time getLogintm() {
		return logintm;
	}

	public void setLogintm(Time logintm) {
		this.logintm = logintm;
	}

	public Date getLogotdt() {
		return logotdt;
	}

	public void setLogotdt(Date logotdt) {
		this.logotdt = logotdt;
	}

	public Time getLogottm() {
		return logottm;
	}

	public void setLogottm(Time logottm) {
		this.logottm = logottm;
	}


}
