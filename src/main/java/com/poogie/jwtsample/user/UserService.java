package com.poogie.jwtsample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
