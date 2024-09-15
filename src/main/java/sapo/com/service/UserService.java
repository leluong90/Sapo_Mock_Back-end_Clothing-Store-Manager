package sapo.com.service;

import sapo.com.exception.UserException;
import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.entity.User;

public interface UserService {
    User register(User user);
    UserResponse login (UserRequest userRequest);

    User findById(Long id) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;
}
