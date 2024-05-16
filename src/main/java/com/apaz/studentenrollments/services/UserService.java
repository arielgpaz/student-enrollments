package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.request.UserCreateRequest;
import com.apaz.studentenrollments.domain.responses.UserResponse;
import com.apaz.studentenrollments.exceptions.EmailAlreadyInUseException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.exceptions.UsernameAlreadyInUseException;
import com.apaz.studentenrollments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserCreateRequest request) {

        this.validateRequest(request);

        return userRepository.save(User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build());
    }

    private void validateRequest(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyInUseException(request.email());
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyInUseException(request.username());
        }
    }

    public UserResponse getUserByUsername(String username) {

        var user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UserNotFoundException(username));

        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
