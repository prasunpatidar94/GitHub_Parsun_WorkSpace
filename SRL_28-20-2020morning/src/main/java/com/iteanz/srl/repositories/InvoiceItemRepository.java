package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.InvoiceItem;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long>{
}
