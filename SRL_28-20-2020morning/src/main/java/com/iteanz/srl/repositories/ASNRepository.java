package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.ASNHeader;
import com.iteanz.srl.domain.VPHeader;

@Repository
public interface ASNRepository extends JpaRepository<ASNHeader,Long>{
	public List<ASNHeader> getASNByEbeln(Long ebeln);
	
}
