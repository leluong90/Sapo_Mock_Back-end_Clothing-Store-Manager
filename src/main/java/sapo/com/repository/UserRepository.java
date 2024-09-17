package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sapo.com.model.entity.User;

public interface UserRepository extends JpaRepository<User , Integer> {
    @Query("select u from User u where u.email = :email")
    User findByEmail(String email);

    @Query("select u from User u where u.phoneNumber = :phoneNumber")
    User findByPhoneNumber(String phoneNumber);


}
