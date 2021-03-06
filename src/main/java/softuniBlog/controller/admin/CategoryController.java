package softuniBlog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;
import softuniBlog.service.CategoryServiceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {


    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("view", "admin/category/list");

      List<Category> categories = categoryService.getAllCategoriesList();

        model.addAttribute("categories", categories);

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel){

        if (!StringUtils.hasText(categoryBindingModel.getName())){
            return "redirect:/admin/categories/create";
        }

        categoryService.createCategory(categoryBindingModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){

        if (!this.categoryService.existById(id)){
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryService.findCategoriesById(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");


        return "base-layout";

    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){

        if (!this.categoryService.existById(id)){
            return "redirect:/admin/categories/";
        }

        this.categoryService.editCategory(id, categoryBindingModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/delete{id}")
    public String delete(Model model, @PathVariable Integer id){

        if (!this.categoryService.existById(id)){
            return "redirect:/admin/categories";
        }

        Category category = this.categoryService.findCategoriesById(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return "base-layout";
    }

    @GetMapping("delete/{id}")
    public String deleteProcess(@PathVariable Integer id){

        if (!this.categoryService.existById(id)){
            return "redirect:/admin/categories";

        }

        this.categoryService.deleteCategory(id);

        return "redirect:/admin/categories/";
    }


}
