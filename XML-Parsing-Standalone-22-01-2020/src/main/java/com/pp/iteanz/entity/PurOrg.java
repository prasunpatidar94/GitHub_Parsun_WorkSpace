package com.pp.iteanz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PurOrg")
public class PurOrg {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "PurOrg_seq", allocationSize = 1, name = "PurOrg_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "bukrs")//company  code
	public String bukrs;
	
	@Column(name = "butxt")//company  desc
	public String butxt;
	
	@Column(name = "werks")//plant
	public String werks;

	@Column(name = "ekorg")//prurchase organisation
	public String ekorg;
	
	@Column(name = "ekgrp")//purchase group
	public String ekgrp;
	
	@Column(name = "ekmam") //purchase group descrption
	public String ekmam;
	
	@Column(name = "ekotx") //purchase organisation desc
	public String ekotx;
	
	@Column(name = "createdby")
    String createdby;
	
	@Column(name = "changedby")
    String changedby;
	
	@Column(name = "changeddate")
    Date changeddate;
	
	@Column(name = "createddate")
    Date createddate;
	
	
	@Column(name = "inactive")
	public int inactive;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getEkorg() {
		return ekorg;
	}

	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}

	public String getEkmam() {
		return ekmam;
	}

	public void setEkmam(String ekmam) {
		this.ekmam = ekmam;
	}

	public String getEkotx() {
		return ekotx;
	}

	public void setEkotx(String ekotx) {
		this.ekotx = ekotx;
	}

	public int getInactive() {
		return inactive;
	}

	public void setInactive(int inactive) {
		this.inactive = inactive;
	}
	

	public String getEkgrp() {
		return ekgrp;
	}

	public void setEkgrp(String ekgrp) {
		this.ekgrp = ekgrp;
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

	public Date getChangeddate() {
		return changeddate;
	}

	public void setChangeddate(Date changeddate) {
		this.changeddate = changeddate;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getButxt() {
		return butxt;
	}

	public void setButxt(String butxt) {
		this.butxt = butxt;
	}

}
