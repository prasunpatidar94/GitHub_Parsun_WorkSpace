package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pp.iteanz.entity.GRNItem;

public interface GRNItemRepository extends JpaRepository<GRNItem, Serializable> {

}
