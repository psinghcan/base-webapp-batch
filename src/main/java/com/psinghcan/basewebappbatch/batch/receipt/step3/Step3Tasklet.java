package com.psinghcan.basewebappbatch.batch.receipt.step3;

import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;

public class Step3Tasklet implements Tasklet {
    public Step3Tasklet(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        System.out.println("start the tasklet step 3");
        try{
            moveFiles();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finishing the tasklet: STEP 3 completed");
        return RepeatStatus.FINISHED;
    }

    private void moveFiles() throws Exception{
        Resource resource = new FileSystemResource("output/current/file-1.json");
        File file1 = resource.getFile();
        String _file1 = executionContext.getString("file1");
        File file1_target = new FileSystemResource(_file1).getFile();
        file1.renameTo(file1_target);
    }

    private final ExecutionContext executionContext;
}
