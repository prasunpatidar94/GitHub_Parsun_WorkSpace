package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.GRNItem;

public interface GRNItemRepository extends JpaRepository<GRNItem, Serializable> {
	
	public List<GRNItem> getGRNItemByEbelnAndEbelp(Long ebeln, Long ebelp);
}
