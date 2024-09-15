package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.com.model.entity.ERole;
import sapo.com.model.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role , Long> {
    Role findRoleByName(ERole name);
}
