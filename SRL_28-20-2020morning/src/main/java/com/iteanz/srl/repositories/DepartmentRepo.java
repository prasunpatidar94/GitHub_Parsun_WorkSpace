package com.iteanz.srl.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Serializable> {

	String findByDepartment(String sender);
	


}
