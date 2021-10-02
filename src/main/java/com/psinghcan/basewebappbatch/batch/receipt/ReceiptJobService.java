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
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String file1 = "output/file-1-" + date + ".json";
        String file2 = "output/file-2-" + date + ".json";
        executionContext.put("file1", file1);
        executionContext.put("file2", file2);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString("name", "receipt-deal-batch-job")
                .addString("file1", file1)
                .addString("file2", file2)
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
