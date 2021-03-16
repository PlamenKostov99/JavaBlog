package softuniBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_emails", unique = true, nullable = false)
    private String email;

    @Column(name = "user_fullNames",  nullable = false)
    private String fullName;

    @Column(name = "user_passwords", length = 60, nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private Set<Article> articles;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;


    @Column(name = "user_photos")
    private byte[] photos;

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = new HashSet<>();
        this.articles = new HashSet<>();
    }

    public void addRole(Role role){
        this.roles.add(role);
    }


    @Transient
    public boolean isAdmin(){
        return this.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Transient
    public boolean isAuthor(Article article){
        return Objects.equals(this.getId(), article.getAuthor().getId());
    }



}
