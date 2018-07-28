package com.pms.service;

import com.pms.exception.TaskNotFoundException;
import com.pms.model.*;
import com.pms.repository.StoryRepository;
import com.pms.repository.TaskRepository;
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
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private StoryRepository storyRepository;

    @Before
    public void init() { MockitoAnnotations.initMocks(this); }

    @Test
    public void testFindTask(){
        Task task = new Task();
        task.setTaskId(1);
        when(taskRepository.findOne(1)).thenReturn(task);

        Task task1 = taskService.findTask(1);
        assertThat(task1, is(task));
    }

    @Test
    public void testfindTaskByName(){
        Task task = new Task();
        task.setName("Updatedb");
        when(taskRepository.findByName("Updatedb")).thenReturn(task);

        Task task1 = taskService.findTaskByName("Updatedb");
        assertThat(task1, is(task));
    }

    @Test
    public void testSaveTask() throws ParseException {
        Task task = new Task("Opendb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee());
        when(taskRepository.saveAndFlush(task)).thenReturn(task);

        Task task1 = taskService.saveTask(task);
        assertThat(task1, is(task));
    }

    @Test
    public void testFindAllTasksByStory() throws ParseException {

        Story story= new Story(1,"Database management", "managing", null, 3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"),new Employee(), new Project());
        Task task1 = new Task("Opendb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), story, new Employee());
        Task task2 = new Task("Updatedb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), story, new Employee());

        List<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);
        when(taskRepository.findAllByStory(story)).thenReturn(taskList);

        List<Task> taskList1 = taskService.findAllTasksByStory(story);
        assertThat(taskList1, is(taskList));
    }

    @Test
    public void testgetStatusesOfTasksOfStory() throws ParseException{
        Story story = new Story();
        story.setStoryId(1);
        when(storyRepository.findOne(1)).thenReturn(story);
        List<Task> taskList = new ArrayList<Task>();
        Task task1 = new Task("Opendb", "completed", Status.DONE, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee());
        Task task2 = new Task("Updatedb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee());
        taskList.add(task1);
        taskList.add(task2);
        when(taskRepository.findAllByStory(story)).thenReturn(taskList);

        List<Task> task3 = taskService.getStatusesOfTasksOfStory(1);
        assertThat(task3, is(taskList));
    }

    @Test(expected = TaskNotFoundException.class)
    public void testTaskNotFoundException() {
        Task task = new Task();
        task.setTaskId(40);
        when(taskRepository.findOne(40)).thenReturn(null);

        taskService.findTask(40);
    }
}
