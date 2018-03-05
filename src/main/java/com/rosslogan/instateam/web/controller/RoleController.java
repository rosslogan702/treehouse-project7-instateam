package com.rosslogan.instateam.web.controller;

import com.rosslogan.instateam.model.Role;
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
}
