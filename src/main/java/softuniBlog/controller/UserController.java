package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.User;
import softuniBlog.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;


    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel,
                                  @RequestParam("image") MultipartFile file, Integer id) throws Exception {

        if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return "redirect:/register";
        }

        this.userServiceImpl.registerUser(userBindingModel, file);


        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("view", "user/login");

        return "base-layout";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response , auth);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model)
            throws IOException {

        User user = userServiceImpl.getProfileDetails();

       String image = this.userServiceImpl.getImgUtility();

        Set<Article> articles = user.getArticles();

       model.addAttribute("image" , image);

        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        model.addAttribute("view", "user/profile");

        return "base-layout";
    }



}
