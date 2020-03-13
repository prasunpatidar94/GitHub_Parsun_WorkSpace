package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.RFPHeader;
import com.iteanz.srl.domain.VPHeader;

@Repository
public interface VPHeaderRepository extends JpaRepository<VPHeader,Long>{
	public List<VPHeader> getVPByRfpno(Long rfpno);
	
	public VPHeader getVPByLifnr(String lifnr);
	public VPHeader getVPByLifnrAndRfpno(String lifnr,long rfpno);
	public List<VPHeader> getVPByCreatedby(String createdby);
}

