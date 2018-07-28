package com.pms.service;

import com.pms.exception.TaskNotFoundException;
import com.pms.model.Story;
import com.pms.model.Task;
import com.pms.repository.StoryRepository;
import com.pms.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StoryRepository storyRepository;


    public List<Task> getStatusesOfTasksOfStory(Integer id){
        Story story = storyRepository.findOne(id);
        List<Task> tasks = taskRepository.findAllByStory(story);
        return tasks;
    }

    public Task findTask(Integer id) {

        Task task = taskRepository.findOne(id);

        if(task == null){
            throw new TaskNotFoundException(id, "Task not Found");
        }

        return task;
    }

    public Task findTaskByName(String name) {
        return taskRepository.findByName(name);
    }

    public List<Task> findAllTasksByStory(Story story) {
        return taskRepository.findAllByStory(story);
    }

    public Task saveTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }
}
