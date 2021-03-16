package softuniBlog.service;

import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findCategoriesById(Integer id);

    List<Category> getAllCategoriesList();

    Category createCategory(CategoryBindingModel name);

    Category editCategory(Integer id, CategoryBindingModel name);

    Category deleteCategory(Integer id);

    boolean existById(Integer id);

    List<Category> getAll();
}
