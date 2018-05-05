package com.rosslogan.instateam.service;

import com.rosslogan.instateam.dao.ProjectDao;
import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Project;
import com.rosslogan.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao projectDao;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectDao.findById(id);
    }

    @Override
    public void save(Project project) {
        projectDao.save(project);
    }

    @Override
    public void delete(Project project) {
        project.setRolesNeeded(new ArrayList<Role>());
        project.setCollaborators(new ArrayList<Collaborator>());
        projectDao.save(project);
        projectDao.delete(project);
    }
}
