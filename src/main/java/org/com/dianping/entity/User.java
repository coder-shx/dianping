package org.com.dianping.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 用户实体类
 * <p>
 * 此类表示系统中的用户，包含用户的基本信息。作为JPA实体映射到数据库的User表。
 * </p>
 * 
 * @author Software Engineering Group
 */
@Entity
@Table(name = "User")
public class User {
    /**
     * 用户ID
     * <p>
     * 主键，由数据库自动生成
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = true, unique = true)
    private Long id;

    /**
     * 用户名
     * <p>
     * 用户的登录名，具有唯一性
     * </p>
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * 加密后的密码
     * <p>
     * 使用BCrypt算法加密后的密码字符串
     * </p>
     */
    @Column(name = "encryptedPassword", nullable = false, unique = true)
    private String encryptedPassword;

    /**
     * 构造方法
     * <p>
     * 使用用户ID、用户名和加密后的密码初始化用户对象
     * </p>
     * 
     * @param id 用户ID
     * @param name 用户名
     * @param encryptedPassword 加密后的密码
     */
    public User(Long id, String name, String encryptedPassword) {
        this.id = id;
        this.username = name;
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * 构造方法
     * <p>
     * 使用用户名和加密后的密码初始化用户对象
     * </p>
     * 
     * @param username 用户名
     * @param encryptedPassword 加密后的密码
     */
    public User(String username, String encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * 默认构造方法
     * <p>
     * 初始化一个空的用户对象
     * </p>
     */
    public User() {
    }

    /**
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     * 
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取加密后的密码
     * 
     * @return 加密后的密码
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * 设置加密后的密码
     * 
     * @param encryptedPassword 加密后的密码
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
