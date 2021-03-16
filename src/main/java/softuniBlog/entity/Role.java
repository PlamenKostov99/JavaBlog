package softuniBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "role_name", nullable = false)
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  public Role() {
    this.users = new HashSet<>();
  }

  @Transient
  public String getSimpleName() {
    return StringUtils.capitalize(this.getName().substring(5).toLowerCase());
  }
}
