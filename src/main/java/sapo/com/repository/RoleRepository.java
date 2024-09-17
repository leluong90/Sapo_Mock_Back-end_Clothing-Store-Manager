package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.com.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role , Long> {
    Role findRoleByName(String name);
}
