package com.psinghcan.basewebappbatch.batch.receipt;

import com.psinghcan.basewebappbatch.batch.receipt.step1.Step1Tasklet;
import com.psinghcan.basewebappbatch.batch.receipt.step2.ReceiptDealItemReader;
import com.psinghcan.basewebappbatch.batch.receipt.step2.ReceiptItemProcessor;
import com.psinghcan.basewebappbatch.model.primary.Deal;
import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class ReceiptBatchConfig {
    public ReceiptBatchConfig(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.executionContext = new ExecutionContext();
    }

    @Bean
    @Qualifier("receipt-batch-job")
    public Job job(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2){
        Job job = jobBuilderFactory.get("receipt-job")
                .flow(step1)
                .next(step2)
                .end().build();
        return job;
    }

    @Bean
    public ExecutionContext executionContext(){
        return executionContext;
    }

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContext executionContext;
}
