package com.psinghcan.basewebappbatch.batch.receipt.step1;

import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Step1Config {
    public Step1Config(StepBuilderFactory stepBuilderFactory, DataSource dataSource, JobRepository jobRepository, DealRepository dealRepository, JdbcTemplate jdbcTemplate, ExecutionContext executionContext) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;
        this.dealRepository = dealRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.executionContext = executionContext;
    }


    @Bean
    public Step1Tasklet step1Tasklet(){
        return new Step1Tasklet(dealRepository, jdbcTemplate, executionContext);
    }

    @Bean
    @Qualifier("step1")
    public Step step1() throws Exception{
        return stepBuilderFactory.get("step1")
                .tasklet(step1Tasklet())
                .build();
    }

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final DealRepository dealRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ExecutionContext executionContext;
}
