package com.pms.controller;

import com.pms.model.Employee;
import com.pms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/add/employee",consumes = APPLICATION_JSON_VALUE ,produces = APPLICATION_JSON_VALUE ,method = RequestMethod.POST)
    public Employee addEmployee(@Valid @RequestBody Employee employee) {

        return employeeService.save(employee);
    }

    @RequestMapping(value = "get/employee/{id}", method = RequestMethod.GET)
    public String getEmployee(@PathVariable Integer id){
        Employee employee = employeeService.findEmployee(id);
        return employee.getName();
    }

    @RequestMapping(value = "get/employee/contact/{name}", method = RequestMethod.GET)
    public long getEmployeeContact(@PathVariable String name){
        Employee employee = employeeService.findEmployeeByName(name);
        return employee.getContact();
    }

    @RequestMapping(value = "remove/employee/{id}", method = RequestMethod.DELETE)
    public long removeEmployee(@PathVariable int id){
        Employee employee = employeeService.removeEmployee(id);
        return employee.getContact();
    }



}