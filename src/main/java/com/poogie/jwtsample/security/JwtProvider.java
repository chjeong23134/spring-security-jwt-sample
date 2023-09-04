package com.poogie.jwtsample.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    public String create(UserDetails userDetails) {
        // 키생성
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());

        // 만료시간
        long EXPIRE_TIME = 60 * 60 * 24 * 1000L;

        // JWT 리턴
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        // 키생성
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());

        // 유저 디테일 생성
        String username = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject();
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        // 유저 인증토큰 리턴
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolve(HttpServletRequest req) {
        // JWT 추출
        String bearerJwt = req.getHeader("Authorization");
        if (bearerJwt != null && bearerJwt.startsWith("Bearer")) {
            return bearerJwt.substring(7);
        }
        return null;
    }

    public boolean validate(String jwt) {
        // 키생성
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());

        // 인증유저 정보 유무
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException("Error on Token");
        }
    }
}
