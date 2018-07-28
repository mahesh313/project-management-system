package com.pms.integration;

import com.pms.App;
import com.pms.model.Employee;
import com.pms.repository.EmployeeRepository;
import com.pms.service.EmployeeService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class EmployeeIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private List<Integer> employeeIdList = new ArrayList<Integer>();

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testEmployeeIntegration() {
        Employee employee = new Employee("Ram","Manager",943766662L,"ram@gmail.com");
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        employeeIdList.add(savedEmployee.getId());


        Employee returnedEmployee = employeeRepository.findOne(savedEmployee.getId());

        assertThat(savedEmployee.getId(), is(returnedEmployee.getId()));
        assertThat(savedEmployee.getName(), is(returnedEmployee.getName()));
        assertThat(savedEmployee.getDesignation(), is(returnedEmployee.getDesignation()));
        assertThat(savedEmployee.getContact(), is(returnedEmployee.getContact()));
        assertThat(savedEmployee.getEmail(), is(returnedEmployee.getEmail()));
    }

    @After
    public void cleanUp() {
        Iterator<Integer> itr = employeeIdList.iterator();
        while (itr.hasNext()) {
            employeeService.removeEmployee(itr.next());
        }
    }
}
