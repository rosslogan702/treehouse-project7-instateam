package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.CollaboratorService;
import com.rosslogan.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CollaboratorController {
    @Autowired
    CollaboratorService collaboratorService;
    @Autowired
    RoleService roleService;

    // Index of all roles
    @SuppressWarnings("unchecked")
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model) {
        if(!model.containsAttribute("collaborator")) {
            model.addAttribute("collaborator", new Collaborator());
        }
        // TODO: Get all collaborators and roles
        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Role>  roles = roleService.findAll();
        model.addAttribute("collaborators",collaborators);
        model.addAttribute("roles", roles);
        return "collaborators";
    }
}
