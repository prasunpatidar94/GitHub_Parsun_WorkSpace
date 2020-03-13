package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.ProposalSummary;

@Repository
public interface ProposalSummaryRepository extends JpaRepository<ProposalSummary, Serializable> {

	public List<ProposalSummary> findByLifnr(String reqid);
	

	public ProposalSummary findByRfpno(long reqid);

	
	
	
	

}
