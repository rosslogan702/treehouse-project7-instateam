package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.ProjectStatus;
import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Project;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.ProjectService;
import com.rosslogan.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    RoleService roleService;

    @RequestMapping(value = {"/projects", "/"})
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "index";
    }

    @RequestMapping(value ="/projects", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes){
        projectService.save(project);
        return "redirect:/projects";
    }

    @RequestMapping(value = "/projects/add")
    public String formAddProject(Model model){
        if(!model.containsAttribute("project")) {
            model.addAttribute("project",new Project());
        }

        List<Role>  roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("statuses", ProjectStatus.values());

        return "edit_project";
    }

    // Form for editing an existing collaborator
    @RequestMapping("projects/{projectId}/edit")
    public String formEditProject(@PathVariable Long projectId, Model model) {
        // TODO: Add model attributes needed for new form
        if(!model.containsAttribute("project")) {
            Project project = projectService.findById(projectId);
            model.addAttribute("project",project);
        }
        return "project_detail";
    }

}
