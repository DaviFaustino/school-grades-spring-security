package com.davifaustino.schoolgradesspringsecurity.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.RecordConflictException;
import com.davifaustino.schoolgradesspringsecurity.infrastructure.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registerUser(User user) {
        if (userRepository.existsById(user.getUsername())) throw new RecordConflictException("Username already exists");
        userRepository.saveUser(user);
    }
}
