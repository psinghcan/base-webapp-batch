package com.psinghcan.basewebappbatch.repository.secondary;

import com.psinghcan.basewebappbatch.model.secondary.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
