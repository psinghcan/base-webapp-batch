package com.psinghcan.basewebappbatch.repository.primary;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findAllByStatus(int status);
}
