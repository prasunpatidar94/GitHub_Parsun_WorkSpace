package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.Invite;
import com.iteanz.srl.domain.Invoice;

public interface InviteRepository extends JpaRepository<Invite,Long>{
	public List<Invite> getInviteByUserid(String userid);
}
