package com.psinghcan.basewebappbatch.repository.primary;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
}
