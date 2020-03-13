package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.ASNHeader;
import com.iteanz.srl.domain.ASNItem;

public interface ASNHeaderRepository extends JpaRepository<ASNHeader,Long>{

}
