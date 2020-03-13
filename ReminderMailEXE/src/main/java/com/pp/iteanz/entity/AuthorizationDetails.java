package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AuthorizationDetails {
	
	@Id
	@GeneratedValue
	private long authDetailsId;
	private String authorizedName;
	private String designation;
	public long getAuthDetailsId() {
		return authDetailsId;
	}
	public void setAuthDetailsId(long authDetailsId) {
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
