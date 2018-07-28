package com.pms.service;

import com.pms.exception.EmployeeNotFoundException;
import com.pms.model.Employee;
import com.pms.repository.EmployeeRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeService {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee findEmployee(Integer id) {

        Employee employee = employeeRepository.findOne(id);
        if(employee == null){
            throw new EmployeeNotFoundException(id, "Employee not Found");
        }
        return employee;
    }

    public Employee findEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Transactional
    public Employee removeEmployee(int id) {
        Employee employee = employeeRepository.findOne(id);
        employeeRepository.deleteEmployeeById(employee.getId());
        String no  = Integer.toString(id);
        this.template.convertAndSend(queue.getName(), no);
        System.out.println("---------send----------"+ no);
        return employee;
    }
}
