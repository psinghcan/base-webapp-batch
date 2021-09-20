package com.psinghcan.basewebappbatch.repository.primary;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    public List<Deal> findAllByStatus(int status);


    @Transactional
    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Deal d set d.status = :newstatus where d.status = :status")
//    public int updateRecords(@Param("newstatus") int newstatus, @Param("status") int status);
    @Query(value = "UPDATE deal set status = ?1 where status = ?2", nativeQuery = true)
    public int updateRecords(int newstatus, int status);
}
