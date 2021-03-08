package softuniBlog.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.*;
import softuniBlog.exceptions.CustomerNotFoundException;
import softuniBlog.repository.*;
import softuniBlog.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private  CategoryRepository categoryRepository;


    public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException {
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

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
    public void registerUser(UserBindingModel userBindingModel, @RequestParam("image") MultipartFile file)
            throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword()));


        Role userRole = this.roleRepository.findByName("ROLE_USER");


        user.addRole(userRole);
        user.setPhotos(file.getBytes());
        this.userRepository.saveAndFlush(user);


    }

    @Override
    public User getProfileDetails() throws UnsupportedEncodingException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();



        return this.userRepository.findByEmail(principal.getUsername());

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
    public void editUser(Integer id, UserEditBindingModel userEditBindingModel) {
        User user = this.userRepository.findById(id).orElse(null);

        if (StringUtils.hasText(userEditBindingModel.getPassword())
                && StringUtils.hasText(userEditBindingModel.getConfirmPassword())) {

            if (userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {

                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(userEditBindingModel.getPassword()));

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
    }

    @Override
    public void deleteUser(Integer id) {

        User user = this.userRepository.findById(id).orElse(null);

        for (Article article : user.getArticles()) {
            this.articleRepository.delete(article);
        }

        this.userRepository.delete(user);
    }

    @Override
    public String getImgUtility() throws UnsupportedEncodingException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        byte[] encodeBase64 = Base64.encodeBase64(getByEmail(principal.getUsername()).getPhotos());
        String base64Encoded = new String(encodeBase64, "UTF-8");
        return base64Encoded;
    }
}
