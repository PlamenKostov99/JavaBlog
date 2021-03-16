package softuniBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Category;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.exceptions.MatchedNames;

import java.util.List;

@RequestMapping("/admin/categories")
public interface CategoryController {

  @GetMapping("/")
  ResponseEntity<List<Category>> list();

  @PostMapping("/create")
  ResponseEntity<Category> createProcess(CategoryBindingModel categoryBindingModel) throws MatchedNames;

  @PostMapping("/edit/{id}")
  ResponseEntity<Category> editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) throws EntityNotFound;

  @PostMapping("delete/{id}")
  ResponseEntity<Category> deleteProcess(@PathVariable Integer id) throws EntityNotFound;
}
