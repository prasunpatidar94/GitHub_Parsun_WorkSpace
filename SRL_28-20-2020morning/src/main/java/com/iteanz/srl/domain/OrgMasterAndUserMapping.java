package com.iteanz.srl.domain;

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
@Table(name = "OrgMasterAndUserMapping")
public class OrgMasterAndUserMapping {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "OrgMasterAndUserMapping_seq", allocationSize = 1, name = "OrgMasterAndUserMapping_s")
	@Column(name = "id")
	private Long id;

	@Column(name = "ardid")
	String ardid;
	
	@Column(name = "bukrs")
	String bukrs;
	
	@Column(name = "vkorg")
	String vkorg;
	
	@Column(name = "ekorg")
	String ekorg;
	
	@Column(name = "dctyp")
	String dctyp;
	
	@Column(name = "plant")
	String plant;
	
	@Column(name = "kunnr")
	String kunnr;
	
	@Column(name = "crdat")
	Date crdat;
	
	@Column(name = "crtim")
	String crtim;
	
	@Column(name = "cddat")
	Date cddat;
	
	@Column(name = "cdtim")
	Time cdtim;
	
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

	public String getArdid() {
		return ardid;
	}

	public void setArdid(String ardid) {
		this.ardid = ardid;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getVkorg() {
		return vkorg;
	}

	public void setVkorg(String vkorg) {
		this.vkorg = vkorg;
	}

	public String getEkorg() {
		return ekorg;
	}

	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}

	public String getDctyp() {
		return dctyp;
	}

	public void setDctyp(String dctyp) {
		this.dctyp = dctyp;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public Date getCrdat() {
		return crdat;
	}

	public void setCrdat(Date crdat) {
		this.crdat = crdat;
	}

	public String getCrtim() {
		return crtim;
	}

	public void setCrtim(String crtim) {
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
