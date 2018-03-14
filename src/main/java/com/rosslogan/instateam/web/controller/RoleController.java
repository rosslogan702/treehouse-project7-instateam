package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Role;
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
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    // Index of all roles
    @SuppressWarnings("unchecked")
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        // TODO: Get all roles
        List<Role> roles = roleService.findAll();

        model.addAttribute("roles",roles);
        //Blank role attribute for adding a new role
        model.addAttribute("role", new Role());
        return "roles";
    }


    // Add a category
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String addCategory(@Valid Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    // Form for editing an existing role
    @RequestMapping("roles/{roleId}/edit")
    public String formEditCategory(@PathVariable Long roleId, Model model) {
        // TODO: Add model attributes needed for new form
        if(!model.containsAttribute("role")) {
            model.addAttribute("role",roleService.findById(roleId));
        }

        return "role_detail";
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.POST)
    public String updateRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role",result);
            redirectAttributes.addFlashAttribute("role", role);
            return String.format("redirect:/roles/%s/edit", role.getId());
        }
        roleService.save(role);
        return "redirect:/roles";
    }

    // Delete an existing category
    @RequestMapping(value = "/roles/{roleId}/delete")
    public String deleteRole(@PathVariable Long roleId, RedirectAttributes redirectAttributes) {
        Role role = roleService.findById(roleId);
        roleService.delete(role);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Role Deleted!", FlashMessage.Status.SUCCESS));
        return "redirect:/roles";
    }


}
