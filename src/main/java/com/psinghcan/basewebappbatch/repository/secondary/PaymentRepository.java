package com.psinghcan.basewebappbatch.repository.secondary;

import com.psinghcan.basewebappbatch.model.secondary.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
