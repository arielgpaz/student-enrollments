package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.enums.RoleType;
import com.apaz.studentenrollments.domain.request.UserCreateRequest;
import com.apaz.studentenrollments.domain.responses.UserResponse;
import com.apaz.studentenrollments.exceptions.EmailAlreadyInUseException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.exceptions.UsernameAlreadyInUseException;
import com.apaz.studentenrollments.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser_Success() {
        UserCreateRequest request = new UserCreateRequest("John Doe", "johndoe", "johndoe@email.com", "password", RoleType.ESTUDANTE);
        User expectedUser = User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByUsername(request.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User actualUser = userService.createUser(request);

        assertEquals(expectedUser, actualUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testCreateUser_EmailAlreadyInUse() {
        UserCreateRequest request = new UserCreateRequest("John Doe", "johndoe", "johndoe@email.com", "password", RoleType.ESTUDANTE);

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(request));
    }

    @Test
    public void testCreateUser_UsernameAlreadyInUse() {
        UserCreateRequest request = new UserCreateRequest("John Doe", "johndoe", "johndoe@email.com", "password", RoleType.ESTUDANTE);

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByUsername(request.username())).thenReturn(true);

        assertThrows(UsernameAlreadyInUseException.class, () -> userService.createUser(request));
    }

    @Test
    public void testGetUserByUsername_Success() {
        String username = "johndoe";
        User user = new User();
        UserResponse expectedResponse = UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserResponse actualResponse = userService.getUserByUsername(username);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetUserByUsername_NotFound() {
        String username = "invalid-username";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }
}

