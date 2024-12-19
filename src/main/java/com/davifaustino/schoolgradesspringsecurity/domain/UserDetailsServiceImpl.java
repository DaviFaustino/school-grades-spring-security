package com.davifaustino.schoolgradesspringsecurity.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.NonExistingRecordException;
import com.davifaustino.schoolgradesspringsecurity.infrastructure.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new NonExistingRecordException("User does not exist"));
        user.setPassword("{noop}" + user.getPassword());
        return user;
    }
}
