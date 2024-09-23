package com.rsg.activiti.controller;

import com.rsg.activiti.util.Constants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Constants.ACTIVITI_API)
public class ActivitiController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @GetMapping(Constants.START)
    public String startProcess() {
        runtimeService.startProcessInstanceByKey(Constants.BPMN_FILE_KEY);
        return "Process Started";
    }

    /**
     * To start a process based on the given key
     *
     * @param key
     * @return
     */

    @PostMapping(Constants.START_PROCESS)
    public String startProcess(@RequestParam("key") String key) {
        runtimeService.startProcessInstanceByKey(key);
        return "Process Started";
    }


    /**
     * To fetch the list of tasks per assignee
     *
     * @param assignee
     * @return
     */
    @GetMapping(Constants.TASK)
    public List<Task> ggetTasks(@RequestParam("assignee") String assignee) {
        TaskService taskService = processEngine.getTaskService();
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    /**
     * To complete the given task
     *
     * @param taskId
     * @return
     */
    @PostMapping(Constants.COMPLETE_TASK)
    public String completeTasks(@RequestParam("taskId") String taskId) {
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId);
        return "Task COmplete";
    }

    @PostMapping(Constants.DEPLOY_bpmn)
    public ResponseEntity<String> deployBPMN(@RequestParam("file") MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            } else {
                //deploy ythe file
                repositoryService.createDeployment()
                        .addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream())
                        .deploy();
                return ResponseEntity.ok("BPMN file deployed successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deployment Failed " + e.getMessage());
        }
    }


}
