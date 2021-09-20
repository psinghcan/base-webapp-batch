package com.psinghcan.basewebappbatch.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "com.psinghcan.basewebappbatch.repository.secondary",
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "secondaryTransactionManager"
)

public class SecondaryDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix="secondary.datasource")
    public DataSource secondaryDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("secondary.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("secondary.datasource.url"));
        dataSource.setUsername(env.getProperty("secondary.datasource.username"));
        dataSource.setPassword(env.getProperty("secondary.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager()
    {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondaryDataSource());
        em.setPackagesToScan(new String[] { "com.psinghcan.basewebappbatch.model.secondary" });
        em.setPersistenceUnitName("secondary_datasource");

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",  env.getProperty("secondary.datasource.hibernate.ddl-auto"));
        properties.put("hibernate.database-platform", env.getProperty("secondary.datasource.database-platform"));
        properties.put("hibernate.dialect", env.getProperty("secondary.datasource.database-platform"));
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                secondaryEntityManager().getObject());
        return transactionManager;
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(){
        return new JdbcTemplate(secondaryDataSource());
    }

    public SecondaryDatabaseConfig(Environment env) {
        this.env = env;
    }

    private final Environment env;
}
