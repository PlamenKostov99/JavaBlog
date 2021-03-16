package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.*;
import softuniBlog.exceptions.CustomerNotFoundException;
import softuniBlog.repository.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final ArticleRepository articleRepository;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      ArticleRepository articleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.articleRepository = articleRepository;
  }

  public void updateResetPasswordToken(String token, String email)
      throws CustomerNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user != null) {
      user.setResetPasswordToken(token);
      userRepository.save(user);
    } else {
      throw new CustomerNotFoundException("Could not find any customer with the email " + email);
    }
  }

  public User getByResetPasswordToken(String token) {
    return userRepository.findByResetPasswordToken(token);
  }

  public void updatePassword(User user, String newPassword) {

    user.setPassword(newPassword);

    user.setResetPasswordToken(null);
    userRepository.save(user);
  }

  @Override
  public User getByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public User findByResetPasswordToken(String token) {
    return this.findByResetPasswordToken(token);
  }

  @Override
  public User registerUser(UserBindingModel userBindingModel) throws IOException {

    System.out.println();

    User user =
        new User(
            userBindingModel.getEmail(),
            userBindingModel.getFullName(),
            userBindingModel.getPassword());

    Role userRole = this.roleRepository.findByName("ROLE_USER");

    user.addRole(userRole);

    this.userRepository.saveAndFlush(user);

    return user;
  }

  @Override
  public User getProfileDetails(Integer id)
      throws UnsupportedEncodingException {

    return userRepository.findById(id).orElse(null);
  }

  @Override
  public List<User> getAllUsers() {

    return this.userRepository.findAll();
  }

  @Override
  public boolean existsById(Integer id) {
    return this.userRepository.existsById(id);
  }

  @Override
  public User getById(Integer id) {
    return this.userRepository.findById(id).orElse(null);
  }

  @Override
  public User editUser(Integer id, UserEditBindingModel userEditBindingModel) {
    User user = this.userRepository.findById(id).orElse(null);

    if (StringUtils.hasText(userEditBindingModel.getPassword())
        && StringUtils.hasText(userEditBindingModel.getConfirmPassword())) {

      if (userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {

        user.setPassword(userEditBindingModel.getPassword());
      }
    }
    user.setFullName(userEditBindingModel.getFullName());
    user.setEmail(userEditBindingModel.getEmail());

    Set<Role> roles = new HashSet<>();

    for (Integer roleId : userEditBindingModel.getRoles()) {
      roles.add(this.roleRepository.findRoleById(roleId));
    }
    user.setRoles(roles);

    this.userRepository.saveAndFlush(user);

    return user;
  }

  @Override
  public User deleteUser(Integer id) {

    User user = this.userRepository.findById(id).orElse(null);

    for (Article article : user.getArticles()) {
      this.articleRepository.delete(article);
    }

    this.userRepository.delete(user);
    return user;
  }
}
