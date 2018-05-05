package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Project;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.CollaboratorService;
import com.rosslogan.instateam.service.RoleService;
import com.rosslogan.instateam.web.FlashMessage;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
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

    // Form for editing an existing collaborator
    @RequestMapping("collaborators/{collaboratorId}/edit")
    public String formEditCollaborator(@PathVariable Long collaboratorId, Model model) {
        // TODO: Add model attributes needed for new form
        if(!model.containsAttribute("collaborator")) {
            model.addAttribute("collaborator",collaboratorService.findById(collaboratorId));
        }
        model.addAttribute("roles", roleService.findAll());
        return "collaborator_detail";
    }

    @RequestMapping(value = "/collaborators/{collaboratorId}", method = RequestMethod.POST)
    public String updateCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator",result);
            redirectAttributes.addFlashAttribute("collaborator", collaborator);
            return String.format("redirect:/collaborators/%s/edit", collaborator.getId());
        }
        collaboratorService.save(collaborator);
        return "redirect:/collaborators";
    }

    // Delete an existing category
    @RequestMapping(value = "/collaborators/{collaboratorId}/delete", method = RequestMethod.POST)
    public String deleteCollaborator(@PathVariable Long collaboratorId, RedirectAttributes redirectAttributes) {
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        List<Project> projects = collaborator.getProjects();
        if(projects.size() > 0){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator Deletion Failed! This collaborator is in use in a project! Please delete from project first!", FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("collaborator", collaborator);
            return String.format("redirect:/collaborators/%s/edit", collaborator.getId());
        } else {
            collaboratorService.delete(collaborator);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator Deleted!", FlashMessage.Status.SUCCESS));
            return "redirect:/collaborators";
        }
    }
}
