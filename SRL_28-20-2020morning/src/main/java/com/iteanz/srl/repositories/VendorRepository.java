package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.Vendor;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel="vendor", path="vendor")
public interface VendorRepository extends JpaRepository<Vendor,Long>{

	public List<Vendor> getVendorByCreatedBy(String createdBy);

	public Vendor findByCreatedBy(String reqid);
	
	public Vendor findByVid(long vid);
	
}
