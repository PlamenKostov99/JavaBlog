package softuniBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "article_title", nullable = false)
  private String title;

  @Column(name = "article_content", columnDefinition = "text", nullable = false)
  private String content;

  @JsonIgnore
  @ManyToOne()
  @JoinColumn(nullable = false, name = "authorId")
  private User author;


  @ManyToOne
  @JoinColumn(nullable = false, name = "categoryId")
  private Category category;


  @ManyToMany()
  @JoinColumn(table = "articles_tags")
  private Set<Tag> tags;

  @Column(name = "article_likes")
  private Integer likes;

  @Lob
  @Column(name = "article_photos")
  private byte[] photos;

  public Article(
      String title,
      String content,
      User author,
      Category category,
      HashSet<Tag> tags) {
    this.title = title;
    this.content = content;
    this.author = author;
    this.category = category;
    this.tags = tags;

  }

  @Transient
  public String getSummary() {

    return this.getContent().substring(0, this.getContent().length() / 2) + "...";
  }
}
