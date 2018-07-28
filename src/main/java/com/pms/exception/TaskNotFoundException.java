package com.pms.exception;

public class TaskNotFoundException extends RuntimeException {

    private int taskId;

    public TaskNotFoundException(int taskId, String message) {
        super(message);
        this.taskId = taskId;
    }
}
