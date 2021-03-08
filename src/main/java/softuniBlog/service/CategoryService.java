package softuniBlog.service;

import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findCategoriesById(Integer id);

    List<Category> getAllCategoriesList();

    void createCategory(CategoryBindingModel name);

    void editCategory(Integer id,CategoryBindingModel name);

    void deleteCategory(Integer id);

    boolean existById(Integer id);

    List<Category> getAll();
}
