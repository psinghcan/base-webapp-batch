package com.psinghcan.basewebappbatch.batch.receipt.step1;

import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class Step1Tasklet implements Tasklet {
    public Step1Tasklet(DealRepository dealRepository, JdbcTemplate jdbcTemplate, ExecutionContext executionContext) {
        this.dealRepository = dealRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.executionContext = executionContext;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("start the tasklet");
//        dealRepository.updateRecords(21, 20);
        updateRecords();
        System.out.println("finishing the tasklet: STEP 1 completed");
        return RepeatStatus.FINISHED;
    }

    private void updateRecords(){
        String updateQuery = "update deal set status = 21 where status = 20";
        jdbcTemplate.execute(updateQuery);
    }
    private final DealRepository dealRepository;

    private final JdbcTemplate jdbcTemplate;
    private final ExecutionContext executionContext;
}
