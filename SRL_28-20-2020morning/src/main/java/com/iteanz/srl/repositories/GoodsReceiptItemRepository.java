package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.GoodsReceiptItem;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface GoodsReceiptItemRepository extends JpaRepository<GoodsReceiptItem,Long>{

}
