package com.rsg.activiti.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.*;

import javax.sql.DataSource;

@Configuration
public class ActivitiConfig {


    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        return processEngineConfiguration;
    }

    @Bean
    public ProcessEngine getProcessEngine(ProcessEngineConfiguration processEngineConfiguration) {
        return processEngineConfiguration.buildProcessEngine();
    }

    @Bean
    public RuntimeService getRuntimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public RepositoryService getRepositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }


}
