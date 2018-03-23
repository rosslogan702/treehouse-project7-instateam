package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.CollaboratorService;
import com.rosslogan.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Role>  roles = roleService.findAll();
        model.addAttribute("collaborators",collaborators);
        model.addAttribute("roles", roles);
        return "collaborators";
    }

    // Add a category
    @RequestMapping(value = "/collaborators", method = RequestMethod.POST)
    public String addCategory(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator",result);
            redirectAttributes.addFlashAttribute("collaborator", collaborator);
            return "redirect:/collaborators";
        }
        collaboratorService.save(collaborator);
        return "redirect:/collaborators";
    }
}
