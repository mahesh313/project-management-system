package com.pms.exception;

public class ProjectNotFoundException extends RuntimeException {

    private int projId;

    public ProjectNotFoundException(int projId, String message) {
        super(message);
        this.projId = projId;
    }
}
