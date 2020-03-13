package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pp.iteanz.entity.Plant;

public interface PlantRepository extends JpaRepository<Plant, Serializable> {

//	public  Plant findByWerks(String werks);

	public Plant findByWerksAndLgort(String werks, String lgort);
	
	

}
