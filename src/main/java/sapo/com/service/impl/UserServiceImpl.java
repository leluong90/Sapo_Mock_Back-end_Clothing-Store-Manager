package sapo.com.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sapo.com.exception.IncorrectPasswordException;

import sapo.com.exception.UserNotFoundException;

import sapo.com.model.dto.request.PasswordResetRequest;

import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.entity.Role;
import sapo.com.model.entity.User;
import sapo.com.repository.UserRepository;
import sapo.com.security.jwt.JwtProvider;
import sapo.com.security.user_principal.UserPrincipal;
import sapo.com.service.RoleService;
import sapo.com.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleService roleService;


    @Override
    public User findById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserNotFoundException("Không tìm thấy nhân viên với ID:" + id);

//        return null;
    }

    @Override
    public void resetPassword(Long staffId, PasswordResetRequest request) throws IncorrectPasswordException, UserNotFoundException {
        // Fetch staff by id
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy nhân viên với ID:" + staffId));

        // Verify current password using passwordEncoder
        if (!passwordEncoder.matches(request.getCurrentPassword(), staff.getPassword())) {
            throw new IncorrectPasswordException("Mật khẩu hiện tại không chính xác");
        }

        // Optional: You may want to add validation to check if newPassword equals confirmNewPassword
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new IllegalArgumentException("Mật khẩu mới không khớp");
        }

        // Encode and update staff password
        staff.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(staff);
    }
}



