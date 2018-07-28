package com.pms.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.*;

@Entity
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storyId;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;
    private int numberOfTasks;
    private Date startDate;
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Story() {}

    public Story(String name, String description, Status status, int numberOfTasks, Date startDate, Date endDate, Employee employee, Project project) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.numberOfTasks = numberOfTasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.project = project;
    }

    public Story(String name, String description, Status status, int numberOfTasks, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.numberOfTasks = numberOfTasks;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Story(int storyId, String name, String description, Status status, int numberOfTasks, Date startDate, Date endDate, Employee employee, Project project) {
        this.storyId = storyId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.numberOfTasks = numberOfTasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.project = project;
    }


    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

//    public void setNumberOfTasks(int numberOfTasks) {
//        this.numberOfTasks = numberOfTasks;
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

//    public Project getProject() {
//        return project;
//    }

    public void setProject(Project project) {
        this.project = project;
    }
}
