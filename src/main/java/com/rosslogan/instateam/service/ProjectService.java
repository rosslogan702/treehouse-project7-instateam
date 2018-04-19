package com.rosslogan.instateam.service;

import com.rosslogan.instateam.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project role);
    void delete(Project role);
}

