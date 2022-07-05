package com.poogie.jwtsample.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtDto {
    private String accessJwt;
}
