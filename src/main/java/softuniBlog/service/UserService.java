package softuniBlog.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    User getByEmail(String email);

    User findByResetPasswordToken(String token);

    void registerUser(UserBindingModel userBindingModel, @RequestParam("image") MultipartFile multipartImage)
            throws Exception;

    User getProfileDetails() throws UnsupportedEncodingException;

    List<User> getAllUsers();

    boolean existsById(Integer id);

    User getById(Integer id);

    void editUser(Integer id, UserEditBindingModel userEditBindingModel);

    void deleteUser(Integer id);

    String getImgUtility() throws UnsupportedEncodingException;



}
