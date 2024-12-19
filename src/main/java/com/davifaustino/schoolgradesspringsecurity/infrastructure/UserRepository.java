package com.davifaustino.schoolgradesspringsecurity.infrastructure;

import org.springframework.data.repository.CrudRepository;

import com.davifaustino.schoolgradesspringsecurity.domain.User;

public interface UserRepository extends CrudRepository<User, String> {
    
}
