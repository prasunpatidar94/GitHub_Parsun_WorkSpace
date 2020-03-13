package com.pp.iteanz.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
public class RFPMessage {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPMessage_seq", allocationSize = 1, name = "RFPMessage_s")
	@Column(name = "id")
	private Long id;

	@Column(name = "rfpno")
	String rfpno;
	
	@Column(name = "version")
	String version;
	
	@Column(name = "lifnr")
	String lifnr;
	
	@Column(name = "slnum")
	int slnum;
	
	@Column(name = "message")
	String message;
	
	@Column(name = "createdby")
	String createdby;
	
	@Column(name = "changedby")
    String changedby;
	
	@Column(name = "changeddate")
	String changeddate;
	
	public String getChangeddate() {
		return changeddate;
	}

	public void setChangeddate(String changeddate) {
		this.changeddate = changeddate;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	@Column(name = "createddate")
	String createddate;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRfpno() {
		return rfpno;
	}

	public void setRfpno(String rfpno) {
		this.rfpno = rfpno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public int getSlnum() {
		return slnum;
	}

	public void setSlnum(int slnum) {
		this.slnum = slnum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	
}
