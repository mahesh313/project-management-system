package com.pms.service;

import com.pms.exception.StoryNotFoundException;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.model.Task;
import com.pms.repository.ProjectRepository;
import com.pms.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoryService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    TaskService taskService;

    public Story findStory(Integer id) {

        Story story = storyRepository.findOne(id);

        if(story == null){
            throw new StoryNotFoundException(id, "Story not Found");
        }

        return story;
    }

    public Story findStoryByName(String name) {
        return storyRepository.findByName(name);
    }

    public Story saveStory(Story story) {
        return storyRepository.saveAndFlush(story);
    }

    public List<Story> getStoriesOfProject(Integer projectId) {
        Project project= projectRepository.findOne(projectId);
        List<Story> storyList = storyRepository.findAllByProject(project);
        return storyList;
    }

    public Status updateStatusOfStory(Story story, Status status) {
        Integer storyId = story.getStoryId();
        List<Task> taskList = taskService.getStatusesOfTasksOfStory(storyId);
        List<Status> statusList = new ArrayList<Status>();
        for (Task task:taskList) {
            statusList.add(task.getStatus());
        }
        Status updatedStatus = checkStoryStatus(statusList);
        if(updatedStatus == status)
        {
            story.setStatus(updatedStatus);
            storyRepository.save(story);
            return story.getStatus();
        }
        return Status.INVALID;
    }

    public Status checkStoryStatus(List<Status> statusList) {
        if(statusList.contains(Status.QUEUED)){
            return Status.QUEUED;
        }
        if(statusList.contains(Status.INPROGRESS)){
            return Status.INPROGRESS;
        }
        return Status.DONE;
    }
}
