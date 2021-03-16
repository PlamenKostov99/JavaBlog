package softuniBlog.service;

import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.exceptions.EntityNotFound;

import java.io.IOException;

public interface ArticleService {

  Article createArticles(
      ArticleBindingModel articleBindingModel, UserBindingModel userBindingModel, Integer id)
      throws IOException;

  Article getById(Integer id);

  Article editProcess(ArticleBindingModel articleBindingModel, Integer id) throws IOException;

  Article delete(Integer id) throws EntityNotFound;

  boolean existsById(Integer id);
}
