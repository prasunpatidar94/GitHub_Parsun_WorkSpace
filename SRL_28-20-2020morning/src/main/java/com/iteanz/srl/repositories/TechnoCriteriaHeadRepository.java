package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.TechCriteriaHeader;

@Repository
public interface TechnoCriteriaHeadRepository extends JpaRepository<TechCriteriaHeader,Long>{
	
	

}
