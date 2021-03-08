package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.Tag;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;
import softuniBlog.repository.TagRepository;
import softuniBlog.repository.UserRepository;
import softuniBlog.service.*;

import javax.jws.WebParam;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.text.View;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArticleController {


    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ArticleServiceImpl articleService;


    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {

        model.addAttribute("view", "article/create");

        List<Category> categories = this.categoryService.getAll();

        model.addAttribute("categories", categories);

        return "base-layout";

    }

    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel,
                                @RequestParam("image") MultipartFile file) throws IOException {

        this.articleService.createArticles(articleBindingModel, file);

        return "redirect:/";
    }


    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id ) throws UnsupportedEncodingException{

        if (!this.articleService.existsById(id)) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof
                AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal();

            User entityUser = this.userService.getByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Article article = this.articleService.getById(id);

        String image = this.articleService.getImgUtility(id);

        model.addAttribute("image" , image);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");

        return "base-layout";
    }

    @GetMapping("/article/like/{id}")
    public String like(Model model, @PathVariable Integer id ) throws UnsupportedEncodingException {
        if (!this.articleService.existsById(id)) {
            return "redirect:/";
        }
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof
                AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userService.getByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Article article = this.articleService.getById(id);

        String image = this.articleService.getImgUtility(id);

        this.articleService.countClick(id);

        model.addAttribute("image" , image);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/like");

        return "base-layout";

    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {

        if (!this.articleService.existsById(id)) {
            return "redirect:/";
        }
        Article article = this.articleService.getById(id);

        if (!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        List<Category> categories = this.categoryService.getAll();
        String tagString = article.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));


        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagString);

        return "base-layout";
    }

    @PostMapping("article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel,
                              @RequestParam("image") MultipartFile file) throws IOException {

        if (!this.articleService.existsById(id)) {
            return "redirect:/";
        }

        Article article = this.articleService.getById(id);

        if (!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        this.articleService.editProcess(articleBindingModel, id , article, file);

        return "redirect:/article/" + article.getId();

    }

    @GetMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {

        if (!this.articleService.existsById(id)) {
            return "redirect:/";
        }
        Article article = this.articleService.getById(id);

        if (!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return "base-layout";
    }

    @PostMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {

        if (!this.articleService.existsById(id)) {
            return "redirect:/";
        }

        Article article = this.articleService.getById(id);

        if (!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        this.articleService.delete(article);

        return "redirect:/";

    }

    private boolean isUserAuthorOrAdmin(Article article){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                            .getAuthentication().getPrincipal();

        User userEntity = this.userService.getByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }

    private HashSet<Tag> findTagsFromString(String tagString){

        return this.tagService.getTagsFromString(tagString);
    }


}
