package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final ArticleRepository articleRepository;

  @Autowired
  public CategoryServiceImpl(
      CategoryRepository categoryRepository, ArticleRepository articleRepository) {
    this.categoryRepository = categoryRepository;
    this.articleRepository = articleRepository;
  }

  @Override
  public Category findCategoriesById(Integer id) {
    return this.categoryRepository.findCategoriesById(id);
  }

  @Override
  public List<Category> getAllCategoriesList() {

    List<Category> categories = this.categoryRepository.findAll();

    categories =
        categories.stream()
            .sorted(Comparator.comparingInt(Category::getId))
            .collect(Collectors.toList());

    return categories;
  }

  @Override
  public Category createCategory(CategoryBindingModel categoryBindingModel) {

    Category category = new Category(categoryBindingModel.getName());
    this.categoryRepository.saveAndFlush(category);

    return category;
  }

  @Override
  public Category editCategory(Integer id, CategoryBindingModel categoryBindingModel) {

    Category category = this.categoryRepository.findById(id).orElse(null);

    category.setName(categoryBindingModel.getName());

    this.categoryRepository.saveAndFlush(category);

    return category;
  }

  @Override
  public Category deleteCategory(Integer id) {

    Category category = this.categoryRepository.findCategoriesById(id);

    for (Article article : category.getArticles()) {
      this.articleRepository.delete(article);
    }
    this.categoryRepository.delete(category);

    return category;
  }

  @Override
  public boolean existById(Integer id) {
    return this.categoryRepository.existsById(id);
  }

  @Override
  public List<Category> getAll() {
    return this.categoryRepository.findAll();
  }
}
