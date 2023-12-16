package doanjava1com.example.demo1.Controllers;

import doanjava1com.example.demo1.Models.User;
import doanjava1com.example.demo1.Services.RoleService;
import doanjava1com.example.demo1.Services.UserService;
import doanjava1com.example.demo1.Utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
@ComponentScan("doanjava1com.example.demo1.Services")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    public String viewAllUser(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("users", listUsers);
        return "user/index";
    }

    @GetMapping("/new")
    public String showNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listAll());
        return "user/create";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user")User user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotourl(fileName);
        user.setEnabled(true);
        User saveUser = userService.save(user);
        if (!multipartFile.getOriginalFilename().isBlank())
        {
            String uploadDir = "photos/" + saveUser.getId();
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }

        return "redirect:/users";
    }


    @GetMapping("/edit/{id}")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);

        if(user == null) {
            return "notfound";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.listAll());
            return "user/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRUser(@PathVariable("id") Long id) {
        User user = userService.get(id);
        if(user == null) {
            return "notfound";
        } else {
            userService.delete(id);
            return "redirect:/users";
        }
    }
}
