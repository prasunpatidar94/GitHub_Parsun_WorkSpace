package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.Product;


public interface ProductRepository extends JpaRepository<Product,Integer> {
}
