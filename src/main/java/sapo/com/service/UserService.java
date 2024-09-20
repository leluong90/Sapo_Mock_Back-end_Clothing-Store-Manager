package sapo.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sapo.com.exception.IncorrectPasswordException;

import sapo.com.exception.UserNotFoundException;

import sapo.com.model.dto.request.PasswordResetRequest;

import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.entity.Role;
import sapo.com.model.entity.User;

public interface UserService {

    User findById(Long id) throws UserNotFoundException;

    public void resetPassword(Long staffId, PasswordResetRequest request) throws IncorrectPasswordException, UserNotFoundException;
}