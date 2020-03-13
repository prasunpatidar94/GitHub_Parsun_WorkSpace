package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ContactInfo {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "contactinfo_seq", allocationSize = 1, name = "contactinfo_s")
	@Column(name = "cid")
	private Long cid;

	private String addType;
	private String address;
	private String telephone;
	private String email;
	private String fax;
	private String escContact;
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEscContact() {
		return escContact;
	}
	public void setEscContact(String escContact) {
		this.escContact = escContact;
	}

	
	
}
