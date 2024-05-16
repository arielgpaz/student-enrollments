package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.request.UserCreateRequest;
import com.apaz.studentenrollments.domain.responses.UserResponse;
import com.apaz.studentenrollments.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

}
