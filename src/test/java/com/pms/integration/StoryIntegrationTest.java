package com.pms.integration;

import com.pms.App;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.service.StoryService;
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
public class StoryIntegrationTest {

    @Autowired
    public StoryService storyService;

    @Test
    public void testStoryIntegration() throws ParseException {

        Story story = new Story("Chandamama","This is the first story", Status.DONE, 5, new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-05"), new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-05"));
        
        Story savedStory = storyService.saveStory(story);

        Story returnedStory = storyService.findStory(savedStory.getStoryId());

        assertThat(savedStory.getName(), is(returnedStory.getName()));
        assertThat(savedStory.getDescription(), is(returnedStory.getDescription()));
        assertThat(savedStory.getStatus(), is(returnedStory.getStatus()));
        assertThat(savedStory.getNumberOfTasks(), is(returnedStory.getNumberOfTasks()));
        assertThat(savedStory.getStartDate(), is(returnedStory.getStartDate()));
        assertThat(savedStory.getEndDate(), is(returnedStory.getEndDate()));
    }
}
