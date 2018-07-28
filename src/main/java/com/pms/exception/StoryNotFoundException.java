package com.pms.exception;

public class StoryNotFoundException extends RuntimeException {

    private int storyId;

    public StoryNotFoundException(int storyId, String message) {
        super(message);
        this.storyId = storyId;
    }
}
