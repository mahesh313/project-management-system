package com.pms.service;

import com.pms.exception.EmployeeNotFoundException;
import com.pms.model.Employee;
import com.pms.repository.EmployeeRepository;
import com.pms.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveEmployee() {
        Employee employee= new Employee(1,"Suresh", "coder", 49878787, "harsh@gmail.com");
        when(employeeRepository.saveAndFlush(employee)).thenReturn(employee);

        Employee employee1 = employeeService.save(employee);
        assertThat(employee1, is(employee));
    }

    @Test
    public void testFindEmployee(){
        Employee employee = new Employee();
        employee.setId(1);
        when(employeeRepository.findOne(1)).thenReturn(employee);

        Employee employee1 = employeeService.findEmployee(1);
        assertThat(employee1, is(employee));

    }

    @Test
    public void findEmployeeByName(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Rajesh");
        when(employeeRepository.findByName("Rajesh")).thenReturn(employee);

        Employee employee1 = employeeService.findEmployeeByName("Rajesh");
        assertThat(employee1, is(employee));
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testEmployeeNotFoundException() {
        Employee employee = new Employee();
        employee.setId(40);
        when(employeeRepository.findOne(40)).thenReturn(null);

        employeeService.findEmployee(40);
    }
}
