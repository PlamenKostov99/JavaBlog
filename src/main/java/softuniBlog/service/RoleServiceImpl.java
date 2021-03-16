package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuniBlog.entity.Role;
import softuniBlog.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role getByName(String name) {
    return this.roleRepository.findByName(name);
  }

  @Override
  public List<Role> getAllRoles() {
    return this.roleRepository.findAll();
  }

  @Override
  public Role getRoleById(Integer id) {
    return this.roleRepository.findRoleById(id);
  }
}
