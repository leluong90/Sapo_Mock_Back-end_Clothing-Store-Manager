package sapo.com.controller.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.entity.User;
import sapo.com.service.UserService;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService ;

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.login(userRequest);
        System.out.println(userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody User user){
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }
}
