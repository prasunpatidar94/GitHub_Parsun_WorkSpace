package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.VendorDocument;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel="vendor", path="vendor")
public interface VendorDocumentRepo extends JpaRepository<VendorDocument,Long>{

}
