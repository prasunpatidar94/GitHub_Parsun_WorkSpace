package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Plant {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "Plant_seq", allocationSize = 1, name = "Plant_s")

	@Column(name = "id")
	int id;

	@Column(name = "bukrs")//company code
	public String bukrs;
	
	@Column(name = "ekorg")//purchase organisation
	public String ekorg;
	
	@Column(name = "werks")// plant
	public String werks;
	
	@Column(name = "lgort")//storage location
	public String lgort;
	
	@Column(name = "city1") //address
	public String city1;
	
	@Column(name = "name1")// plant name
	public String name1;	
	
	@Column(name = "emailid")// emaailid
	public String emailid;
	
	@Column(name = "crdat")
	public String crdat;
	
	@Column(name = "cddat")
	public String cddat;
	
	@Column(name = "createdBy")
	String createdBy;
	
	@Column(name = "changeddBy")
	String changeddBy;
	
	@Column(name = "lgobe")
	String lgobe;
	
	public String getLgobe() {
		return lgobe;
	}

	public void setLgobe(String lgobe) {
		this.lgobe = lgobe;
	}

	

	public String getCrdat() {
		return crdat;
	}

	public void setCrdat(String crdat) {
		this.crdat = crdat;
	}

	public String getCddat() {
		return cddat;
	}

	public void setCddat(String cddat) {
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

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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


	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}
	

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}	
}