package com.pms.webIntegration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.App;
import com.pms.model.Employee;
import com.pms.repository.EmployeeRepository;
import com.pms.service.EmployeeService;
import net.sf.ehcache.CacheManager;
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
import java.util.Collections;
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
public class EmployeeControllerWebIntegrationTest {



    @Autowired
    EmployeeService employeeService;

    @Test
    public void testGetEmployee() throws IOException {
        RestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/get/employee/91", String.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(responseEntity.getBody(), is("Manali"));

    }

    @Test
    public void testGetEmployeeContact() throws IOException {
        RestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<Long> responseEntity = restTemplate.getForEntity("http://localhost:8080/get/employee/contact/Ram", Long.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));


        assertThat(responseEntity.getBody(), is(943766662L));

    }

    @Test
    public void testAddEmployee() throws JsonProcessingException{
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        RestTemplate restTemplate = new TestRestTemplate();

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("contact", 321L);
        requestBody.put("designation", "manager");
        requestBody.put("email", "authormail");
        requestBody.put("name", "Rohan");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Employee apiResponse =
                restTemplate.postForObject("http://localhost:8080/add/employee", httpEntity, Employee.class);
        System.out.println(apiResponse.getName());
        assertNotNull(apiResponse);
        System.out.println(apiResponse.getContact());


        assertEquals(321L, apiResponse.getContact());
        assertEquals("manager", apiResponse.getDesignation());
        assertEquals("authormail", apiResponse.getEmail());
        assertEquals("Rohan", apiResponse.getName());

        Employee employeeFromDb = employeeService.findEmployee(apiResponse.getId());

        assertEquals(321L, employeeFromDb.getContact());
        assertEquals("manager", employeeFromDb.getDesignation());
        assertEquals("authormail", employeeFromDb.getEmail());
        assertEquals("Rohan", employeeFromDb.getName());

    }
}
