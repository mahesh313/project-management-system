package com.pms.controller;

import com.pms.controller.EmployeeController;
import com.pms.model.Employee;
import com.pms.repository.EmployeeRepository;
import com.pms.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEmployeeGetName() {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Vishal");
        when(employeeService.findEmployee(1)).thenReturn(employee);

        String employeeName = employeeController.getEmployee(1);
        verify(employeeService).findEmployee(1);

        assertThat(employeeName, is("Vishal"));
    }

    @Test
    public void testEmployeeGetContact() {

        Employee employee = new Employee();
        employee.setName("Vishal");
        employee.setContact(912421654);
        when(employeeService.findEmployeeByName("Vishal")).thenReturn(employee);

        Long employeeContact = employeeController.getEmployeeContact("Vishal");
        verify(employeeService).findEmployeeByName("Vishal");

        assertThat(employeeContact, is(912421654L));
    }

    @Test
    public void testAddEmployee() throws ParseException {
        Employee employee= new Employee(1,"Ram", "coder", 46578787, "abc@gmail.com");
        when(employeeService.save(employee)).thenReturn(employee);

        Employee employee1 = employeeController.addEmployee(employee);
        assertThat(employee1, is(employee));
    }

//    @Test(expected = MethodArgumentNotValidException.class)
//    public void testNoEmailForEmployeeException () {
//
//        Employee employee = new Employee();
//        employee.setEmail(null);
//
//        when(employeeService.save(employee)).thenReturn(employee);
//
//        employeeController.addEmployee(employee);
//    }

}
