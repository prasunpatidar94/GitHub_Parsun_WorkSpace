package com.pp.iteanz.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.GatePass;
@Repository
@Transactional
public interface GatePassRepository extends JpaRepository<GatePass, Serializable>{


}
