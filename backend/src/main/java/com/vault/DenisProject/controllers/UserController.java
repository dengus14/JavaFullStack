package com.vault.DenisProject.controllers;

import com.vault.DenisProject.models.User;
import com.vault.DenisProject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String token = userService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(token);
    }


}
