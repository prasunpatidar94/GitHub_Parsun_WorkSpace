package com.iteanz.srl.domain;

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
@Table(name = "UserRolesAssignment")
public class UserRolesAssignment {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "UserRolesAssignment_seq", allocationSize = 1, name = "UserRolesAssignment_s")
	@Column(name = "id")
	private Long id;

	
	@ManyToOne(targetEntity = RoleModel.class)
	@JoinColumn(name = "department")
	RoleModel department;

	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "userMasterObject")
	UserMaster userMasterObject;

	@ManyToOne(targetEntity = Status.class)
	@JoinColumn(name = "status")
	Status status;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleModel getDepartment() {
		return department;
	}

	public void setDepartment(RoleModel department) {
		this.department = department;
	}

	public UserMaster getUserMasterObject() {
		return userMasterObject;
	}

	public void setUserMasterObject(UserMaster userMasterObject) {
		this.userMasterObject = userMasterObject;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}	
	
}
