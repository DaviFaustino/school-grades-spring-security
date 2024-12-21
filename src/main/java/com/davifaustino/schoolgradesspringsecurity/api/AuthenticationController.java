package com.davifaustino.schoolgradesspringsecurity.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davifaustino.schoolgradesspringsecurity.api.dtos.UserRequest;
import com.davifaustino.schoolgradesspringsecurity.domain.AuthenticationService;
import com.davifaustino.schoolgradesspringsecurity.domain.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;
    
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRequest userRequest) {
        authService.registerUser(new User(userRequest));

        return ResponseEntity.created(URI.create("")).build();
    }
}
