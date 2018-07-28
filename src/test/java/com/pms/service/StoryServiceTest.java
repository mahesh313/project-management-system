package com.pms.service;

import com.pms.exception.StoryNotFoundException;
import com.pms.model.*;
import com.pms.repository.ProjectRepository;
import com.pms.repository.StoryRepository;
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

public class StoryServiceTest {

    @InjectMocks
    private StoryService storyService;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private ProjectRepository projectRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindStory(){
        Story story = new Story();
        story.setStoryId(1);
        when(storyRepository.findOne(1)).thenReturn(story);

        Story story1 = storyService.findStory(1);
        assertThat(story1, is(story));
    }

    @Test
    public void testfindStoryByName(){
        Story story = new Story();
        story.setName("StoryA");
        when(storyRepository.findByName("StoryA")).thenReturn(story);

        Story story1 = storyService.findStoryByName("StoryA");
        assertThat(story1, is(story));
    }

    @Test
    public void testSaveStory() throws ParseException {
        Story story= new Story("Database management", "managing", Status.INPROGRESS,3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        when(storyRepository.saveAndFlush(story)).thenReturn(story);

        Story story1 = storyService.saveStory(story);
        assertThat(story1, is(story));
    }

    @Test
    public void testGetStoriesOfProject() throws ParseException {
        Project project= new Project();
        project.setProjectId(1);
        when(projectRepository.findOne(1)).thenReturn(project);

        Story story1= new Story("Database management", "managing", Status.INPROGRESS,3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"), new Employee(),project);
        Story story2= new Story("Database configuration", "managing", Status.INPROGRESS,3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"), new Employee(),project);

        List<Story> storyList = new ArrayList<Story>();
        storyList.add(story1);
        storyList.add(story2);

        when(storyRepository.findAllByProject(project)).thenReturn(storyList);

        List<Story> storyList1 = storyService.getStoriesOfProject(1);
        assertThat(storyList1, is(storyList));
    }

    @Test
    public void testupdateStatusOfStoryForQUEUED() throws ParseException {

        Story story= new Story(1,"Database management", "managing", null, 3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"),new Employee(), new Project());
        List<Task> taskList = new ArrayList<Task>();

        taskList.add(new Task("Opendb", "doing", Status.QUEUED, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee()));
        taskList.add(new Task("Closedb", "doing", Status.QUEUED, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-24"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-30"), new Story(), new Employee()));
        when(taskService.getStatusesOfTasksOfStory(1)).thenReturn(taskList);
        Status storyStatus = storyService.updateStatusOfStory(story, Status.QUEUED);
        assertThat(storyStatus,is(Status.QUEUED));
    }

    @Test
    public void testupdateStatusOfStoryForINPROGRESS() throws ParseException {
        Story story= new Story(1,"Database management", "managing", Status.QUEUED, 3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"), new Employee(), new Project());
        List<Task> taskList = new ArrayList<Task>();

        taskList.add(new Task("Opendb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee()));
        taskList.add(new Task("Opendb", "doing", Status.INPROGRESS, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee()));

        when(taskService.getStatusesOfTasksOfStory(1)).thenReturn(taskList);

        Status status = storyService.updateStatusOfStory(story, Status.INPROGRESS);
        assertThat(status,is(Status.INPROGRESS));
    }

    @Test
    public void testupdateStatusOfStoryForDONE() throws ParseException {
        Story story= new Story(1,"Database management", "managing", Status.INPROGRESS, 3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"), new Employee(), new Project());
        List<Task> taskList = new ArrayList<Task>();

        taskList.add(new Task("Opendb", "doing", Status.DONE, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee()));
        taskList.add(new Task("Closeb", "doing", Status.DONE, "HI","No issues", new SimpleDateFormat("yyyy-mm-dd").parse("2017-08-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-09-25"), new Story(), new Employee()));

        when(taskService.getStatusesOfTasksOfStory(1)).thenReturn(taskList);
        Status storyStatus = storyService.updateStatusOfStory(story, Status.DONE);
        assertThat(storyStatus,is(Status.DONE));
    }

    @Test(expected = StoryNotFoundException.class)
    public void testStoryNotFoundException() {
        Story story = new Story();
        story.setStoryId(10);
        when(storyRepository.findOne(10)).thenReturn(null);

        storyService.findStory(10);
    }
}
