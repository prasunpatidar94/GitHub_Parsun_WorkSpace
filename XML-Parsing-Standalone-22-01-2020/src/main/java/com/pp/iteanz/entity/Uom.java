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
@Table(name = "Uom")
public class Uom {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "Uom_seq", allocationSize = 1, name = "Uom_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "meins")
	String meins;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "createdby")
    String createdby;
	
	@Column(name = "changedby")
    String changedby;
	
	@Column(name = "changeddate")
    Date changeddate;
	
	@Column(name = "createddate")
    Date createddate;
	


	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}


}
