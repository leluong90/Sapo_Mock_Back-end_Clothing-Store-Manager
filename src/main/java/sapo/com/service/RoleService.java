package sapo.com.service;

import sapo.com.model.entity.ERole;
import sapo.com.model.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByRoleName(ERole name);
}
