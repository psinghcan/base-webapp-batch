package com.psinghcan.basewebappbatch.batch.receipt.step3;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Step3Config {
    public Step3Config(StepBuilderFactory stepBuilderFactory, ExecutionContext executionContext) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.executionContext = executionContext;
    }


    @Bean
    public Step3Tasklet step3Tasklet(){
        return new Step3Tasklet(executionContext);
    }

    @Bean
    @Qualifier("step3")
    public Step step3() throws Exception{
        return stepBuilderFactory.get("step3")
                .tasklet(step3Tasklet())
                .build();
    }

    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContext executionContext;
}
