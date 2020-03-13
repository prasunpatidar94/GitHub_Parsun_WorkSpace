package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.GRNHeader;
import com.iteanz.srl.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
	public Payment getPaymentByLifnr(String lifnr);
}
