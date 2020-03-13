package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.UserMaster;

@Repository
public interface UserMasterRepo extends JpaRepository<UserMaster, Serializable> {


	public UserMaster findBySapId(String string);

	public UserMaster findByAdrid(String vendorNo);
	
}
