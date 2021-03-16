package softuniBlog.bindingModel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserEditBindingModel extends UserBindingModel {

    private List<Integer> roles;

    public UserEditBindingModel(){
        this.roles = new ArrayList<>();
    }

}
