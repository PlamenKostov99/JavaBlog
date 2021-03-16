package softuniBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.User;
import softuniBlog.exceptions.WrongPassword;

import java.io.IOException;

public interface UserController {

  @PostMapping("/register")
  ResponseEntity<User> registerProcess(@RequestBody UserBindingModel userBindingModel)
      throws WrongPassword, IOException;

  @GetMapping("/profile/{id}")
  ResponseEntity<User> profilePage(@PathVariable Integer id) throws IOException;
}
