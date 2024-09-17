package sapo.com.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sapo.com.model.entity.Role;
import sapo.com.repository.RoleRepository;
import sapo.com.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository ;
    @Override
    public Role findByRoleName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
