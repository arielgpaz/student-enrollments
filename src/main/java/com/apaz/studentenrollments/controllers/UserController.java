package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.request.UserCreateRequest;
import com.apaz.studentenrollments.domain.responses.UserResponse;
import com.apaz.studentenrollments.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public UserResponse getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

}
