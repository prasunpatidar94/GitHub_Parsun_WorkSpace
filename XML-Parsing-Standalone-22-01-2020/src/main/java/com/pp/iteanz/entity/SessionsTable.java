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
@Table(name = "SessionsTable")
public class SessionsTable {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "SessionsTable_seq", allocationSize = 1, name = "SessionsTable_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "adrid")
	String adrid;
	
	
	@Column(name = "sesid")
	long sesid;
	
	@Column(name = "actse")
	String actse;
	
	@Column(name = "sysid")
	String sysid;
	
	@Column(name = "crdat")
	Date crdat;
	
	

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

	public String getActse() {
		return actse;
	}

	public void setActse(String actse) {
		this.actse = actse;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
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



	@Column(name = "crtim")
	Time crtim;

	public long getSesid() {
		return sesid;
	}

	public void setSesid(long sesid) {
		this.sesid = sesid;
	}
	
	
	
	
}
