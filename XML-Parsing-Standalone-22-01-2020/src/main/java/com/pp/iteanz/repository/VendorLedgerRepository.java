package com.pp.iteanz.repository;

import java.io.Serializable;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.VendorLedger;

@Repository
@Transactional
public interface VendorLedgerRepository extends JpaRepository<VendorLedger, Serializable> {

//	public  Optional<VendorLedger> findByLifnr(String lifnr);

	public Optional<VendorLedger> findByBelnr(String long1);

}
