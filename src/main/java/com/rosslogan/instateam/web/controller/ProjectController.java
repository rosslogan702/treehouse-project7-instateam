package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.ProjectStatus;
import com.rosslogan.instateam.model.Collaborator;
import com.rosslogan.instateam.model.Project;
import com.rosslogan.instateam.model.Role;
import com.rosslogan.instateam.service.ProjectService;
import com.rosslogan.instateam.service.RoleService;
import com.rosslogan.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project",result);
            redirectAttributes.addFlashAttribute("project", project);
            return "redirect:/projects/add";
        }
        projectService.save(project);
        return "redirect:/projects";
    }

    @RequestMapping(value = "/projects/add")
    public String formAddNewProject(Model model){
        if(!model.containsAttribute("project")) {
            model.addAttribute("project",new Project());
        }

        List<Role>  roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("statuses", ProjectStatus.values());

        return "edit_project";
    }

    @RequestMapping(value="/projects/{projectId}/edit")
    public String formEditExistingProject(@PathVariable Long projectId, Model model){
        if(!model.containsAttribute("project")){
            Project project = projectService.findById(projectId);
            List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            model.addAttribute("project", project);
            model.addAttribute("statuses", ProjectStatus.values());
        }
        return "edit_project";
    }

    @RequestMapping("projects/{projectId}/detail")
    public String projectDetail(@PathVariable Long projectId, Model model) {
        //TODO: Fix project detail now that collaborators can be edited, still showing unallocated all the time
        // use roles and collaborator from project rather than explicit
        // Use a map to map the roles and collaborators in the controller here rather than just project, it's the id
        // that ties them together
        // Tidy up the entries for better testing
        // Then consider the extra credit
        if(!model.containsAttribute("project")) {
            Project project = projectService.findById(projectId);
            model.addAttribute("project",project);
            Map<Role, Collaborator> roleCollabMapping = getRoleCollabMapping(project);
            model.addAttribute("roleCollabMapping", roleCollabMapping);
            List<Role> roles = project.getRolesNeeded();
            model.addAttribute("roles", roles);
            Collaborator default_collaborator = new Collaborator();
            default_collaborator.setName("Unallocated");
            model.addAttribute("default_collaborator", default_collaborator);
        }
        return "project_detail";
    }

    @RequestMapping("/projects/{projectId}/collaborator")
    public String formEditProjectCollaborators(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        return "project_collaborators";
    }

    @RequestMapping(value = "/projects/{projectId}/update/collaborators", method = RequestMethod.POST)
    public String updateProjectCollaborators(@PathVariable Long projectId, Project project, BindingResult result,
                                             RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project",result);
            redirectAttributes.addFlashAttribute("project", project);
            return String.format("redirect:/projects/%s/collaborator", projectId.toString());
        }
        Project default_project = projectService.findById(projectId);
        default_project.setCollaborators(project.getCollaborators());
        projectService.save(default_project);
        return String.format("redirect:/projects/%s/detail", projectId.toString());
    }

    // Delete an existing category
    @RequestMapping(value = "/projects/{projectId}/delete", method = RequestMethod.POST)
    public String deleteProject(@PathVariable Long projectId, RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(projectId);
        projectService.delete(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project Deleted!", FlashMessage.Status.SUCCESS));
        return "redirect:/projects";
    }

    private Map<Role,Collaborator> getRoleCollabMapping(Project project) {
        List<Role> rolesNeeded = project.getRolesNeeded();
        List<Collaborator> collaborators = project.getCollaborators();
        Map<Role, Collaborator> roleCollaboratorMap = new LinkedHashMap<>();

        for(Role role: rolesNeeded){
            for(Collaborator collaborator: collaborators){
                if (role.getId().equals(collaborator.getRole().getId())){
                    roleCollaboratorMap.put(role, collaborator);
                }
            }
            if (!roleCollaboratorMap.containsKey(role)){
                Collaborator unallocated = new Collaborator();
                unallocated.setName("Unallocated");
                roleCollaboratorMap.put(role, unallocated);
            }
        }
        return roleCollaboratorMap;
    }
}
