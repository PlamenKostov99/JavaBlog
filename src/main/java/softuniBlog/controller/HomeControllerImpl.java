package softuniBlog.controller;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.exceptions.AccessDeniedEx;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.service.CategoryServiceImpl;

import java.util.List;
import java.util.Set;

@RestController
public class HomeControllerImpl implements HomeController {

  private final CategoryServiceImpl categoryService;

  @Autowired
  public HomeControllerImpl(CategoryServiceImpl categoryService) {
    this.categoryService = categoryService;
  }

  public ResponseEntity<List<Category>> index() {

    List<Category> categories = categoryService.getAllCategoriesList();


    return ResponseEntity.accepted().body(categories);
  }



  public ResponseEntity<Set<Article>> articles(@PathVariable Integer id) throws EntityNotFound {

    if (!categoryService.existById(id)) {
      throw new EntityNotFound();
    }

    Category categories = categoryService.findCategoriesById(id);

    return ResponseEntity.accepted().body(categories.getArticles());
  }


  public String accessDenied() throws AccessDeniedEx {

    throw new AccessDeniedEx();


  }

}
