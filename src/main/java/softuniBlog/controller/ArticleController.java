package softuniBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.exceptions.CheckForProfile;
import softuniBlog.exceptions.EntityNotFound;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RequestMapping("/article")
public interface ArticleController {

  @PostMapping("/create")
  ResponseEntity<Article> createProcess(
      ArticleBindingModel articleBindingModel, UserBindingModel userBindingModel, Integer id)
      throws IOException;

  @GetMapping("/{id}")
  ResponseEntity<Article> details(@PathVariable Integer id)
      throws UnsupportedEncodingException, EntityNotFound;

  @PostMapping("/edit/{id}")
  ResponseEntity<Article> editProcess(
      @PathVariable Integer id, ArticleBindingModel articleBindingModel) throws EntityNotFound;

  @PostMapping("/delete/{id}")
  ResponseEntity deleteProcess(@PathVariable Integer id) throws EntityNotFound, CheckForProfile;
}
