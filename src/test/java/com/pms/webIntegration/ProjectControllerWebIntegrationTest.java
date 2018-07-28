package com.pms.webIntegration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.App;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest
public class ProjectControllerWebIntegrationTest {
    @Autowired
    ProjectService projectService;

    @Test
    public void testGetProject() throws IOException {
        RestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/get/project/1", String.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(responseEntity.getBody(), is("abc"));

    }

    @Test
    public void testAddProject() throws JsonProcessingException, ParseException {
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        RestTemplate restTemplate = new TestRestTemplate();

        Map<String, Object> requestBody = new HashMap<String, Object>();

        requestBody.put("name", "pms");
        requestBody.put("technologies", "java");
        requestBody.put("status", Status.QUEUED);
        requestBody.put("numberOfStories", 2);
        requestBody.put("startDate", new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-05"));
        requestBody.put("endDate", new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-10"));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Project apiResponse =
                restTemplate.postForObject("http://localhost:8080/addproject/2", httpEntity, Project.class);

        assertNotNull(apiResponse);

        assertEquals("pms", apiResponse.getName());
        assertEquals("java", apiResponse.getTechnologies());
        assertEquals(Status.QUEUED, apiResponse.getStatus());
        assertEquals(2, apiResponse.getNumberOfStories());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-05"), apiResponse.getStartDate());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-10"), apiResponse.getEndDate());

        Project projectFromDb = projectService.findProject(apiResponse.getProjectId());

        assertEquals("pms", projectFromDb.getName());
        assertEquals("java", projectFromDb.getTechnologies());
        assertEquals(Status.QUEUED, projectFromDb.getStatus());
        assertEquals(2, projectFromDb.getNumberOfStories());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-05"), projectFromDb.getStartDate());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-10"), projectFromDb.getEndDate());


    }
}
