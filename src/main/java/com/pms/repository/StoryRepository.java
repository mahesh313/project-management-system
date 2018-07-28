package com.pms.repository;

import com.pms.model.Project;
import com.pms.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    Story findByName(String name);
    List<Story> findAllByProject(Project project);
}
