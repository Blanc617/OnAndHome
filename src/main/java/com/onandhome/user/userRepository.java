package com.onandhome.user;

import com.onandhome.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * userId로 사용자 조회
     */
    Optional<User> findByUserId(String userId);

    /**
     * email로 사용자 조회
     */
    Optional<User> findByEmail(String email);

    /**
     * userId 존재 여부 확인
     */
    boolean existsByUserId(String userId);

    /**
     * email 존재 여부 확인
     */
    boolean existsByEmail(String email);

    /**
     * 활성 사용자 중 userId로 조회
     */
    Optional<User> findByUserIdAndActiveTrue(String userId);
}
