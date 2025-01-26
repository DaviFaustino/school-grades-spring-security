package com.davifaustino.schoolgradesspringsecurity.infrastructure.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.davifaustino.schoolgradesspringsecurity.domain.entities.User;
import com.davifaustino.schoolgradesspringsecurity.domain.enums.UserRole;
import com.davifaustino.schoolgradesspringsecurity.infrastructure.repositories.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Value("classpath:public.key")
    private RSAPublicKey publicKey;

    @Value("classpath:private.key")
    private RSAPrivateKey privateKey;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/report-card/student").hasRole("STUDENT")
                                .requestMatchers(HttpMethod.POST, "/authentication/register").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/authentication/login", "/authentication/refresh").permitAll()
                                .anyRequest().hasRole("TEACHER"))
                            .httpBasic(Customizer.withDefaults())
                            .oauth2ResourceServer(conf -> conf
                                .jwt(jwt -> jwt
                                    .decoder(jwtDecoder())))
                            .build();
    }
    
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	ApplicationRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.count() == 0l) {
				User user = new User("admin", passwordEncoder.encode("1234"), UserRole.ADMIN);
				userRepository.saveUser(user);
				
				System.out.println("Default credentials created!\nUsername: admin\nPassword: 1234");
			}
		};
	}
}
