package softuniBlog.service;

import softuniBlog.entity.Role;

import java.util.List;

public interface RoleService {

    Role getByName(String name);

    List<Role> getAllRoles();

    Role getRoleById(Integer id);
}
