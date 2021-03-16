package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuniBlog.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);
}
