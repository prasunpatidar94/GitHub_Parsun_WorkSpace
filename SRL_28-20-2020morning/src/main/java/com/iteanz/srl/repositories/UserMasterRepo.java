package com.iteanz.srl.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.UserMaster;

@Repository
public interface UserMasterRepo extends JpaRepository<UserMaster, Serializable> {
	
	public UserMaster findBySapId(String string);

	public UserMaster findByAdrid(String adrid);


}
