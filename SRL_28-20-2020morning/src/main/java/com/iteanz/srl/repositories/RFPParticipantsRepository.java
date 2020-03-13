package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.RFPParticipant;

public interface RFPParticipantsRepository extends JpaRepository<RFPParticipant,Long>{
	public List<RFPParticipant> getRFPParticipantByUserid(String userid);
}
