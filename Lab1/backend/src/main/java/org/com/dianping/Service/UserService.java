package org.com.dianping.Service;

import org.com.dianping.DTO.LoginRequest;
import org.com.dianping.DTO.NewUserRequest;
import org.com.dianping.DTO.UserResponse;
import org.com.dianping.Utils.PasswordUtil;
import org.com.dianping.entity.User;
import org.com.dianping.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserResponse registerUser(NewUserRequest request) {
        if (!request.username().matches("^[A-Za-z0-9_]{4,20}$")) {
            throw new RuntimeException("用户名格式不合法");
        }
        if (!request.password().matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
            throw new RuntimeException("密码格式不合法");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("用户名已存在");
        }
        String encryptedPassword = PasswordUtil.encryptPassword(request.password());
        var user = new User(null, request.username(), encryptedPassword);
        user = userRepository.save(user);
        return new UserResponse(user);
    }

    public UserResponse loginUser(LoginRequest request) {
        var userOpt = userRepository.findByUsername(request.username());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("用户不存在");
        }
        User user = userOpt.get();
        if (!PasswordUtil.checkPassword(request.password(), user.getEncryptedPassword())) {
            throw new RuntimeException("密码错误");
        }
        return new UserResponse(user);
    }
}
