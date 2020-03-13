package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.RFPQuery;

public interface RFPQueryRepository extends JpaRepository<RFPQuery,Long>{
	
	public List<RFPQuery> getRFPQueryByRfpno(long rfpno);
	
	public List<RFPQuery> getRFPQueryByRfpnoAndProposalno(long rfpno, long proposalno);

}
