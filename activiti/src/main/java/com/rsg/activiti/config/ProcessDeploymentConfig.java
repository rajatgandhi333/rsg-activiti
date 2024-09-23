package com.rsg.activiti.config;

import com.rsg.activiti.util.Constants;
import jakarta.annotation.PostConstruct;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.rsg.activiti.util.Constants.FILE_NOT_FOUND;

@Configuration
public class ProcessDeploymentConfig {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @PostConstruct
    public void deployProcess() throws IOException {
        Resource resource = new ClassPathResource(Constants.CLASSPATH);
        if (!resource.exists()) {
            throw new FileNotFoundException(FILE_NOT_FOUND + " " + resource.getFile().getAbsolutePath());
        } else {
            repositoryService.createDeployment()
                    .addInputStream(Constants.BPMN_FILE_NAME, resource.getInputStream())
                    .deploy();
        }
    }
}
