package org.com.dianping.controller;

import jakarta.servlet.http.HttpSession;
import org.com.dianping.DTO.LoginRequest;
import org.com.dianping.DTO.UserResponse;
import org.com.dianping.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_WithValidCredentials_ShouldReturnUserResponse() {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "password");
        UserResponse expectedResponse = new UserResponse(1L, "testuser");
        when(userService.loginUser(request)).thenReturn(expectedResponse);

        // Act
        UserResponse response = authController.login(request, session);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.username(), response.username());
        assertEquals(expectedResponse.id(), response.id());
        verify(session).setAttribute("USER_SESSION", expectedResponse);
    }

    @Test
    void login_WithInvalidCredentials_ShouldThrowException() {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "wrongpassword");
        when(userService.loginUser(request)).thenThrow(new RuntimeException("密码错误"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authController.login(request, session));
        assertEquals("密码错误", exception.getMessage());
        verify(session, never()).setAttribute(any(), any());
    }
}
