package com.pms.service;

import com.pms.exception.ProjectNotFoundException;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.repository.ProjectRepository;
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

public class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StoryService storyService;

    @Mock
    private List<Status> statusList;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindProject(){
        Project project = new Project();
        project.setProjectId(1);
        when(projectRepository.findOne(1)).thenReturn(project);

        Project project1 = projectService.findProject(1);
        assertThat(project1, is(project));
    }

    @Test
    public void testfindProjectByName(){
        Project project = new Project();
        project.setName("Carwale");
        when(projectRepository.findByName("Carwale")).thenReturn(project);

        Project project1 = projectService.findProjectByName("Carwale");
        assertThat(project1, is(project));
    }

    @Test
    public void testSaveProject() throws ParseException {
        Project project= new Project(1,"PMS","Spring", Status.INPROGRESS,1,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        when(projectRepository.saveAndFlush(project)).thenReturn(project);

        Project project1 = projectService.saveProject(project);
        assertThat(project1, is(project));
    }
    @Test
    public void testupdateStatusOfProjectForQUEUED() throws ParseException {
        Project project = new Project(1,"PMS","Spring", null,1,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        List<Story> stories = new ArrayList<Story>();

        Status projectStatus = Status.QUEUED;

        stories.add(new Story("story1", "des1", Status.QUEUED,2, new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"),new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12")));
        stories.add(new Story("story2", "des2", Status.QUEUED,2, new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"),new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12")));

         when(storyService.getStoriesOfProject(1)).thenReturn(stories);

        projectStatus = projectService.updateStatusOfProject(project, projectStatus);
        assertThat(projectStatus,is(Status.QUEUED));
    }

    @Test
    public void testupdateStatusOfProjectForINPROGRESS() throws ParseException {
        Project project = new Project(1,"PMS","Spring", Status.QUEUED,1,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        List<Story> stories = new ArrayList<Story>();

        Status projectStatus = Status.QUEUED;

        stories.add(new Story("story1", "des1", Status.INPROGRESS,2, new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"),new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12")));
        stories.add(new Story("story2", "des2", Status.INPROGRESS,2, new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"),new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12")));

        when(storyService.getStoriesOfProject(1)).thenReturn(stories);

        projectStatus = projectService.updateStatusOfProject(project, projectStatus);
        assertThat(projectStatus,is(Status.INPROGRESS));
    }

    @Test
    public void testupdateStatusOfProjectForDONE() throws ParseException {
        Project project = new Project(1,"PMS","Spring", Status.INPROGRESS,1,new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"), new SimpleDateFormat("yyyy-mm-dd").parse("2017-10-10"));
        List<Story> stories = new ArrayList<Story>();

        stories.add(new Story("story1", "des1", Status.DONE,2, new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"),new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12")));
        stories.add(new Story("story2", "des2", Status.DONE,2, new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12"),new SimpleDateFormat("yyyy-mm-dd").parse("2017-07-12")));

        when(storyService.getStoriesOfProject(1)).thenReturn(stories);

        Status projectStatus = projectService.updateStatusOfProject(project, Status.DONE);
        assertThat(projectStatus,is(Status.DONE));
    }

    @Test(expected = ProjectNotFoundException.class)
    public void testProjectNotFoundException() {
        Project project = new Project();
        project.setProjectId(30);
        when(projectRepository.findOne(30)).thenReturn(null);

        projectService.findProject(30);
    }
}