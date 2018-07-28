package com.pms.controller;

import com.pms.model.Employee;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.service.ProjectService;
import com.pms.service.StoryService;
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

public class StoryControllerTest {

    @InjectMocks
    private StoryController storyController;

    @Mock
    private StoryService storyService;

    @Mock
    private ProjectService projectService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetStoryName() {

        Story story = new Story();
        story.setStoryId(1);
        story.setName("First Story");
        when(storyService.findStory(1)).thenReturn(story);

        String storyName = storyController.getStory(1);
        verify(storyService).findStory(1);

        assertThat(storyName, is("First Story"));
    }

    @Test
    public void testStoryGetStatus() {

        Story story = new Story();
        story.setName("Second Story");
        story.setStatus(Status.DONE);
        when(storyService.findStoryByName("Second Story")).thenReturn(story);

        Status storyStatus = storyController.getStoryStatus("Second Story");
        verify(storyService).findStoryByName("Second Story");

        assertThat(storyStatus, is(Status.DONE));
    }

    @Test
    public void testAddStory() throws ParseException {
        Employee employee= new Employee(1,"Ram", "coder", 46578787, "abc@gmail.com");
        Project project = new Project(1,"spring project","spring",Status.INPROGRESS,1,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        when(projectService.findProject(1)).thenReturn(project);
        Story story= new Story("Database management", "managing", Status.INPROGRESS,3,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"),employee,project);
        when(storyService.saveStory(story)).thenReturn(story);

        Story story1 = storyController.addStory(story,1);
        assertThat(story1, is(story));
    }

    @Test
    public void testUpdateStoryStatus(){
        Story story = new Story();
        when(storyService.findStory(1)).thenReturn(story);
        story.setStatus(Status.QUEUED);

        Status storyStatus = Status.QUEUED;
        when(storyService.updateStatusOfStory(story, Status.QUEUED)).thenReturn(storyStatus);
        Status response = storyController.updateStoryStatus(1,Status.QUEUED);
        assertThat(storyStatus, is(response));
    }

    @Test
    public void testGetEmployeeOfStory() {

        Story story = new Story();
        story.setStoryId(1);
        story.setName("First Story");
        Employee employee= new Employee(1,"Ram", "coder", 46578787, "abc@gmail.com");
        story.setEmployee(employee);
        when(storyService.findStoryByName("First Story")).thenReturn(story);
        String employeeName = story.getEmployee().getName();

        String employeeName1 = storyController.getEmployeeOfStory("First Story");
        assertThat(employeeName, is(employeeName1));
    }
}
