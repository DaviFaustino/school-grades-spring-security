package com.davifaustino.schoolgradesspringsecurity.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table("tb_users")
@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private UserRole role;
    
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
