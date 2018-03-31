package com.rosslogan.instateam;


import com.rosslogan.instateam.model.Role;
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

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<Role> allRoles = roleService.findAll();
        for(Role role: allRoles){
            if(role.getName().equals("Unallocated")){
                return ;
            }
        }
        Role role = new Role();
        role.setName("Unallocated");
        roleService.save(role);
    }
}
