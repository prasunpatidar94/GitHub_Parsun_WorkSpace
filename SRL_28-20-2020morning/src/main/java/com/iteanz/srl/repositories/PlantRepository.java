package com.iteanz.srl.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.Plant;

public interface PlantRepository extends JpaRepository<Plant,Long>{
	
	public List<Plant> getDistinctWerksByBukrsAndEkorgAndWerks(String bukrs, String ekorg, String werks);

	public Set<Plant>  findByWerks(String werks);

	
	/* @Query("SELECT DISTINCT a.city FROM Address a")
	  List<String> findDistinctCity();
	
	
	public List<Plant> findDistinctWerks();*/

}
