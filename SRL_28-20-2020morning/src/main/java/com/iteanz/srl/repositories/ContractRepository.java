package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.Contract;
import com.iteanz.srl.domain.PurchaseOrder;
import com.iteanz.srl.domain.RFPHeader;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long>{
	public List<Contract> getContractsByContractType(String coontractType);
	public List<Contract> getContractBySessionid(String sessionid);
	
	public List<Contract> getContractByCreatedBy(String createdBy);
}
