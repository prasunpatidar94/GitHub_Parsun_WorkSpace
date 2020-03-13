package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.GRNHeader;
import com.iteanz.srl.domain.RFPInvitedVendors;

@Repository
public interface GRNHeaderRepository extends JpaRepository<GRNHeader,Long>{
	public GRNHeader getGRNByLifnr(String lifnr);
	//GRNHeader getOne(String lifnr);

}
