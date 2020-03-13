package com.pp.iteanz.entity;

import java.sql.Date;

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
@Table(name = "ApprovalMatrix")
public class ApprovalMatrix {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "approvalMatrix_seq", allocationSize = 1, name = "approvalMatrix_s")
	@Column(name = "id")
	private Long id;
	@Column(name = "plant")
	String plant;
	
	@Column(name = "lgort")
	String lgort;

	@Column(name = "matspoc")
	String matspoc;

	@Column(name = "vmAprv")
	String vmAprv;

	@Column(name = "labhead")
	String labhead;

	@Column(name = "buhead")
	String buhead;

	@Column(name = "techhead")
	String techhead;

	@Column(name = "prochead")
	String prochead;

	@Column(name = "localacc")
	String localacc;

	@Column(name = "logistics_spoc")
	String logistics_spoc;

	@Column(name = "logistics_head")
	String logistics_head;

	@Column(name = "mdmteam")
	String mdmteam;

	@Column(name = "rfpcreator")
	String rfpcreator;

	@Column(name = "crdat")
	Date crdat;

	@Column(name = "cddat")
	Date cddat;

	@Column(name = "createdBy")
	String createdBy;

	@Column(name = "changeddBy")
	String changeddBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "status")
	private Status status;




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getMatspoc() {
		return matspoc;
	}

	public void setMatspoc(String matspoc) {
		this.matspoc = matspoc;
	}

	public String getLogistics_spoc() {
		return logistics_spoc;
	}

	public void setLogistics_spoc(String logistics_spoc) {
		this.logistics_spoc = logistics_spoc;
	}

	public String getLogistics_head() {
		return logistics_head;
	}

	public void setLogistics_head(String logistics_head) {
		this.logistics_head = logistics_head;
	}

	public String getLabhead() {
		return labhead;
	}

	public void setLabhead(String labhead) {
		this.labhead = labhead;
	}

	public String getBuhead() {
		return buhead;
	}

	public void setBuhead(String buhead) {
		this.buhead = buhead;
	}

	public String getTechhead() {
		return techhead;
	}

	public void setTechhead(String techhead) {
		this.techhead = techhead;
	}

	public String getProchead() {
		return prochead;
	}

	public void setProchead(String prochead) {
		this.prochead = prochead;
	}

	public String getMdmteam() {
		return mdmteam;
	}

	public void setMdmteam(String mdmteam) {
		this.mdmteam = mdmteam;
	}

	public String getRfpcreator() {
		return rfpcreator;
	}

	public void setRfpcreator(String rfpcreator) {
		this.rfpcreator = rfpcreator;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getLocalacc() {
		return localacc;
	}

	public void setLocalacc(String localacc) {
		this.localacc = localacc;
	}

	public String getVmAprv() {
		return vmAprv;
	}

	public void setVmAprv(String vmAprv) {
		this.vmAprv = vmAprv;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

}
