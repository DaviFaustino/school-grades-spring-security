package com.davifaustino.schoolgradesspringsecurity.domain.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.davifaustino.schoolgradesspringsecurity.api.dtos.UserRequest;
import com.davifaustino.schoolgradesspringsecurity.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private UserRole role;

    // For some reason, for this constructor to exist and authentication to work, there must be a "NoArgsConstructor"
    public User(UserRequest userRequest) {
        this.username = userRequest.username();
        this.password = userRequest.password();
        this.role = userRequest.role();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                            new SimpleGrantedAuthority("ROLE_TEACHER"),
                            new SimpleGrantedAuthority("ROLE_STUDENT"));
        } 
        if (this.role == UserRole.TEACHER) {
            return List.of(new SimpleGrantedAuthority("ROLE_TEACHER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }
}
