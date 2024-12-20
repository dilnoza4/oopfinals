package com.example.appointmentbooking.controller;

import com.example.appointmentbooking.model.User;
import com.example.appointmentbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        return ResponseEntity.ok(userService.registerUser(name, email, password));
    }
}