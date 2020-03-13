package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Serializable>{

}
