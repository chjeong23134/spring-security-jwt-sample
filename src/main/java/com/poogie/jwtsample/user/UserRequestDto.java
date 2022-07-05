package com.poogie.jwtsample.user;

import lombok.Getter;

public class UserRequestDto {
    @Getter
    public static class SignIn {
        private String email;
        private String password;
    }

    @Getter
    public static class SignUp {
        private String email;
        private String password;
        private String name;
    }
}
