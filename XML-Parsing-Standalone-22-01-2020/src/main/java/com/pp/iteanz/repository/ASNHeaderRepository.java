package com.pp.iteanz.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.ASNHeader;

@Repository
public interface ASNHeaderRepository extends JpaRepository<ASNHeader, Serializable> {

	//public List<ASNHeader> getByActstatus(String actstatus);

}
