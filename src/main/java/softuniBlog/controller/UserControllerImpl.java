package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.User;
import softuniBlog.exceptions.WrongPassword;
import softuniBlog.service.UserServiceImpl;

import java.io.IOException;

@RestController
public class UserControllerImpl implements UserController {

  private final UserServiceImpl userServiceImpl;

  @Autowired
  public UserControllerImpl(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  public ResponseEntity<User> registerProcess(UserBindingModel userBindingModel)
          throws WrongPassword, IOException {

    if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
      throw new WrongPassword();
    }

    return  ResponseEntity.accepted().body(userServiceImpl.registerUser(userBindingModel));
  }

  public ResponseEntity<User> profilePage(Integer id) throws IOException {

    User user = userServiceImpl.getProfileDetails(id);

    return ResponseEntity.accepted().body(user);
  }
}
