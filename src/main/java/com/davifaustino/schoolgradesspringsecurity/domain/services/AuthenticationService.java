package com.davifaustino.schoolgradesspringsecurity.domain.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davifaustino.schoolgradesspringsecurity.domain.entities.User;
import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.InvalidTokenException;
import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.NonExistingRecordException;
import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.RecordConflictException;
import com.davifaustino.schoolgradesspringsecurity.infrastructure.repositories.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration-time.access}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.expiration-time.refresh}")
    private Long refreshTokenExpirationTime;

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
            .expiresAt(now.plusSeconds(accessTokenExpirationTime))
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
            .expiresAt(now.plusSeconds(refreshTokenExpirationTime))
            .subject(authentication.getName())
            .build();
        
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String[] refreshAccessToken(String refreshToken) {
        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);
            String username = jwt.getSubject();
            User user = userRepository.findById(username).orElseThrow(() -> new NonExistingRecordException(username));

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            String newAccessToken = generateAccessToken(authentication);
            String newRefreshToken = generateRefreshToken(authentication);

            return new String[] {newAccessToken, newRefreshToken};
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid Token");
        }
    }
}
