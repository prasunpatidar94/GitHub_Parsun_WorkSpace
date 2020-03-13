package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.POItemDetails;

@Repository
public interface POItemDetailsRepository extends JpaRepository<POItemDetails, Serializable> {

	// public List<POItemDetails> getPOItemDetailsByCp_fk(Long cp_fk);

	

}
