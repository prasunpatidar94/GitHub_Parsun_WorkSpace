package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.iteanz.srl.domain.ProposalSummary;

public interface ProposalSummaryRepo extends JpaRepository<ProposalSummary,Long>{
	
	
	public ProposalSummary getProposalSummaryByRfpno(Long rfpno);

}
