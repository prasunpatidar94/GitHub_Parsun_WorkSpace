package com.pp.iteanz.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Storage")
public class Storage {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "Storage_seq", allocationSize = 1, name = "Storage_s")
	@Column(name = "id")
	Long id;

	@Column(name = "werks")
	String werks;
	
	@Column(name = "lgort")
	String lgort;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "inactive")
	int inactive;
	
	@Column(name = "crdat")
	Date crdat;
	
	@Column(name = "cddat")
	Date cddat;
	
	@Column(name = "createdBy")
	String createdBy;
	
	@Column(name = "changeddBy")
	String changeddBy;

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInactive() {
		return inactive;
	}

	public void setInactive(int inactive) {
		this.inactive = inactive;
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

	public String getChangeddBy() {
		return changeddBy;
	}

	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}


}
