package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.VPItem;

public interface VPItemRepo extends JpaRepository<VPItem,Long>{
	//public List<VPItem> getVPItemByCp_fk(long cp_fk);

}
