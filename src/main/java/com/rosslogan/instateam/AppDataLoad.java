package com.rosslogan.instateam;


import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.CollaboratorService;
import com.rosslogan.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AppDataLoad implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    RoleService roleService;
    @Autowired
    CollaboratorService collaboratorService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<Role> allRoles = roleService.findAll();
        boolean unallocated_available = false;
        for(Role role: allRoles){
            if(role.getName().equals("Unallocated")){
                unallocated_available = true;
            }
        }
        if(!unallocated_available){
            Role role = new Role();
            role.setName("Unallocated");
            roleService.save(role);
        }

        List<Collaborator> allCollaborators = collaboratorService.findAll();
        for(Collaborator collaborator: allCollaborators){
            if(collaborator.getName().equals("Unallocated")){
                return ;
            }
        }
        Collaborator collaborator = new Collaborator();
        collaborator.setName("Unallocated");
        collaboratorService.save(collaborator);
    }
}
