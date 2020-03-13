package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.RFPInvitedVendors;

@Repository
public interface RFPInvitedVendorRepo extends JpaRepository<RFPInvitedVendors,Long>{
	public List<RFPInvitedVendors> getRFPInviteByLifnr(String lifnr);

}
