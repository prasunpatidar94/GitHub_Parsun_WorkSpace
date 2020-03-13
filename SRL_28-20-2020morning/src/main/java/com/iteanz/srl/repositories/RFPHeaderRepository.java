package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.RFPHeader;
import com.iteanz.srl.domain.TechnoRatings;
import com.iteanz.srl.domain.VPHeader;

@Repository
public interface RFPHeaderRepository extends JpaRepository<RFPHeader,Long>{
	
	public List<RFPHeader> getRFPByCreatedby(String createdby);
	
//	 @Query("FROM TechnoRatings where cp_fk=:cp_fk and lifnr=:lifnr and createdBy=:createdBy")
//		public List<TechnoRatings> getRatingListByRfpVendCreatedBy(@Param("cp_fk") long rfpno,@Param("lifnr") String lifnr, @Param("createdBy") String createdBy);
//	  
	
	 @Query("FROM RFPHeader where rfpno=:rfpno and rfpmode=:rfpmode")
	public RFPHeader findByRfpnoAndRfpmode(@Param("rfpno")long rfpno,@Param("rfpmode") String rfpmode);
	
	
	//public List<RFPHeader> getRFPByVpno(Long proposalno);
	//public List<RFPHeader> getRFPByInviteLifnr(String  rfpno, String sessionid);
	//public VPHeader getVPByLifnrAndRfpno(String lifnr,long rfpno);
}
