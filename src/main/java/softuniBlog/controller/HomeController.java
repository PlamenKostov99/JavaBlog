package softuniBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.exceptions.AccessDeniedEx;
import softuniBlog.exceptions.EntityNotFound;

import java.util.List;
import java.util.Set;

public interface HomeController {

  @GetMapping("/")
  ResponseEntity<List<Category>> index();

  @GetMapping("/error/403")
   String accessDenied() throws Exception;

  @GetMapping("/category/{id}")
  ResponseEntity<Set<Article>> articles(@PathVariable Integer id) throws Exception, EntityNotFound;
}
