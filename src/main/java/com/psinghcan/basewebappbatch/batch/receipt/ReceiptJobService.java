package com.psinghcan.basewebappbatch.batch.receipt;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReceiptJobService {


    public ReceiptJobService(JobLauncher jobLauncher, @Qualifier("receipt-batch-job") Job job, ExecutionContext executionContext) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.executionContext = executionContext;
    }

    public void startReceiptBatchJob(){
        String name = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
        executionContext.put("file_1", "file_1_" + name);
        executionContext.put("file_2", "file_2_" + name);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString("name", "receipt-deal-batch-job")
                .addString("file_1_", "file_1_" + name)
                .addString("file_2_", "file_2_" + name)
                .addDate("time", new Date());
        try{
            JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
        } catch (Exception e){
            System.out.println("errors " + e);
        }
    }

    private final JobLauncher jobLauncher;
    private final Job job;;
    private final ExecutionContext executionContext;
}
