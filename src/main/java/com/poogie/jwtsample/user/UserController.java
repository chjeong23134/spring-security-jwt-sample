package com.poogie.jwtsample.user;

import com.poogie.jwtsample.security.JwtDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/auth/sign-in")
    public ResponseEntity<JwtDto> signIn(@RequestBody UserRequestDto.SignIn req) {
        return new ResponseEntity<>(authService.signIn(req) , HttpStatus.OK);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<HttpStatus> signUp(@RequestBody UserRequestDto.SignUp req) {
        authService.signUp(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<UserEntity> test(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }
}
