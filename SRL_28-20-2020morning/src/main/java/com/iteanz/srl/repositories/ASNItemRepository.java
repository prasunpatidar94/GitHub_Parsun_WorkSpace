package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.ASNItem;

public interface ASNItemRepository extends JpaRepository<ASNItem, Long> {
	public List<ASNItem> getASNItemByEbelnAndEbelp(Long ebeln, Long ebelp);
}
