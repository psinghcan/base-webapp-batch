package com.psinghcan.basewebappbatch.batch.receipt.step2;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.Step;
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
public class Step2Config {

    public Step2Config(StepBuilderFactory stepBuilderFactory, DataSource dataSource, JobRepository jobRepository, DealRepository dealRepository, JdbcTemplate jdbcTemplate, ExecutionContext executionContext) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;
        this.dealRepository = dealRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.executionContext = executionContext;
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
    @Qualifier("receiptDealItemReader")
    public ReceiptDealItemReader receiptDealItemReader(){
        return new ReceiptDealItemReader(dealRepository);
    }

    @Bean
    @Qualifier("receiptItemProcessor")
    public ReceiptItemProcessor receiptItemProcessor(){
        return new ReceiptItemProcessor();
    }

    @Bean
    @Qualifier("step2Writer")
    public ItemWriter<Deal> step2Writer()
    {
        FlatFileItemWriter<Deal> writer = new FlatFileItemWriter<>();
        Resource resource = new FileSystemResource("output/current/file-1.csv");
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

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final DealRepository dealRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ExecutionContext executionContext;
}
