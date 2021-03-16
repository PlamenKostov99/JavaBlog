package softuniBlog.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    User getByEmail(String email);

    User findByResetPasswordToken(String token);

    User registerUser(UserBindingModel userBindingModel)
            throws Exception;

    User getProfileDetails(Integer id) throws UnsupportedEncodingException;

    List<User> getAllUsers();

    boolean existsById(Integer id);

    User getById(Integer id);

    User editUser(Integer id, UserEditBindingModel userEditBindingModel);

    User deleteUser(Integer id);




}
