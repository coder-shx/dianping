package org.com.dianping.controller;

import jakarta.servlet.http.HttpSession;
import org.com.dianping.DTO.LoginRequest;
import org.com.dianping.DTO.UserResponse;
import org.com.dianping.Service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request, HttpSession session) {
        UserResponse response = userService.loginUser(request);
        // 将登录用户信息存入session，可自定义存储内容
        session.setAttribute("USER_SESSION", response);
        return response;
    }
}
