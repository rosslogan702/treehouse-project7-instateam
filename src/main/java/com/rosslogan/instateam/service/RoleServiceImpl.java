package com.rosslogan.instateam.service;

import com.rosslogan.instateam.dao.CollaboratorDao;
import com.rosslogan.instateam.dao.RoleDao;
import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private long UNALLOCATED_COLLABORATOR_ID = 1L;
    @Autowired
    RoleDao roleDao;
    @Autowired
    CollaboratorService collaboratorService;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delete(Role role) {
        List<Collaborator> collaborators = role.getCollaborators();
        for(Collaborator collaborator: collaborators){
            collaborator.setRole(findById(UNALLOCATED_COLLABORATOR_ID));
            collaboratorService.save(collaborator);
        }
        roleDao.delete(role);
    }
}
