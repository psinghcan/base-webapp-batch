package com.psinghcan.basewebappbatch.batch.receipt;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class ReceiptBatchConfig {
    public ReceiptBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                              DataSource dataSource, JobRepository jobRepository, DealRepository dealRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;
        this.dealRepository = dealRepository;
    }

    @Bean
    public ReceiptDealItemReader receiptDealItemReader(){
        return new ReceiptDealItemReader(dealRepository);
    }

    @Bean
    public ReceiptItemProcessor receiptItemProcessor(){
        return new ReceiptItemProcessor();
    }

    @Bean
    public ReceiptItemWriter receiptItemWriter(){
        return new ReceiptItemWriter();
    }

    @Bean
    @Qualifier("receipt-batch-job")
    public Job job(Step step){
        Job job = jobBuilderFactory.get("receipt-job")
                .flow(step)
                .end().build();
        return job;
    }

    @Bean
    public Step step(ReceiptDealItemReader reader,
                     ReceiptItemProcessor processor,
                     ReceiptItemWriter writer){
        TaskletStep step = stepBuilderFactory.get("receipt")
                .<Deal, Deal>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
        return step;
    }

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final DealRepository dealRepository;
}
