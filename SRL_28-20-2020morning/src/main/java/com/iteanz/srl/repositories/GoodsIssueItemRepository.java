package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.GoodsIssueItem;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface GoodsIssueItemRepository extends JpaRepository<GoodsIssueItem,Long>{

}
