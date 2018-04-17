package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    RoleService roleService;

    @RequestMapping(value = {"/projects", "/"})
    public String listProjects(Model model) {
        return "index";
    }

    @RequestMapping("/projects/add")
    public String addProject(Model model){
        List<Role>  roles = roleService.findAll();
        model.addAttribute("roles", roles);

        return "edit_project";
    }

}
