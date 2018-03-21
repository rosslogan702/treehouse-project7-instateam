package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.service.CollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CollaboratorController {
    @Autowired
    CollaboratorService collaboratorService;

    // Index of all roles
    @SuppressWarnings("unchecked")
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model) {
        if(!model.containsAttribute("collaborator")) {
            model.addAttribute("collaborator", new Collaborator());
        }
        // TODO: Get all roles
        List<Collaborator> collaborators = collaboratorService.findAll();
        model.addAttribute("collaborators",collaborators);
        return "collaborators";
    }
}
