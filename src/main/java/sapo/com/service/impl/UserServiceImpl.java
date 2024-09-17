package sapo.com.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sapo.com.exception.UserException;
import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.entity.Role;
import sapo.com.model.entity.User;
import sapo.com.repository.UserRepository;
import sapo.com.security.jwt.JwtProvider;
import sapo.com.security.user_principal.UserPrincipal;
import sapo.com.service.RoleService;
import sapo.com.service.UserService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider ;
    @Autowired
    private RoleService roleService;
    @Override
    public User register(User user) {
//        ma hoa mat khau
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        roles
        Set<Role> roles = new HashSet<>();

//        register cua user thi coi no la USER
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("ROLE_ADMIN") );
        }else {

//        Tao tk va phan quyen thi phai co quyen ADMIN
            user.getRoles().forEach(role -> {
                roles.add(roleService.findByRoleName(role.getName()));
            });

        }



        User newUser = new User() ;
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setStatus(user.getStatus());
        newUser.setRoles(roles);
        newUser.setAddress(user.getAddress());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setCreatedOn(LocalDate.now());

        return userRepository.save(newUser);
    }

    @Override
    public UserResponse login(UserRequest userRequest) {
        try {
            Authentication authentication ;
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),userRequest.getPassword()));
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return UserResponse.builder()
                    .token(jwtProvider.generateToken(userPrincipal))
                    .email(userPrincipal.getEmail())
                    .name(userPrincipal.getName())
                    .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                    .build();

        }catch (AuthenticationException authenticationException){
            System.err.println(authenticationException);
            return null;
        }
    }




    @Override
    public User findById(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }throw new UserException("user not found with id :" + id);

//        return null;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException{
        String email = jwtProvider.getUserNameFromToken(jwt);
        User user =userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("user not found with email" + email);
        }
        return user;
    }


}
