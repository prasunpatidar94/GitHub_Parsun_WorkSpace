package com.iteanz.srl.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.GoodsIssue;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface GoodsIssueRepository extends JpaRepository<GoodsIssue,Long>{
	public List<GoodsIssue> getGoodsIssuesByCurrentState(String currentState);
}
