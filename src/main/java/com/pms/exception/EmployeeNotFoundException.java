package com.pms.exception;

public class EmployeeNotFoundException extends RuntimeException{

    private int empId;

    public EmployeeNotFoundException(int empId, String message) {
        super(message);
        this.empId = empId;
    }
}