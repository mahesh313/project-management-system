package com.pms.controller;

import com.pms.model.Employee;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.service.EmployeeService;
import com.pms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "get/project/{id}", method = RequestMethod.GET)
    public String getProject(@PathVariable Integer id){
        Project project = projectService.findProject(id);
        return project.getName();
    }

    @RequestMapping(value = "get/project/{name}/status", method = RequestMethod.GET)
    public Status getProjectStatus(@PathVariable String name){
        Project project = projectService.findProjectByName(name);
        return project.getStatus();
    }



    @RequestMapping(value = "update/project/{id}/status/{status}", method = RequestMethod.PUT)
    public Status updateProjectStatus(@PathVariable Integer id, @PathVariable Status status){
        Project project = projectService.findProject(id);
        return projectService.updateStatusOfProject(project, status);
    }

    @RequestMapping(value = "get/project/{id}/manager", method = RequestMethod.GET)
    public String getManager(@PathVariable Integer id) {
        Project project = projectService.findProject(id);
        Employee employee = project.getEmployee();
        String name = employee.getName();
        return name;
    }

    @RequestMapping(value = "/addproject/{employeeId}",consumes = APPLICATION_JSON_VALUE ,produces = APPLICATION_JSON_VALUE ,method = RequestMethod.POST)
    public Project addProject(@RequestBody Project project, @PathVariable Integer employeeId) {
        Employee employee = employeeService.findEmployee(employeeId);
        project.setEmployee(employee);
        return projectService.saveProject(project);
    }
}
