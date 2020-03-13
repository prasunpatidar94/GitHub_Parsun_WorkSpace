package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AuthorizationDetails {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "authorizationdetails_seq", allocationSize = 1, name = "authorizationdetails_seq")
	@Column(name = "asnsecid")
	private Long authDetailsId;
	private String authorizedName;
	private String designation;
	public Long getAuthDetailsId() {
		return authDetailsId;
	}
	public void setAuthDetailsId(Long authDetailsId) {
		this.authDetailsId = authDetailsId;
	}
	public String getAuthorizedName() {
		return authorizedName;
	}
	public void setAuthorizedName(String authorizedName) {
		this.authorizedName = authorizedName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
}
