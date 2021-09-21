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
    public ReceiptBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                              DataSource dataSource, JobRepository jobRepository, DealRepository dealRepository,
                              @Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;
        this.dealRepository = dealRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.executionContext = new ExecutionContext();
    }

    @Bean
    public Step1Tasklet step1Tasklet(){
        return new Step1Tasklet(dealRepository, jdbcTemplate, executionContext);
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
    @Qualifier("step2Writer")
    public ItemWriter<Deal> step2Writer()
    {
        FlatFileItemWriter<Deal> writer = new FlatFileItemWriter<>();
        Resource resource = new FileSystemResource("output/file_1.csv");
        writer.setResource(resource);
        writer.setLineAggregator(new DelimitedLineAggregator<Deal>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Deal>() {
                    {
                        setNames(new String[] { "id", "office", "number", "amount1", "amount2", "amount3", "status", "processStatus", "paymentNumber", "receiptNumber" });
                    }
                });
            }
        });
        return writer;
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
    @Qualifier("step2")
    public Step step2(ReceiptDealItemReader reader,
                     ReceiptItemProcessor processor,
                     @Qualifier("step2Writer") ItemWriter writer){
        TaskletStep step = stepBuilderFactory.get("receipt")
                .<Deal, Deal>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
        return step;
    }

    @Bean
    @Qualifier("step1")
    public Step step1() throws Exception{
        return stepBuilderFactory.get("step1")
                .tasklet(step1Tasklet())
                .build();
    }

    @Bean
    public ExecutionContext executionContext(){
        return executionContext;
    }

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final DealRepository dealRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ExecutionContext executionContext;
}
