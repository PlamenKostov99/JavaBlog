package softuniBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.User;
import softuniBlog.exceptions.EntityNotFound;

import java.util.List;

@RequestMapping("/admin/users")
public interface AdminUserController {

  @GetMapping("/")
  ResponseEntity<List<User>> listUsers();

  @PostMapping("/edit/{id}")
  ResponseEntity<User> editProcess(@PathVariable Integer id, UserEditBindingModel userEditBindingModel) throws EntityNotFound;

  @PostMapping("/delete/{id}")
  ResponseEntity<User> deleteProcess(@PathVariable Integer id) throws EntityNotFound;
}
