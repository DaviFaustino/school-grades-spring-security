package com.davifaustino.schoolgradesspringsecurity.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table("tb_users")
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    private String username;
    private String password;
    private UserRole role;
}
