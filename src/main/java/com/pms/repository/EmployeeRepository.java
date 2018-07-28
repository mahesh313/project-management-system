package com.pms.repository;

import com.pms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    void deleteEmployeeById(Integer id);
}
