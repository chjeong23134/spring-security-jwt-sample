package com.poogie.jwtsample.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;

    @Builder
    public UserEntity(Long id, String email, String password, String name, String role, String isDeleted) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
