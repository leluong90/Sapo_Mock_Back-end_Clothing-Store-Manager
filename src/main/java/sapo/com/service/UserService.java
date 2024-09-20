package sapo.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sapo.com.exception.UserException;
import sapo.com.model.dto.request.PasswordRequest;
import sapo.com.model.dto.request.RoleRequest;
import sapo.com.model.dto.request.UpdateUserRequest;
import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.entity.Role;
import sapo.com.model.entity.User;

public interface UserService {
    User register(User user) throws Exception;
    UserResponse login (UserRequest userRequest) throws Exception;

    User resetPassword (Integer id) throws Exception;
    Page<User> findAll(Pageable pageable);

    User update (Integer id , User user) throws Exception;

    User updateRole (Integer id , Role role) throws Exception;

    User findById(Integer id) throws UserException;

    User findByPhoneNumber(String phoneNumber) throws Exception;

    User findByEmail(String email) throws Exception;

    void existPhoneNumber (String phoneNumber) throws Exception;

    void existEmail(String email) throws Exception ;

    User changPassword (Integer id , PasswordRequest passwordRequest) throws Exception;

    void deleteById (Integer id) throws Exception;

    User findUserProfileByJwt(String jwt) throws UserException;
}
