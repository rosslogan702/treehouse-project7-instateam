package com.rosslogan.instateam.service;

import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Role;
import java.util.List;

public interface CollaboratorService {
    List<Collaborator> findAll();
    Collaborator findById(Long id);
    void save(Collaborator collaborator);
    void delete(Collaborator collaborator);
}
