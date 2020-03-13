package com.pp.iteanz.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.Status;



@Repository
public interface StatusRepo extends JpaRepository<Status, Serializable> {

	Optional<Status> findByStatusId(int status);

	

	


}
