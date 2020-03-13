package com.pp.iteanz.entity;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name ="UserADIntegration")
public class UserADIntegration {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "UserADIntegration_seq", allocationSize = 1, name = "UserADIntegration_s")
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "adrid")
	String adrid;

	@Column(name = "pswrd")
	String pswrd;

	@Column(name = "gegda")
	Date gegda;

	@Column(name = "endda")
	Date endda;

	@Column(name = "adinteg")
	String adinteg;

	@Column(name = "crnam")
	String crnam;

	@Column(name = "crdat")
	Date crdat;

	@Column(name = "crtim")
	Time crtim;

	@Column(name = "cdnam")
	String cdnam;

	@Column(name = "cddat")
	Date cddat;

	@Column(name = "cdtim")
	Time cdtim;

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

	public String getPswrd() {
		return pswrd;
	}

	public void setPswrd(String pswrd) {
		this.pswrd = pswrd;
	}

	public Date getGegda() {
		return gegda;
	}

	public void setGegda(Date gegda) {
		this.gegda = gegda;
	}

	public Date getEndda() {
		return endda;
	}

	public void setEndda(Date endda) {
		this.endda = endda;
	}

	public String getAdinteg() {
		return adinteg;
	}

	public void setAdinteg(String adinteg) {
		this.adinteg = adinteg;
	}

	public String getCrnam() {
		return crnam;
	}

	public void setCrnam(String crnam) {
		this.crnam = crnam;
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

	public String getCdnam() {
		return cdnam;
	}

	public void setCdnam(String cdnam) {
		this.cdnam = cdnam;
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

	
	
	

}
