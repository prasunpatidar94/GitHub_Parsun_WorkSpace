package com.pp.iteanz.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.ASNHeader;

@Repository
public interface ASNHeaderRepository extends JpaRepository<ASNHeader, Serializable>{

	public List<ASNHeader> findByStatus(int status);

	public List<ASNHeader> findByChangeddate(String format);

//	public List<ASNHeader> findByChangeddateAndStatus(String format, int i);
	
	
//	
	@Query("FROM ASNHeader where changeddate=:changeddate and status=:status")
	public List<ASNHeader> findByChangeddateAndStatus(@Param("changeddate") String changeddate, @Param("status") int status);

}
