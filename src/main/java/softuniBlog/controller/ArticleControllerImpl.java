package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.service.ArticleServiceImpl;
import softuniBlog.service.TagServiceImpl;


@RestController
public class ArticleControllerImpl implements ArticleController {

  private final ArticleServiceImpl articleService;

  @Autowired
  public ArticleControllerImpl(ArticleServiceImpl articleService) {

    this.articleService = articleService;
  }

  public ResponseEntity<Article> createProcess(
      ArticleBindingModel articleBindingModel, UserBindingModel userBindingModel, Integer id) {

    return ResponseEntity.accepted()
        .body(articleService.createArticles(articleBindingModel, userBindingModel, id));
  }

  public ResponseEntity<Article> details(Integer id) throws EntityNotFound {

    if (!this.articleService.existsById(id)) {
      throw new EntityNotFound();
    }

    Article article = this.articleService.getById(id);

    return ResponseEntity.accepted().body(article);
  }

  public ResponseEntity<Article> editProcess(Integer id, ArticleBindingModel articleBindingModel)
      throws EntityNotFound {

    if (!this.articleService.existsById(id)) {
      throw new EntityNotFound();
    }

    return ResponseEntity.accepted().body(articleService.editProcess(articleBindingModel, id));
  }

  public ResponseEntity<Article> deleteProcess(Integer id) throws EntityNotFound {

    if (!this.articleService.existsById(id)) {
      throw new EntityNotFound();
    }

    return ResponseEntity.accepted().body(articleService.delete(id));
  }

}
