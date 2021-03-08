package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuniBlog.entity.Category;

import java.util.List;

import static org.hibernate.loader.Loader.SELECT;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

        Category findCategoriesById(Integer id);



}
