package doanjava1com.example.demo1.Controllers;

import doanjava1com.example.demo1.Models.Role;
import doanjava1com.example.demo1.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
@ComponentScan("doanjava1com.example.demo1.Services")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String viewAllRole(Model model) {
        List<Role> listRoles = roleService.listAll();
        model.addAttribute("roles", listRoles);
        return "role/index";
    }

    @GetMapping("/new")
    public String showNewRolePage(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "role/create";
    }

    @PostMapping("/save")
    public String saveRole(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String showEditRolePage(@PathVariable("id") Long id, Model model) {
        Role role = roleService.get(id);

        if(role == null) {
            return "notfound";
        } else {
            model.addAttribute("role", role);
            return "role/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        Role role = roleService.get(id);
        if(role == null) {
            return "notfound";
        } else {
            roleService.delete(id);
            return "redirect:/roles";
        }
    }
}
