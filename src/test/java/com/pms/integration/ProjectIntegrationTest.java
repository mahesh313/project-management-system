package com.pms.integration;

import com.pms.App;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class ProjectIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testProjectIntegration() throws ParseException {

        Project project = new Project("PMS", "JAVA", Status.DONE, 4, new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-05"), new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-05"));
        Project savedProject = projectRepository.saveAndFlush(project);

        Project returnedProject = projectRepository.findOne(savedProject.getProjectId());

        assertThat(savedProject.getName(), is(returnedProject.getName()));
        assertThat(savedProject.getTechnologies(), is(returnedProject.getTechnologies()));
        assertThat(savedProject.getStatus(), is(returnedProject.getStatus()));
        assertThat(savedProject.getNumberOfStories(), is(returnedProject.getNumberOfStories()));
        assertThat(savedProject.getStartDate(), is(returnedProject.getStartDate()));
        assertThat(savedProject.getEndDate(), is(returnedProject.getEndDate()));
    }
}
