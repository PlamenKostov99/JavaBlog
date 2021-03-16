package softuniBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "category_name" ,nullable = false, unique = true)
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "category")
  private Set<Article> articles;

  public Category(String name) {
    this.name = name;
    this.articles = new HashSet<>();
  }
}
