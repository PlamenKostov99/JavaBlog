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
@Table(name = "t_tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "tag_name" ,unique = true, nullable = false)
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "tags")
  private Set<Article> articles;

  public Tag(String name) {
    this.name = name;
    this.articles = new HashSet<>();
  }
}
