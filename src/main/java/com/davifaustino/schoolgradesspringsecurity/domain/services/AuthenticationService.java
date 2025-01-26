package com.davifaustino.schoolgradesspringsecurity.domain.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davifaustino.schoolgradesspringsecurity.domain.entities.User;
import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.RecordConflictException;
import com.davifaustino.schoolgradesspringsecurity.infrastructure.repositories.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(User user) {
        if (userRepository.existsById(user.getUsername())) throw new RecordConflictException("Username already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
    }

    public String generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("school-grades-spring-security")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(3600l))
            .subject(authentication.getName())
            .claim("scope", scope)
            .build();
        
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("school-grades-spring-security")
            .issuedAt(now)
            .expiresAt(now.plus(7, ChronoUnit.DAYS))
            .subject(authentication.getName())
            .build();
        
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
