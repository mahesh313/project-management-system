package com.pms.controller;

import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.service.ProjectService;
import com.pms.service.StoryService;
import com.pms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class StoryController  {

    @Autowired
    TaskService taskService;

    @Autowired
    StoryService storyService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "get/story/{id}", method = RequestMethod.GET)
    public String getStory(@PathVariable Integer id){
        Story story = storyService.findStory(id);
        return story.getName();
    }

    @RequestMapping(value = "get/story/{name}/status", method = RequestMethod.GET)
    public Status getStoryStatus(@PathVariable String name){
        Story story = storyService.findStoryByName(name);
        return story.getStatus();
    }

    @RequestMapping(value = "/add/story/{projectId}",consumes = APPLICATION_JSON_VALUE ,produces = APPLICATION_JSON_VALUE ,method = RequestMethod.POST)
    public Story addStory(@RequestBody Story story, @PathVariable Integer projectId) {
        Project project = projectService.findProject(projectId);
        story.setProject(project);
        return storyService.saveStory(story);
    }

    @RequestMapping(value = "update/story/{id}/status/{status}", method = RequestMethod.PUT)
    public Status updateStoryStatus(@PathVariable Integer id, @PathVariable Status status) {
        Story story = storyService.findStory(id);
        return storyService.updateStatusOfStory(story,status);
    }


    @RequestMapping(value = "get/story/{name}/employee", method = RequestMethod.GET)
    public String getEmployeeOfStory(@PathVariable String name){
        Story story = storyService.findStoryByName(name);
        return story.getEmployee().getName();
    }
}
