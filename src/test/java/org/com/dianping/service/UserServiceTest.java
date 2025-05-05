package org.com.dianping.service;

import org.com.dianping.DTO.LoginRequest;
import org.com.dianping.DTO.NewUserRequest;
import org.com.dianping.DTO.UserResponse;
import org.com.dianping.entity.User;
import org.com.dianping.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void registerUser_WithValidInput_ShouldSucceed() {
        // Arrange
        NewUserRequest request = new NewUserRequest("testuser", "Password123", "1234");
        User savedUser = new User(1L, "testuser", "encodedPassword");
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse response = userService.registerUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("testuser", response.username());
        assertEquals(1L, response.id());
    }

    @Test
    void registerUser_WithExistingUsername_ShouldThrowException() {
        // Arrange
        NewUserRequest request = new NewUserRequest("existinguser", "Password123", "1234");
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.registerUser(request));
        assertEquals("用户名已存在", exception.getMessage());
    }

    @Test
    void registerUser_WithInvalidUsername_ShouldThrowException() {
        // Arrange
        NewUserRequest request = new NewUserRequest("u", "Password123", "1234");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.registerUser(request));
        assertEquals("用户名格式不合法", exception.getMessage());
    }

    @Test
    void loginUser_WithValidCredentials_ShouldSucceed() {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "Password123");
        User user = new User(1L, "testuser", "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Act
        UserResponse response = userService.loginUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("testuser", response.username());
        assertEquals(1L, response.id());
    }

    @Test
    void loginUser_WithNonexistentUser_ShouldThrowException() {
        // Arrange
        LoginRequest request = new LoginRequest("nonexistent", "password");
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.loginUser(request));
        assertEquals("用户不存在", exception.getMessage());
    }
}
