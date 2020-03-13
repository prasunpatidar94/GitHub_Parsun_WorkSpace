package com.iteanz.srl.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class RoleModel {
	
	@Id
	private Integer id;
	
	private String role;
	
	private String roleflag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleflag() {
		return roleflag;
	}

	public void setRoleflag(String roleflag) {
		this.roleflag = roleflag;
	}

	
	
	
}