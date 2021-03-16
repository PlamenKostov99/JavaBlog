package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.Tag;
import softuniBlog.entity.User;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;
import softuniBlog.repository.UserRepository;


import java.util.HashSet;

@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;
  private final TagServiceImpl tagService;

  @Autowired
  public ArticleServiceImpl(
      ArticleRepository articleRepository,
      CategoryRepository categoryRepository,
      UserRepository userRepository,
      TagServiceImpl tagService) {
    this.articleRepository = articleRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
    this.tagService = tagService;

  }

  @Override
  public Article createArticles(
      ArticleBindingModel articleBindingModel, UserBindingModel userBindingModel, Integer id) {

    User userEntity = this.userRepository.findByEmail(userBindingModel.getEmail());

    Category category =
        this.categoryRepository.findCategoriesById(articleBindingModel.getCategoryId());

    HashSet<Tag> tags = this.tagService.getTagsFromString(articleBindingModel.getTagString());

    Article articleEntity =
        new Article(
            articleBindingModel.getTitle(),
            articleBindingModel.getContent(),
            userEntity,
            category,
            tags);

    this.articleRepository.saveAndFlush(articleEntity);
    return articleEntity;
  }

  @Override
  public Article getById(Integer id) {
    return this.articleRepository.findById(id).orElse(null);
  }

  @Override
  public Article editProcess(ArticleBindingModel articleBindingModel, Integer id) {

    Article article = articleRepository.getById(id);
    Category category =
        this.categoryRepository.findCategoriesById(articleBindingModel.getCategoryId());
    HashSet<Tag> tags = this.tagService.getTagsFromString(articleBindingModel.getTagString());

    article.setCategory(category);
    article.setTitle(articleBindingModel.getTitle());
    article.setContent(articleBindingModel.getContent());
    article.setTags(tags);

    this.articleRepository.saveAndFlush(article);
    return article;
  }

  @Override
  public Article delete(Integer id) throws EntityNotFound {
    if (!this.articleRepository.existsById(id)) {
      throw new EntityNotFound();
    }
    Article article = articleRepository.getById(id);
    articleRepository.delete(article);

    return article;
  }

  @Override
  public boolean existsById(Integer id) {
    return this.articleRepository.existsById(id);
  }
}
