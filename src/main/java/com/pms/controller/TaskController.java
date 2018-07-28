package com.pms.controller;

import com.pms.model.Employee;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.model.Task;
import com.pms.service.EmployeeService;
import com.pms.service.StoryService;
import com.pms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    StoryService storyService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "get/task/{id}", method = RequestMethod.GET)
    public String getTask(@PathVariable Integer id) {
        Task task = taskService.findTask(id);
        return task.getName();
    }

    @RequestMapping(value = "get/task/{name}/status", method = RequestMethod.GET)
    public Status getTaskStatus(@PathVariable String name) {
        Task task = taskService.findTaskByName(name);
        return task.getStatus();
    }

    @RequestMapping(value = "get/task/{name}/employee", method = RequestMethod.GET)
    public String getEmployeeForTask(@PathVariable String name) {
        Task task = taskService.findTaskByName(name);
        Employee employee = task.getEmployee();
        return employee.getName();
    }

    @RequestMapping(value = "get/task/{id}/issues", method = RequestMethod.GET)
    public String getIssues(@PathVariable Integer id) {
        Task task = taskService.findTask(id);
        return task.getIssues();
    }


    @RequestMapping(value = "get/tasks/story/{id}", method = RequestMethod.GET)
    public List<Task> getAllTasksByStory(@PathVariable Integer id) {
        Story story = storyService.findStory(id);
        List<Task> taskList = taskService.findAllTasksByStory(story);
        return taskList;
    }

    @RequestMapping(value = "update/task/{id}/status/{status}", method = RequestMethod.PUT)
    public Status updateTaskStatus(@PathVariable Integer id, @PathVariable Status status) {
        Task task = taskService.findTask(id);
        task.setStatus(status);
        taskService.saveTask(task);
        return task.getStatus();
    }

    @RequestMapping(value = "/add/task/{storyId}/{employeeId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Task addTask(@RequestBody Task task, @PathVariable Integer storyId, @PathVariable Integer employeeId) {
        Story story = storyService.findStory(storyId);
        Employee employee = employeeService.findEmployee(employeeId);
        task.setStory(story);
        task.setEmployee(employee);
        return taskService.saveTask(task);
    }

    @RequestMapping(value = "/add/task/{taskId}/comment", consumes = TEXT_PLAIN_VALUE, produces = TEXT_PLAIN_VALUE, method = RequestMethod.POST)
    public String addComments(@RequestBody String comment, @PathVariable Integer taskId) {
        Task task = taskService.findTask(taskId);
        task.setComments(comment);
        taskService.saveTask(task);
        return task.getComments();
    }
}
