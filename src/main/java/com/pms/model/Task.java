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
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String comments;
    private String issues;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @OneToOne
    private Employee employee;

    public Task() {}

    public Task(String name, String description, Status status, String comments, String issues, Date startDate, Date endDate, Story story, Employee employee) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.comments = comments;
        this.issues = issues;
        this.startDate = startDate;
        this.endDate = endDate;
        this.story = story;
        this.employee = employee;
    }

    public Task(int id, String name, String description, Status status, String comments, String issues, Date startDate, Date endDate, Story story, Employee employee) {
        this.taskId = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.comments = comments;
        this.issues = issues;
        this.startDate = startDate;
        this.endDate = endDate;
        this.story = story;
        this.employee = employee;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }

//    public Story getStory() {
//        return story;
//    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
