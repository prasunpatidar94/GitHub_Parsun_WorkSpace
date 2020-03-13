package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.ContactInfo;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel="vendor", path="vendor")
public interface ContactInfoRepo extends JpaRepository<ContactInfo,Long>{

}
