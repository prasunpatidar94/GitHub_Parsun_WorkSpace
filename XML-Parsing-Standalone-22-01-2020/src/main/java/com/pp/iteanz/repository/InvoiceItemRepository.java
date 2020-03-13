package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.InvoiceItem;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Serializable>{

}
