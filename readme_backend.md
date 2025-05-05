# 后端代码实现原理

## 1. UserRepository

`UserRepository` 接口继承自 `JpaRepository`，用于定义用户相关的数据库操作。Spring Data JPA 会自动生成该接口的实现类。

- `existsByUsername(String username)`: 判断用户名是否存在。
- `findByUsername(String username)`: 根据用户名查找用户。

## 2. GlobalExceptionHandler

`GlobalExceptionHandler` 类用于全局异常处理，捕获运行时异常并返回错误响应。

- `handleRuntimeException(RuntimeException e)`: 处理运行时异常，返回包含错误信息的响应。

## 3. User 实体类

`User` 类是一个 JPA 实体类，映射到数据库中的 `User` 表。

- `id`: 用户ID，主键，自增。
- `username`: 用户名，唯一。
- `encryptedPassword`: 加密后的密码。

## 4. CaptchaController

`CaptchaController` 类用于生成验证码图片。

- `getCaptcha(HttpSession session, HttpServletResponse response)`: 生成验证码图片并返回给客户端，同时将验证码存储在 session 中。

## 5. UserController

`UserController` 类处理用户相关的请求。

- `checkUsernameAvailability(String username)`: 检查用户名是否可用。
- `registerUser(NewUserRequest request, HttpSession session)`: 注册新用户，验证验证码和用户名、密码格式。

## 6. AuthController

`AuthController` 类处理用户认证相关的请求。

- `login(LoginRequest request, HttpSession session)`: 用户登录，验证用户名和密码，并将用户信息存储在 session 中。

## 7. UserService

`UserService` 类包含用户相关的业务逻辑。

- `usernameExists(String username)`: 检查用户名是否存在。
- `registerUser(NewUserRequest request)`: 注册新用户，验证用户名和密码格式，检查用户名是否存在，加密密码并保存用户。
- `loginUser(LoginRequest request)`: 用户登录，验证用户名和密码。

## 8. PasswordUtil

`PasswordUtil` 类用于密码加密和校验。

- `encryptPassword(String password)`: 加密密码。
- `checkPassword(String password, String encryptPassword)`: 校验密码。

## 9. DianpingApplication

`DianpingApplication` 类是 Spring Boot 应用的入口类，包含 `main` 方法用于启动应用。

## 10. DTO 类

### UserResponse

`UserResponse` 类是用户响应 DTO，用于返回用户信息。

### NewUserRequest

`NewUserRequest` 类是新用户请求 DTO，用于接收注册请求的数据。

### LoginRequest

`LoginRequest` 类是登录请求 DTO，用于接收登录请求的数据。
