package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuniBlog.entity.Article;


public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Article getById(Integer id);
}
