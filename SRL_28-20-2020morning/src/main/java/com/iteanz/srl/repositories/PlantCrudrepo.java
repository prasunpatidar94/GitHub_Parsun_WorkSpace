package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import com.iteanz.srl.domain.Plant;

public interface PlantCrudrepo extends CrudRepository<Plant, String> {
	  
	@Query("SELECT DISTINCT p.werks  FROM Plant p")
	  List<Plant> findDistinctWerks();
	// p.id, p.bukrs, p.ekorg, p.lgort, p.city1, 
	
	@Query("SELECT DISTINCT p.werks FROM Plant p")
	  List<Plant> ByBukrsAndEkorgAndWerks();
	
	/*@Query("SELECT DISTINCT p.werks FROM Plant")
	List<Plant> findDistinctWerksByBukrsAndEkorgAndWerks(String bukrs, String ekorg, String werks);
*/
	
	
}