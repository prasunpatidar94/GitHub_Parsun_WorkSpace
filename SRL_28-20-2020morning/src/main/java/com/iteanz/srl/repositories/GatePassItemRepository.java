package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.GatePassItem;

public interface GatePassItemRepository extends JpaRepository<GatePassItem, Serializable> {
	public List<GatePassItem> getGatePassItemByEbelnAndEbelp(Long ebeln, Long ebelp);

}
