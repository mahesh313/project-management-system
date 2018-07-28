package com.pms.controller;

import com.pms.model.Employee;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.service.EmployeeService;
import com.pms.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private EmployeeService employeeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProjectGetName() {

        Project project = new Project();
        project.setProjectId(1);
        project.setName("PMS");
        when(projectService.findProject(1)).thenReturn(project);

        String projectName = projectController.getProject(1);
        verify(projectService).findProject(1);

        assertThat(projectName, is("PMS"));
    }

    @Test
    public void testGetProjectStatus(){
        Project project = new Project();
        project.setName("CarCare");
        project.setStatus(Status.INPROGRESS);
        when(projectService.findProjectByName("CarCare")).thenReturn(project);

        Status projectStatus = projectController.getProjectStatus("CarCare");
        assertThat(projectStatus, is(Status.INPROGRESS));
    }

    @Test
    public void testGetProjectManager(){
        Employee employee = new Employee("Mahesh", "manager", 465788787, "mahesh@gmail.com");
        Project project = new Project();
        project.setProjectId(2);
        project.setName("VIMS");
        project.setEmployee(employee);
        when(projectService.findProject(2)).thenReturn(project);

        String projectManager = projectController.getManager(2);
        assertThat(projectManager, is("Mahesh"));
    }

    @Test
    public void testAddProject() throws ParseException {
        Employee employee= new Employee(1,"Ram", "coder", 46578787, "abc@gmail.com");
        Project project = new Project(1,"PMS","Spring",Status.INPROGRESS,1,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        when(employeeService.findEmployee(1)).thenReturn(employee);
        when(projectService.saveProject(project)).thenReturn(project);

        Project project1 = projectController.addProject(project,1);
        assertThat(project1, is(project));
    }

    @Test
    public void testUpdateProjectStatus(){
        Project project = new Project();
        project.setStatus(Status.QUEUED);
        when(projectService.findProject(1)).thenReturn(project);
        Status status = Status.QUEUED;
        when(projectService.updateStatusOfProject(project, status)).thenReturn(status);

        Status projectStatus = projectController.updateProjectStatus(1,status);
        assertThat(projectStatus, is(status));
    }

}
