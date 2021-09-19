package com.psinghcan.basewebappbatch;

import com.psinghcan.basewebappbatch.service.LoadDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class BaseWebappBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseWebappBatchApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(LoadDataService loadDataService){
        return args -> {
            loadDataService.init();
        };
    }
}
