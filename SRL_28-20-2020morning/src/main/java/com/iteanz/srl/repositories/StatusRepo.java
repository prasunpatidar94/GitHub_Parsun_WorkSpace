package com.iteanz.srl.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.Status;

@Repository
public interface StatusRepo extends JpaRepository<Status, Serializable> {

	


}
