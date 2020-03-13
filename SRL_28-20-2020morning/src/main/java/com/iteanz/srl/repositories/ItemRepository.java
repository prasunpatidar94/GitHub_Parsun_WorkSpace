package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.Item;

public interface ItemRepository extends JpaRepository<Item,Long>{

}
