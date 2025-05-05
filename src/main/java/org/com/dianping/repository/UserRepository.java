package org.com.dianping.repository;

import org.com.dianping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 判断用户名是否存在
    boolean existsByUsername(String username);

    // 根据用户名查找用户
    Optional<User> findByUsername(String username);
}
