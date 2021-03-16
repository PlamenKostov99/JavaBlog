package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Category;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.exceptions.MatchedNames;
import softuniBlog.service.CategoryServiceImpl;

import java.util.List;

@RestController
public class CategoryControllerImpl implements CategoryController {

  private final CategoryServiceImpl categoryService;

  @Autowired
  public CategoryControllerImpl(CategoryServiceImpl categoryService) {
    this.categoryService = categoryService;
  }

  public ResponseEntity<List<Category>> list() {

    return ResponseEntity.accepted().body(categoryService.getAllCategoriesList());
  }


  public ResponseEntity<Category> createProcess(CategoryBindingModel categoryBindingModel) throws MatchedNames {

    if (!StringUtils.hasText(categoryBindingModel.getName())) {
      throw new MatchedNames();
    }

    return ResponseEntity.accepted().body(categoryService.createCategory(categoryBindingModel));

  }


  public ResponseEntity<Category> editProcess(Integer id, CategoryBindingModel categoryBindingModel) throws EntityNotFound {

    if (!this.categoryService.existById(id)) {
      throw new EntityNotFound();
    }

    return ResponseEntity.accepted().body(this.categoryService.editCategory(id, categoryBindingModel));


  }


  public ResponseEntity<Category> deleteProcess(Integer id) throws EntityNotFound {

    if (!this.categoryService.existById(id)) {
      throw new EntityNotFound();
    }

    this.categoryService.deleteCategory(id);

   return  ResponseEntity.accepted().body(categoryService.deleteCategory(id));
  }
}
