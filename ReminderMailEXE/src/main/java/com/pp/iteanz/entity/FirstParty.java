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
public class FirstParty {
	
	@Id
	@GeneratedValue
	private long firstPartyId;
	private String plant;
	private String nameOfParty;	
	private String address;
	private String storageLocation;	
	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = AuthorizationDetails.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "firstPartyId", referencedColumnName = "firstPartyId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<AuthorizationDetails> authorizationDetails;

	public long getFirstPartyId() {
		return firstPartyId;
	}

	public void setFirstPartyId(long firstPartyId) {
		this.firstPartyId = firstPartyId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getNameOfParty() {
		return nameOfParty;
	}

	public void setNameOfParty(String nameOfParty) {
		this.nameOfParty = nameOfParty;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
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
