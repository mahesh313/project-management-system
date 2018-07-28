package com.pms.service;

import com.pms.exception.ProjectNotFoundException;
import com.pms.model.Project;
import com.pms.model.Status;
import com.pms.model.Story;
import com.pms.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private StoryService storyService;

    public Project findProject(Integer id) {

        Project project = projectRepository.findOne(id);

        if(project == null){
            throw new ProjectNotFoundException(id, "Project not Found");
        }

        return project;
    }

    @Cacheable(value = "projects", key = "#name")
    public Project findProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    public Project saveProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public Status updateStatusOfProject(Project project, Status status) {
        Integer projectId = project.getProjectId();
        List<Story> storyList = storyService.getStoriesOfProject(projectId);
        List<Status> statusList = new ArrayList<Status>();
        for (Story story:storyList) {
            statusList.add(story.getStatus());
        }
        Status updatedStatus = checkProjectStatus(statusList);
        if(updatedStatus == status){
            project.setStatus(updatedStatus);
            projectRepository.saveAndFlush(project);
            return project.getStatus();
        }
        return Status.INVALID;
    }

    public Status checkProjectStatus(List<Status> statusList) {
        if(statusList.contains(Status.QUEUED)){
            return Status.QUEUED;
        }
        if(statusList.contains(Status.INPROGRESS)){
            return Status.INPROGRESS;
        }
        return Status.DONE;
    }


}
