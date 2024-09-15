package sapo.com.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sapo.com.model.entity.ERole;
import sapo.com.model.entity.Role;
import sapo.com.repository.RoleRepository;
import sapo.com.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository ;
    @Override
    public Role findByRoleName(ERole name) {
        return roleRepository.findRoleByName(name);
    }
}
