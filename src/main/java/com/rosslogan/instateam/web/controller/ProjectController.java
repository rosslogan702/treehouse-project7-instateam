package com.rosslogan.instateam.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

    @RequestMapping("/projects")
    public String listProjects(Model model) {
        return "index";
    }

    @RequestMapping("/projects/add")
    public String addProject(Model model){
        return "edit_project";
    }

}
