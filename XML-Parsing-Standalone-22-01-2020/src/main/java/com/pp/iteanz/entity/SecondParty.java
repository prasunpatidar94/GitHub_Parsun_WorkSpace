package com.pp.iteanz.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)

public class SecondParty {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "SecondParty_seq", allocationSize = 1, name = "SecondParty_s")
	@Column(name = "secondPartyId")
	private Long secondPartyId;
	//private String partner;
	private String lifnr;
	private String name1;//nameOfPartner;	
	private String address;
	private String mobnumber;//contact;	
	private String emailid;//email;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = AuthorizationDetails.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "secondPartyId", referencedColumnName = "secondPartyId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<AuthorizationDetails> authorizationDetails;

	public Long getSecondPartyId() {
		return secondPartyId;
	}

	public void setSecondPartyId(Long secondPartyId) {
		this.secondPartyId = secondPartyId;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobnumber() {
		return mobnumber;
	}

	public void setMobnumber(String mobnumber) {
		this.mobnumber = mobnumber;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public List<AuthorizationDetails> getAuthorizationDetails() {
		return authorizationDetails;
	}

	public void setAuthorizationDetails(List<AuthorizationDetails> authorizationDetails) {
		this.authorizationDetails = authorizationDetails;
	}


}
