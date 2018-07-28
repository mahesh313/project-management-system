package com.pms.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    private String name;
    private String technologies;

    @Enumerated(EnumType.STRING)
    private Status status;
    private int numberOfStories;
    private Date startDate;
    private Date endDate;

    @OneToOne
    private Employee employee;

    public Project() {}


    public Project(String name, String technologies, Status status, int numberOfStories, Date startDate, Date endDate) {
        this.name = name;
        this.technologies = technologies;
        this.status = status;
        this.numberOfStories = numberOfStories;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project(int projectId, String name, String technologies, Status status, int numberOfStories, Date startDate, Date endDate) {
        this.projectId = projectId;
        this.name = name;
        this.technologies = technologies;
        this.status = status;
        this.numberOfStories = numberOfStories;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechnologies() {
        return technologies;
    }

//    public void setTechnologies(String technologies) {
//        this.technologies = technologies;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNumberOfStories() {
        return numberOfStories;
    }

//    public void setNumberOfStories(int numberOfStories) {
//        this.numberOfStories = numberOfStories;
//    }

    public Date getStartDate() {
        return startDate;
    }

//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }

    public Date getEndDate() {
        return endDate;
    }

//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
