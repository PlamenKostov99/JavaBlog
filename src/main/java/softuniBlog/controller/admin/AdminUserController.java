package softuniBlog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Role;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.RoleRepository;
import softuniBlog.repository.UserRepository;
import softuniBlog.service.RoleServiceImpl;
import softuniBlog.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {


    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public String listUsers(Model model) {

        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.userService.existsById(id)) {
            return "redirect:/admin/users/";
        }

        User user = this.userService.getById(id);
        List<Role> roles = roleService.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "/admin/user/edit");

        return "base-layout";

    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, UserEditBindingModel userEditBindingModel) {

        if (!this.userService.existsById(id)) {
            return "redirect:/admin/users/";
        }

       this.userService.editUser(id, userEditBindingModel);

        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        if (!this.userService.existsById(id)) {
            return "redirect:/admin/users/";
        }

        User user = this.userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("view", "/admin/user/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {

        if (!this.userService.existsById(id)) {
            return "redirect:/admin/users/";
        }
        this.userService.deleteUser(id);

        return "redirect:/admin/users/";
    }


}
