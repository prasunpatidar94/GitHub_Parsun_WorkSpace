package com.iteanz.srl.domain;

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

public class FirstParty {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "firstparty_seq", allocationSize = 1, name = "firstparty_s")
	@Column(name = "firstPartyId")
	private Long firstPartyId;
	private String werks;
	private String name1;// nameOfParty;	
	private String city1;// address;
	private String lgort;//storageLocation;	
	private String emailid;//email;
	private String bukrs; //company code
	//private String butxt;
	
	
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = AuthorizationDetails.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "firstPartyId", referencedColumnName = "firstPartyId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<AuthorizationDetails> authorizationDetails;
	
	

	

	public Long getFirstPartyId() {
		return firstPartyId;
	}

	public void setFirstPartyId(Long firstPartyId) {
		this.firstPartyId = firstPartyId;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public List<AuthorizationDetails> getAuthorizationDetails() {
		return authorizationDetails;
	}

	public void setAuthorizationDetails(List<AuthorizationDetails> authorizationDetails) {
		this.authorizationDetails = authorizationDetails;
	}
}
