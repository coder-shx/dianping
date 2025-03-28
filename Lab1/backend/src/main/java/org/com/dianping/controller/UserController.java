package org.com.dianping.controller;

import org.com.dianping.DTO.NewUserRequest;
import org.com.dianping.DTO.UserResponse;
import org.com.dianping.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsernameAvailability(@RequestParam String username) {
        boolean exists = userService.usernameExists(username);
        return Collections.singletonMap("exists", exists);
    }

    @PostMapping
    public UserResponse registerUser(@RequestBody NewUserRequest request, HttpSession session) {
        String sessionCaptcha = (String) session.getAttribute("CAPTCHA_CODE");
        if (sessionCaptcha == null || !sessionCaptcha.equals(request.captcha())) {
            throw new RuntimeException("验证码错误");
        }
        if (!request.username().matches("^[A-Za-z0-9_]{4,20}$")) {
            throw new RuntimeException("用户名格式不合法");
        }
        if (!request.password().matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
            throw new RuntimeException("密码格式不合法");
        }
        session.removeAttribute("CAPTCHA_CODE");
        return userService.registerUser(request);
    }
}