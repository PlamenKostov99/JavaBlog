package softuniBlog.bindingModel;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBindingModel {


    private String email;


    private String fullName;


    private String password;


    private String confirmPassword;

}
