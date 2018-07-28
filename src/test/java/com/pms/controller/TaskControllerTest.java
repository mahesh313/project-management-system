package com.pms.controller;

import com.pms.controller.TaskController;
import com.pms.model.*;
import com.pms.service.EmployeeService;
import com.pms.service.StoryService;
import com.pms.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private StoryService storyService;

    @Mock
    private TaskService taskService;

    @Mock
    private EmployeeService employeeService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTaskGetName() {

        Task task = new Task();
        task.setTaskId(1);
        task.setName("First task");
        when(taskService.findTask(1)).thenReturn(task);
        String taskName = taskController.getTask(1);
        verify(taskService).findTask(1);
        assertThat(taskName, is("First task"));
    }

    @Test
    public void testGetIssues(){
        Task task = new Task();
        task.setTaskId(1);
        task.setIssues("First task not completed");
        when(taskService.findTask(1)).thenReturn(task);
        String issues = taskController.getIssues(1);
        verify(taskService).findTask(1);
        assertThat(issues, is("First task not completed"));
    }

    @Test
    public void testGetEmployeeForTask(){
        Employee employee = new Employee("Ram", "coder", 46578787, "abc@gmail.com");
        Task task = new Task();
        task.setTaskId(1);
        task.setName("Second task");
        task.setEmployee(employee);
        when(taskService.findTaskByName("Second task")).thenReturn(task);
        String employeeName = taskController.getEmployeeForTask("Second task");
        assertThat(employeeName,is(task.getEmployee().getName()));
    }

    @Test
    public void testGetAllTasksByStory() throws ParseException {
        Employee employee = new Employee(7,"Ram", "coder", 46578787, "abc@gmail.com");
        Story story = new Story(1,"gsahj",null,Status.QUEUED,2, new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-05"), new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-10"),employee,new Project());
        Task taskOne = new Task("ab","jgejcged", Status.QUEUED,"dwfe", "hghjd",new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-05"), new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-10"),story,employee);
        Task taskTwo = new Task("nbn","jgejcged", Status.QUEUED,"vhgfjh","hjhg", new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-05"), new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-10"),story,employee);
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(taskOne);
        taskList.add(taskTwo);
        when(storyService.findStory(story.getStoryId())).thenReturn(story);
        when(taskService.findAllTasksByStory(story)).thenReturn(taskList);
        List<Task> tasks = taskController.getAllTasksByStory(1);
        assertThat(taskList,is(tasks));
    }

    @Test
    public void testGetStatus(){
        Task task = new Task();
        task.setName("Updatedb");
        task.setStatus(Status.INPROGRESS);
        when(taskService.findTaskByName("Updatedb")).thenReturn(task);
        Status taskStatus = taskController.getTaskStatus("Updatedb");
        assertThat(taskStatus, is(Status.INPROGRESS));
    }

    @Test
    public void testUpdateTaskStatus(){
        Task task = new Task();
        task.setStatus(Status.QUEUED);
        when(taskService.findTask(1)).thenReturn(task);
        Status taskStatus = taskController.updateTaskStatus(1, Status.INPROGRESS);
        assertThat(taskStatus, is(Status.INPROGRESS));
    }

    @Test
    public void testAddTask() throws ParseException {
        Employee employee= new Employee("Ram", "coder", 46578787, "abc@gmail.com");
        Task task = new Task("Opendb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee());
        Story story= new Story("Database management", "managing", Status.INPROGRESS,3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"),employee, new Project());
        when(taskService.saveTask(task)).thenReturn(task);
        when(storyService.findStory(1)).thenReturn(story);
        when(employeeService.findEmployee(2)).thenReturn(employee);

        Task task1 = taskController.addTask(task,1,2);
        assertThat(task1, is(task));
    }

    @Test
    public void testAddComments() throws ParseException{
        Task task = new Task(1,"Opendb", "doing", Status.INPROGRESS, null,"No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee());
        when(taskService.findTask(1)).thenReturn(task);
        task.setComments("Database not working");
        when(taskService.saveTask(task)).thenReturn(task);
        Integer taskId = task.getTaskId();
        String taskComments = taskController.addComments("Database not working", taskId);
        assertThat("Database not working", is(taskComments));
    }

}
