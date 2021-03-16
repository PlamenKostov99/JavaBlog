package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.User;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.service.RoleServiceImpl;
import softuniBlog.service.UserServiceImpl;

import java.util.List;

@RestController
public class AdminUserControllerImpl implements AdminUserController {

  private final UserServiceImpl userService;

  @Autowired
  public AdminUserControllerImpl(UserServiceImpl userService) {

    this.userService = userService;
  }

  public ResponseEntity<List<User>> listUsers() {

    return ResponseEntity.accepted().body(userService.getAllUsers());
  }

  public ResponseEntity<User> editProcess(Integer id, UserEditBindingModel userEditBindingModel)
      throws EntityNotFound {

    if (!this.userService.existsById(id)) {
      throw new EntityNotFound();
    }

    return ResponseEntity.accepted().body(userService.editUser(id, userEditBindingModel));
  }

  public ResponseEntity<User> deleteProcess(Integer id) throws EntityNotFound {

    if (!this.userService.existsById(id)) {
      throw new EntityNotFound();
    }

    return ResponseEntity.accepted().body(userService.deleteUser(id));
  }
}
