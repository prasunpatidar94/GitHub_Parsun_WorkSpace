package com.pp.iteanz.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DocumentType")
public class DocumentType {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "documenttype_seq", allocationSize = 1, name = "documenttype_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "dctyp")
	String dctyp;
	
	@Column(name = "descp")
	String descp;
	
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

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDctyp() {
		return dctyp;
	}

	public void setDctyp(String dctyp) {
		this.dctyp = dctyp;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
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

	
	
	
}
