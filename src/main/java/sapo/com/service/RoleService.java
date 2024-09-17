package sapo.com.service;

import sapo.com.model.entity.Role;

public interface RoleService {
    Role findByRoleName(String name);
}
