package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pp.iteanz.entity.GRNHeader;

public interface GRNHeaderRepository extends JpaRepository<GRNHeader, Serializable> {

//	 public GRNHeader findGrn(long grn);

//	public GRNHeader findId(@Param("grn")long grn);

}
