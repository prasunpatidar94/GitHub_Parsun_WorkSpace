package com.pp.iteanz.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
public class SecondParty {
	
	@Id
	@GeneratedValue
	private long secondPartyId;
	private String partner;
	private String nameOfPartner;	
	private String address;
	private String contact;	
	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = AuthorizationDetails.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "secondPartyId", referencedColumnName = "secondPartyId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<AuthorizationDetails> authorizationDetails;

	public long getSecondPartyId() {
		return secondPartyId;
	}

	public void setSecondPartyId(long secondPartyId) {
		this.secondPartyId = secondPartyId;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getNameOfPartner() {
		return nameOfPartner;
	}

	public void setNameOfPartner(String nameOfPartner) {
		this.nameOfPartner = nameOfPartner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AuthorizationDetails> getAuthorizationDetails() {
		return authorizationDetails;
	}

	public void setAuthorizationDetails(List<AuthorizationDetails> authorizationDetails) {
		this.authorizationDetails = authorizationDetails;
	}
}
